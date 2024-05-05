package com.enoca.eticaret.web.order.service.impl;

import com.enoca.eticaret.web.order.entity.Order;
import com.enoca.eticaret.web.order.mapper.OrderMapper;
import com.enoca.eticaret.web.order.model.OrderRequest;
import com.enoca.eticaret.web.order.model.OrderResponse;
import com.enoca.eticaret.web.order.repository.OrderRepository;
import com.enoca.eticaret.web.order.service.IOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService implements IOrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Override
    public List<OrderResponse> GetAllOrdersForCustomer() {
        List<Order> orders = orderRepository.findAll();
        return orderMapper.mapResponseToEntityList(orders);
    }

    @Override
    public OrderResponse GetOrderForCode(Integer code) {
        Order order = orderRepository.GetOrderForCode(code);
        return orderMapper.mapResponseToEntity(order);
    }


}