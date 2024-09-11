package org.example.gc_coffee.service;

import org.example.gc_coffee.dto.request.OrderReqDto;
import org.example.gc_coffee.dto.response.OrderResDto;

import java.util.List;

public interface OrderService {

    List<OrderResDto> getOrderByEmail(String email);

    List<OrderResDto> getAllOrders();

    void registerOrder(OrderReqDto orderReqDto);
}
