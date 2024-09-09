package org.example.gc_coffee.controller;

import lombok.RequiredArgsConstructor;
import org.example.gc_coffee.dto.OrderDto;
import org.example.gc_coffee.dto.response.CommonResponseDto;
import org.example.gc_coffee.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/{email}")
    public ResponseEntity<CommonResponseDto<List<OrderDto>>> getOrderByEmail(@PathVariable(value = "email") String email) {
        List<OrderDto> orderDtoList = orderService.getOrderByEmail(email);
        CommonResponseDto<List<OrderDto>> response = new CommonResponseDto<>("Order retrieved successfully", orderDtoList);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<CommonResponseDto<List<OrderDto>>> getAllOrder() {
        List<OrderDto> orders = orderService.getAllOrders();
        CommonResponseDto<List<OrderDto>> response = new CommonResponseDto<>("Orders retrieved successfully", orders);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CommonResponseDto<String>> registerOrder(@RequestBody OrderDto orderDto) {
        orderService.registerOrder(orderDto);
        CommonResponseDto<String> response = new CommonResponseDto<>("Order registered successfully \n당일 오후 2시 이후의 주문은 다음날 배송을 시작합니다.", null);
        return ResponseEntity.ok(response);
    }
}