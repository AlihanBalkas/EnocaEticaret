package com.enoca.eticaret.web.cart.entity;

import com.enoca.eticaret.web.common.BaseEntity;
import com.enoca.eticaret.web.customer.entity.Customer;
import com.enoca.eticaret.web.product.entity.Product;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.List;

@Document(collection = "t_cart", language = "tr")
@Data
@EqualsAndHashCode(callSuper = true) //sepet
public class Cart extends BaseEntity {
    private BigDecimal totalAmount = BigDecimal.ZERO;
    private int quantity;
    @DBRef
    private List<Product> products;
    @DBRef
    private Customer customer;
}