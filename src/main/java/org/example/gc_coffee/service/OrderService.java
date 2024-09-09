package org.example.gc_coffee.service;

import org.example.gc_coffee.dto.OrderDto;

import java.util.List;

public interface OrderService {

    OrderDto getOrderByEmail(String email);

    List<OrderDto> getAllOrders();

    void registerOrder(OrderDto orderDto);
}
