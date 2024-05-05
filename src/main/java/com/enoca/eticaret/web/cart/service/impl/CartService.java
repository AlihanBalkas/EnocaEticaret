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
    public void addProductToCart(String customerId, String productId, int quantity) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomNotFoundException("Müşteri bulunamadı: " + customerId));

        // Müşteriye ait sepeti al veya yeni bir sepet oluştur
        Cart cart = customer.getCart();
        if (cart == null) {
            cart = new Cart();
            customer.setCart(cart);
        }

        // Sepet içindeki ürünleri al veya yeni bir liste oluştur
        List<Product> products = cart.getProducts();
        if (products == null) {
            products = new ArrayList<>();
            cart.setProducts(products);
        }

        // Eklenmek istenen ürünü bul
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new CustomNotFoundException("Ürün bulunamadı: " + productId));

        int availableStock = product.getStock();
        if (availableStock < quantity) {
            throw new CustomNotFoundException("Yeterli stok bulunmamaktadır. Stok: " + availableStock);
        }

        // Ürünün sepete eklenmeden önce kontrollerini yap
        checkProductBeforeAddingToCart(customer, product);

        // Ürünü sepete ekle
        products.add(product);
        cart.setCustomer(customer);

        // Sepete ürün eklenirken toplam fiyatı güncelle
        BigDecimal totalAmount = cart.getTotalAmount().add(product.getPrice());
        cart.setTotalAmount(totalAmount);

        // Sepetin güncellenmiş halini veritabanına kaydet
        cartRepository.save(cart);

        // Ürün stoklarını azalt
        product.setStock(availableStock - quantity);
        productRepository.save(product);

        // Sepete eklenen ürünü sipariş olarak kaydet
        placeOrder(customer, product, quantity);

        //stock dan düşülecek
        //cart temizlencek
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


    private void placeOrder(Customer customer, Product product, int quantity) {
        Random random = new Random();
        int orderNumber = random.nextInt(8);
        Order order = new Order();
        order.setCustomerId(customer.getId());
        order.getProductIds().add(product.getId()); // Siparişe eklenen ürünün id'sini listeye ekle
        order.setTotalAmount(product.getPrice()); // Siparişin toplam tutarı, eklenen ürünün fiyatıyla aynı
        order.setOrderDate(LocalDateTime.now()); // Siparişin oluşturulma tarihi
        order.setCode(orderNumber);

        orderRepository.save(order);
    }



    private void checkProductBeforeAddingToCart(Customer customer, Product product) {
        // Ürün sepete daha önce eklenmiş mi kontrolü
        List<Product> products = customer.getCart().getProducts();
        Optional<Product> existingProduct = products.stream()
                .filter(p -> p.getId().equals(product.getId()))
                .findFirst();

        if (existingProduct.isPresent()) {
            // Ürün sepette bulunduğunda adetini artır
            Product foundProduct = existingProduct.get();
            foundProduct.setQuantity(foundProduct.getQuantity() + 1); // Varsayılan olarak 1 arttırıyoruz, isteğinize göre ayarlayabilirsiniz
            // Diğer işlemler...
        } else {
            // Ürün sepette bulunmadığında normal ekleme işlemi yap
            // Ürünü sepete ekleme
            products.add(product);
            // Diğer işlemler...
        }
        // Stok kontrolü
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