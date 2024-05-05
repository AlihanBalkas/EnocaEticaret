package com.enoca.eticaret.web.cart.model;

import com.enoca.eticaret.web.customer.model.CustomerResponse;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CartResponse {
    private BigDecimal totalAmount = BigDecimal.ZERO;
    private List<String> productIds;
    private CustomerResponse customerId;
}