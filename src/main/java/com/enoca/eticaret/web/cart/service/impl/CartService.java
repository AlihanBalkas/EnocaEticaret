package com.enoca.eticaret.web.cart.service.impl;

import com.enoca.eticaret.web.cart.entity.Cart;
import com.enoca.eticaret.web.cart.mapper.CartMapper;
import com.enoca.eticaret.web.cart.model.CartResponse;
import com.enoca.eticaret.web.cart.model.CartUpdate;
import com.enoca.eticaret.web.cart.repository.CartRepository;
import com.enoca.eticaret.web.cart.service.ICartService;
import com.enoca.eticaret.web.common.exception.CustomNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartService implements ICartService {

    private final CartRepository cartRepository;
    private final CartMapper cartMapper;

    @Override
    public List<CartResponse> findAll() {
        List<Cart> carts = cartRepository.findAll();
        return cartMapper.mapResponseToEntityList(carts);
    }

    @Override
    public CartResponse findById(String id) {
        Cart cart = cartRepository.findById(id).orElseThrow(()-> new CustomNotFoundException(""));
        return cartMapper.mapResponseToEntity(cart);
    }

    @Override
    public CartResponse update(CartUpdate cartUpdate) {
        Cart cart = cartMapper.mapUpdateToEntity(cartUpdate);
        Cart savedEntity = cartRepository.save(cart);
        return cartMapper.mapResponseToEntity(savedEntity);
    }
}
