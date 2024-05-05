package com.enoca.eticaret.web.cart.service.impl;

import com.enoca.eticaret.web.cart.entity.Cart;
import com.enoca.eticaret.web.cart.mapper.CartMapper;
import com.enoca.eticaret.web.cart.model.CartResponse;
import com.enoca.eticaret.web.cart.model.CartUpdate;
import com.enoca.eticaret.web.cart.repository.CartRepository;
import com.enoca.eticaret.web.cart.service.ICartService;
import com.enoca.eticaret.web.common.exception.CustomNotFoundException;
import com.enoca.eticaret.web.customer.entity.Customer;
import com.enoca.eticaret.web.customer.repository.CustomerRepository;
import com.enoca.eticaret.web.order.entity.Order;
import com.enoca.eticaret.web.order.repository.OrderRepository;
import com.enoca.eticaret.web.product.entity.Product;
import com.enoca.eticaret.web.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartService implements ICartService {

    private final CartRepository cartRepository;
    private final CartMapper cartMapper;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    @Override
    public List<CartResponse> findAll() {
        List<Cart> carts = cartRepository.findAll();
        return cartMapper.mapResponseToEntityList(carts);
    }

    @Override
    public CartResponse findById(String id) {
        Cart cart = cartRepository.findById(id).orElseThrow(()-> new CustomNotFoundException(""));
        return cartMapper.mapResponseToEntity(cart);
    }

    @Override
    public CartResponse update(CartUpdate cartUpdate) {
        Cart cart = cartMapper.mapUpdateToEntity(cartUpdate);
        Cart savedEntity = cartRepository.save(cart);
        return cartMapper.mapResponseToEntity(savedEntity);
    }

    @Override
    public void addProductToCart(String customerId, String productId, Integer quantity) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomNotFoundException("Müşteri bulunamadı: " + customerId));

        Cart cart = customer.getCart();
        if (cart == null) {
            cart = new Cart();
            customer.setCart(cart);
        }

        List<Product> products = cart.getProducts();
        if (products == null) {
            products = new ArrayList<>();
            cart.setProducts(products);
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new CustomNotFoundException("Ürün bulunamadı: " + productId));

        int availableStock = product.getStock();
        if (availableStock < quantity) {
            throw new CustomNotFoundException("Yeterli stok bulunmamaktadır. Stok: " + availableStock);
        }

        checkProductBeforeAddingToCart(customer, product);

        products.add(product);
        cart.setCustomer(customer);

        BigDecimal totalAmount = cart.getTotalAmount().add(product.getPrice());
        cart.setTotalAmount(totalAmount);

        cartRepository.save(cart);

        product.setStock(availableStock - quantity);
        productRepository.save(product);

        placeOrder(customer, product, quantity);

        emptyCart(customer);
    }

    public void emptyCart(Customer customer) {
        Cart cart = customer.getCart();
        if (cart != null) {
            List<Product> products = cart.getProducts();
            if (products != null) {
                products.clear();
                cart.setTotalAmount(BigDecimal.ZERO);
                cartRepository.save(cart);
            }
        }
    }


    private void placeOrder(Customer customer, Product product, Integer quantity) {
        Random random = new Random();
        int orderNumber = random.nextInt(8);
        Order order = new Order();
        order.setCustomerId(customer.getId());
        order.getProductIds().add(product.getId());
        order.setTotalAmount(product.getPrice());
        order.setOrderDate(LocalDateTime.now());
        order.setCode(orderNumber);
        order.setQuantity(1);
        orderRepository.save(order);
    }



    private void checkProductBeforeAddingToCart(Customer customer, Product product) {
        List<Product> products = customer.getCart().getProducts();
        Optional<Product> existingProduct = products.stream()
                .filter(p -> p.getId().equals(product.getId()))
                .findFirst();

        if (existingProduct.isPresent()) {
            Product foundProduct = existingProduct.get();
            foundProduct.setQuantity(foundProduct.getQuantity() + 1);
        } else {
            products.add(product);
        }
        if (product.getStock() <= 0) {
            throw new CustomNotFoundException("Üzgünüz, bu üründen stokta kalmamıştır.");
        }
    }

    /*@Override
    public void removeProductFromCart(String customerId, String productId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomNotFoundException("Müşteri bulunamadı: " + customerId));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new CustomNotFoundException("Ürün bulunamadı: " + productId));

        // Sepetten ürün çıkarılırken toplam fiyatı güncelle
        BigDecimal totalAmount = customer.getCart().getTotalAmount().subtract(product.getPrice());
        customer.getCart().setTotalAmount(totalAmount);

        customer.getCart().getProducts().remove(product);
        customerRepository.save(customer);
    }*/

}