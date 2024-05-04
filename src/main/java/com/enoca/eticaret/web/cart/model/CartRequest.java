package com.enoca.eticaret.web.cart.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
@Data
public class CartRequest {
    private BigDecimal totalAmount = BigDecimal.ZERO;
    private List<String> products;
}