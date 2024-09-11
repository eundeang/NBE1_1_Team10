package org.example.gc_coffee.controller;

import lombok.RequiredArgsConstructor;
import org.example.gc_coffee.dto.OrderDto;
import org.example.gc_coffee.dto.response.ApiResponseDto;
import org.example.gc_coffee.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/{email}")
    public ApiResponseDto<List<OrderDto>> getOrderByEmail(@PathVariable(value = "email") String email) {
        List<OrderDto> orderDtoList = orderService.getOrderByEmail(email);
        return new ApiResponseDto<>("Order retrieved successfully", orderDtoList);
    }

    @GetMapping("/all")
    public ApiResponseDto<List<OrderDto>> getAllOrder() {
        List<OrderDto> orders = orderService.getAllOrders();
        return new ApiResponseDto<>("Orders retrieved successfully", orders);
    }

    @PostMapping
    public ApiResponseDto<String> registerOrder(@RequestBody OrderDto orderDto) {
        orderService.registerOrder(orderDto);
        return new ApiResponseDto<>(201, "Order registered successfully \n당일 오후 2시 이후의 주문은 다음날 배송을 시작합니다.", null);
    }
}