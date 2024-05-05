package com.enoca.eticaret.web.product.entity;

import com.enoca.eticaret.web.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document(collection = "t_product", language = "tr")
@Data
@EqualsAndHashCode(callSuper = true)
public class Product extends BaseEntity {
    private String productName;
    private BigDecimal price;
    private Integer stock;
    private Integer Quantity;
}