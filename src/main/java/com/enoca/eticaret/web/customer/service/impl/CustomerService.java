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
import java.util.ArrayList;
import java.util.List;

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

}