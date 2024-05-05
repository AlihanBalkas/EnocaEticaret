package com.enoca.eticaret.web.order.mapper;

import com.enoca.eticaret.web.order.entity.Order;
import com.enoca.eticaret.web.order.model.OrderRequest;
import com.enoca.eticaret.web.order.model.OrderResponse;
import com.enoca.eticaret.web.product.entity.Product;
import com.enoca.eticaret.web.product.model.ProductResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderResponse mapResponseToEntity(Order order);
    Order mapRequestToEntity(OrderRequest orderRequest);
    List<OrderResponse> mapResponseToEntityList(List<Order> orders);
}