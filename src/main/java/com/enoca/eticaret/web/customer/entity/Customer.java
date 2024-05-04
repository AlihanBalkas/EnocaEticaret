package com.enoca.eticaret.web.customer.entity;

import com.enoca.eticaret.web.cart.entity.Cart;
import com.enoca.eticaret.web.common.BaseEntity;
import com.enoca.eticaret.web.order.entity.Order;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "t_customer", language = "tr")
@Data
@EqualsAndHashCode(callSuper = true)
public class Customer extends BaseEntity {
    @NotNull(message = "İsim alanı boş geçilemez")
    @NotEmpty(message = "İsim alanı boş geçilemez")
    @NotBlank(message = "İsim alanı boş geçilemez")
    private String name;
    @NotNull(message = "Soy İsim alanı boş geçilemez")
    @NotEmpty(message = "Soy İsim alanı boş geçilemez")
    @NotBlank(message = "Soy İsim alanı boş geçilemez")
    private String lastname;
    @DBRef
    private Cart cart;
    @DBRef
    private List<Order> orders;
}
