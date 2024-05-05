package com.enoca.eticaret.web.order.repository;

import com.enoca.eticaret.web.order.entity.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends MongoRepository<Order,String> {
    @Query("{ 'code' : ?0 }")
    List<Order> getOrderForCode(Integer code);
}