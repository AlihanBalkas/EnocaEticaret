package com.enoca.eticaret.web.customer.service.impl;

import com.enoca.eticaret.web.cart.entity.Cart;
import com.enoca.eticaret.web.common.exception.CustomNotFoundException;
import com.enoca.eticaret.web.customer.entity.Customer;
import com.enoca.eticaret.web.customer.mapper.CustomerMapper;
import com.enoca.eticaret.web.customer.model.CustomerRequest;
import com.enoca.eticaret.web.customer.model.CustomerResponse;
import com.enoca.eticaret.web.customer.repository.CustomerRepository;
import com.enoca.eticaret.web.customer.service.ICustomerService;
import com.enoca.eticaret.web.product.entity.Product;
import com.enoca.eticaret.web.product.mapper.ProductMapper;
import com.enoca.eticaret.web.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerService implements ICustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final ProductMapper productMapper;
    private final ProductRepository productRepository;
    @Override
    public CustomerResponse addCustomer(CustomerRequest customerRequest) {
        Customer customer = customerMapper.mapRequestToEntity(customerRequest);
        Customer savedCustomer = customerRepository.save(customer);
        return customerMapper.mapResponseToEntity(savedCustomer);
    }

    @Override
    public void addProductToCart(String customerId, String productId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomNotFoundException("Müşteri bulunamadı: " + customerId));
        if (customer.getCart() == null) {
            customer.setCart(new Cart());
        }
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new CustomNotFoundException("Ürün bulunamadı: " + productId));
        // ürün ve stock kontrolü
        checkProductBeforeAddingToCart(customer,product);

        // Sepete ürün eklenirken toplam fiyatı güncelle
        BigDecimal totalAmount = customer.getCart().getTotalAmount().add(product.getPrice());
        customer.getCart().setTotalAmount(totalAmount);
        // Ürünü sepete ekleme
        customer.getCart().getProducts().add(product);

        customerRepository.save(customer);
    }

    private void checkProductBeforeAddingToCart(Customer customer, Product product) {
        // Ürün sepete daha önce eklenmiş mi kontrolü
        if (customer.getCart().getProducts().stream().anyMatch(p -> p.getId().equals(product.getId()))) {
            throw new CustomNotFoundException("Ürün zaten sepetinizde bulunmaktadır.");
        }
        // Stok kontrolü
        if (product.getStock() <= 0) {
            throw new CustomNotFoundException("Üzgünüz, bu üründen stokta kalmamıştır.");
        }
    }

    @Override
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
    }

}