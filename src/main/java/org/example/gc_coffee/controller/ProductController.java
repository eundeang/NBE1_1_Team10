package org.example.gc_coffee.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.example.gc_coffee.dto.request.ProductReqDto;
import org.example.gc_coffee.dto.response.ProductResDto;
import org.example.gc_coffee.dto.common.ApiResponseDto;
import org.example.gc_coffee.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "getProductByname", description = "상품 이름으로 조회")
    @ApiResponse(responseCode = "200", description = "성공")
    @GetMapping("/{name}")
    public ApiResponseDto<ProductResDto> getProductByName(@PathVariable(value = "name") String name) {
        ProductResDto product = productService.getProductByNames(name);
        return new ApiResponseDto<>("Product retrieved successfully", product);
    }

    @Operation(summary = "getAllProducts", description = "모든 상품 조회")
    @ApiResponse(responseCode = "200", description = "성공")
    @GetMapping("/all")
    public ApiResponseDto<List<ProductResDto>> getAllProducts() {
        List<ProductResDto> products = productService.getAllProducts();
        return new ApiResponseDto<>("All products retrieved successfully", products);
    }


    @Operation(summary = "register Product", description = "상품 등록")
    @ApiResponse(responseCode = "201", description = "성공")
    @PostMapping
    public ApiResponseDto<String> registerProduct(@RequestBody ProductReqDto productReqDto) {
        productService.registerProduct(productReqDto);
        return new ApiResponseDto<>(201,"Product registered successfully", null);
    }
}