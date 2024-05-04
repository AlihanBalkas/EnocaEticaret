package com.enoca.eticaret.web.customer.mapper;

import com.enoca.eticaret.web.cart.mapper.CartMapper;
import com.enoca.eticaret.web.customer.entity.Customer;
import com.enoca.eticaret.web.customer.model.CustomerRequest;
import com.enoca.eticaret.web.customer.model.CustomerResponse;
import com.enoca.eticaret.web.order.mapper.OrderMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {CartMapper.class, OrderMapper.class})
public interface CustomerMapper {
    CustomerResponse mapResponseToEntity(Customer customer);
    Customer mapRequestToEntity(CustomerRequest customerRequest);
}