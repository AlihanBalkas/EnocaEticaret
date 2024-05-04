package com.enoca.eticaret.web.cart.repository;

import com.enoca.eticaret.web.cart.entity.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends MongoRepository<Cart,String> {
}