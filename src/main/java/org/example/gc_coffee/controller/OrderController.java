package org.example.gc_coffee.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @Operation(summary = "getOrderByEmail", description = "Email을 통한 주문 검색")
    @ApiResponse(responseCode = "200", description = "성공")
    @GetMapping("/{email}")
    public ApiResponseDto<List<OrderResDto>> getOrderByEmail(@PathVariable(value = "email") String email) {
        List<OrderResDto> orderResDtoList = orderService.getOrderByEmail(email);
        return new ApiResponseDto<>("Order retrieved successfully", orderResDtoList);
    }

    @Operation(summary = "getAllOrder", description = "모든 주문 데이터 조회")
    @ApiResponse(responseCode = "200", description = "성공")
    @GetMapping("/all")
    public ApiResponseDto<List<OrderResDto>> getAllOrder() {
        List<OrderResDto> orders = orderService.getAllOrders();
        return new ApiResponseDto<>("Orders retrieved successfully", orders);
    }

    @Operation(summary = "registerOrder", description = "주문 데이터 생성")
    @ApiResponse(responseCode = "201", description = "성공")
    @PostMapping
    public ApiResponseDto<String> registerOrder(@RequestBody @Valid OrderReqDto orderReqDto) {
        orderService.registerOrder(orderReqDto);
        return new ApiResponseDto<>(201, "Order registered successfully \n당일 오후 2시 이후의 주문은 다음날 배송을 시작합니다.", null);
    }
}