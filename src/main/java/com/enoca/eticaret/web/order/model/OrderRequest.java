package com.enoca.eticaret.web.order.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderRequest {
    private String customerId;
    private List<String> productIds;
    private BigDecimal totalAmount;
    private Integer quantity;
    private LocalDateTime orderDate;
    private Integer code;
}