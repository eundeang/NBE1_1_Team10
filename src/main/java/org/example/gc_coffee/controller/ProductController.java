package org.example.gc_coffee.controller;

import lombok.RequiredArgsConstructor;
import org.example.gc_coffee.dto.ProductDto;
import org.example.gc_coffee.dto.response.CommonResponseDto;
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
    public ResponseEntity<CommonResponseDto<ProductDto>> getProductByName(@PathVariable(value = "name") String name) {
        ProductDto product = productService.getProductByNames(name);
        CommonResponseDto<ProductDto> response = new CommonResponseDto<>("Product retrieved successfully", product);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<CommonResponseDto<List<ProductDto>>> getAllProducts() {
        List<ProductDto> products = productService.getAllProducts();
        CommonResponseDto<List<ProductDto>> response = new CommonResponseDto<>("All products retrieved successfully", products);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CommonResponseDto<String>> registerProduct(@RequestBody ProductDto productDto) {
        productService.registerProduct(productDto);
        CommonResponseDto<String> response = new CommonResponseDto<>("Product registered successfully", null);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}