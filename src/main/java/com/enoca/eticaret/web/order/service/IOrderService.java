package com.enoca.eticaret.web.order.service;

import com.enoca.eticaret.web.order.model.OrderResponse;

import java.util.List;

public interface IOrderService {
    List<OrderResponse> GetAllOrdersForCustomer();
}
