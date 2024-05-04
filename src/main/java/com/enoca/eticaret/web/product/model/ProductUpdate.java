package com.enoca.eticaret.web.product.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductUpdate {
    private String productName;
    private BigDecimal price;
    private Integer stock;
}