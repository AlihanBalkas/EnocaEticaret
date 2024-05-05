package com.enoca.eticaret.web.customer.controller;

import com.enoca.eticaret.web.customer.model.CustomerRequest;
import com.enoca.eticaret.web.customer.model.CustomerResponse;
import com.enoca.eticaret.web.customer.service.impl.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<CustomerResponse> addCustomer(@RequestBody CustomerRequest customerRequest){
        CustomerResponse response = customerService.addCustomer(customerRequest);
        return ResponseEntity.ok(response);
    }

}