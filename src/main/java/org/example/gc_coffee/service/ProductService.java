package org.example.gc_coffee.service;

import org.example.gc_coffee.dto.ProductDto;
import org.example.gc_coffee.entity.Product;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface ProductService {

    List<ProductDto> getAllProducts();

    void registerProduct(ProductDto productDto);

    Map<UUID, Product> getProductByIds(List<UUID> productIds);

    ProductDto getProductByNames(String name);
}