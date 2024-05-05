package com.enoca.eticaret.web.cart.service;

import com.enoca.eticaret.web.cart.model.CartResponse;
import com.enoca.eticaret.web.cart.model.CartUpdate;

import java.util.List;

public interface ICartService {
    List<CartResponse> findAll();
    CartResponse findById(String id);
    CartResponse update(CartUpdate cartUpdate);
    void addProductToCart(String customerId, String productId, int quantity);
}
