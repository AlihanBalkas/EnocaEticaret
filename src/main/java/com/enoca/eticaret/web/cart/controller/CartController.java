package com.enoca.eticaret.web.cart.controller;

import com.enoca.eticaret.web.cart.model.CartResponse;
import com.enoca.eticaret.web.cart.model.CartUpdate;
import com.enoca.eticaret.web.cart.service.impl.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping
    public ResponseEntity<List<CartResponse>> findAll(){
        return ResponseEntity.ok(cartService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartResponse> findById(@PathVariable String id){
        return ResponseEntity.ok(cartService.findById(id));
    }

    @PutMapping
    public ResponseEntity<CartResponse> update(@RequestBody CartUpdate cartUpdate){
        return ResponseEntity.ok(cartService.update(cartUpdate));
    }

}