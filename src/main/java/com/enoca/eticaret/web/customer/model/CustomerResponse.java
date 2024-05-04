package com.enoca.eticaret.web.customer.model;

import lombok.Data;

import java.util.List;

@Data
public class CustomerResponse {
    private String id;
    private String name;
    private String lastname;
    private String cartId;
    private List<String> orderIds;
}