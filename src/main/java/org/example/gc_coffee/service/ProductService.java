package org.example.gc_coffee.service;

import org.example.gc_coffee.dto.request.ProductReqDto;
import org.example.gc_coffee.dto.response.ProductResDto;
import org.example.gc_coffee.entity.Product;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface ProductService {

    List<ProductResDto> getAllProducts();

    void registerProduct(ProductReqDto productReqDto);

    Map<UUID, Product> getProductByIds(List<UUID> productIds);

    ProductResDto getProductByNames(String name);
}