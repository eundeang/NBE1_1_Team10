package org.example.gc_coffee.controller;

import lombok.RequiredArgsConstructor;
import org.example.gc_coffee.dto.ProductDto;
import org.example.gc_coffee.dto.response.ApiResponseDto;
import org.example.gc_coffee.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/{name}")
    public ApiResponseDto<ProductDto> getProductByName(@PathVariable(value = "name") String name) {
        ProductDto product = productService.getProductByNames(name);
        return new ApiResponseDto<>("Product retrieved successfully", product);
    }

    @GetMapping("/all")
    public ApiResponseDto<List<ProductDto>> getAllProducts() {
        List<ProductDto> products = productService.getAllProducts();
        return new ApiResponseDto<>("All products retrieved successfully", products);
    }

    @PostMapping
    public ApiResponseDto<String> registerProduct(@RequestBody ProductDto productDto) {
        productService.registerProduct(productDto);
        return new ApiResponseDto<>(201,"Product registered successfully", null);
    }
}