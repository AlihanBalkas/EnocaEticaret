package com.enoca.eticaret.web.customer.service;

import com.enoca.eticaret.web.customer.model.CustomerRequest;
import com.enoca.eticaret.web.customer.model.CustomerResponse;

public interface ICustomerService {
    CustomerResponse addCustomer(CustomerRequest customerRequest);
}