package com.enoca.eticaret.web.order.entity;

import com.enoca.eticaret.web.common.BaseEntity;
import com.enoca.eticaret.web.customer.entity.Customer;
import com.enoca.eticaret.web.product.entity.Product;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document(collection = "t_order", language = "tr")
@Data
@EqualsAndHashCode(callSuper = true)
public class Order extends BaseEntity {
    private String customerId;
    private List<String> productIds;
    private BigDecimal totalAmount;
    private Integer quantity;
    private LocalDateTime orderDate;
    private Integer code;
    public Order() {
        this.productIds = new ArrayList<>();
        this.totalAmount = BigDecimal.ZERO;
        this.orderDate = LocalDateTime.now();
    }
}