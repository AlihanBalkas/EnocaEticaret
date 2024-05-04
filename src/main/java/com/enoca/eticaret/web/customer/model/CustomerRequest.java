package com.enoca.eticaret.web.customer.model;

import com.enoca.eticaret.web.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
public class CustomerRequest {
    private String name;
    private String lastname;
    private String cartId;
    private List<String> orderIds;
}
