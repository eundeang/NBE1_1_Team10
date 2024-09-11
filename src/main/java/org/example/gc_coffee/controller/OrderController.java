package org.example.gc_coffee.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.gc_coffee.dto.request.OrderReqDto;
import org.example.gc_coffee.dto.response.OrderResDto;
import org.example.gc_coffee.dto.common.ApiResponseDto;
import org.example.gc_coffee.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/{email}")
    public ApiResponseDto<List<OrderResDto>> getOrderByEmail(@PathVariable(value = "email") String email) {
        List<OrderResDto> orderResDtoList = orderService.getOrderByEmail(email);
        return new ApiResponseDto<>("Order retrieved successfully", orderResDtoList);
    }

    @GetMapping("/all")
    public ApiResponseDto<List<OrderResDto>> getAllOrder() {
        List<OrderResDto> orders = orderService.getAllOrders();
        return new ApiResponseDto<>("Orders retrieved successfully", orders);
    }

    @PostMapping
    public ApiResponseDto<String> registerOrder(@RequestBody @Valid OrderReqDto orderReqDto) {
        orderService.registerOrder(orderReqDto);
        return new ApiResponseDto<>(201, "Order registered successfully \n당일 오후 2시 이후의 주문은 다음날 배송을 시작합니다.", null);
    }
}