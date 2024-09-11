package org.example.gc_coffee.controller;

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

    @GetMapping("/{name}")
    public ApiResponseDto<ProductResDto> getProductByName(@PathVariable(value = "name") String name) {
        ProductResDto product = productService.getProductByNames(name);
        return new ApiResponseDto<>("Product retrieved successfully", product);
    }

    @GetMapping("/all")
    public ApiResponseDto<List<ProductResDto>> getAllProducts() {
        List<ProductResDto> products = productService.getAllProducts();
        return new ApiResponseDto<>("All products retrieved successfully", products);
    }

    @PostMapping
    public ApiResponseDto<String> registerProduct(@RequestBody ProductReqDto productReqDto) {
        productService.registerProduct(productReqDto);
        return new ApiResponseDto<>(201,"Product registered successfully", null);
    }
}