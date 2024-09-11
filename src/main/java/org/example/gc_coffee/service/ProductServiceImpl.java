package org.example.gc_coffee.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.gc_coffee.Exception.AlreadyExistsException;
import org.example.gc_coffee.dto.request.ProductReqDto;
import org.example.gc_coffee.dto.response.ProductResDto;
import org.example.gc_coffee.entity.Product;
import org.example.gc_coffee.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public ProductResDto getProductByNames(String name) {
        try {
            Product product = productRepository.findByName(name)
                    .orElseThrow(EntityNotFoundException::new);

            return ProductResDto.builder()
                    .id(product.getId())
                    .name(product.getName())
                    .category(product.getCategory())
                    .price(product.getPrice())
                    .description(product.getDescription())
                    .createdAt(product.getCreatedAt())
                    .updatedAt(product.getUpdatedAt())
                    .build();
        } catch (EntityNotFoundException e) {
            log.error("Product not found with name: {}", name, e);
            throw e;
        } catch (Exception e) {
            log.error("Error occurred while fetching product by name: {}", name, e);
            throw e;
        }
    }

    @Override
    public List<ProductResDto> getAllProducts() {
        try {
            List<Product> products = productRepository.findAll();

            return products.stream()
                    .map(product -> ProductResDto.builder()
                            .id(product.getId())
                            .name(product.getName())
                            .category(product.getCategory())
                            .price(product.getPrice())
                            .description(product.getDescription())
                            .createdAt(product.getCreatedAt())
                            .updatedAt(product.getUpdatedAt())
                            .build())
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error occurred while fetching all products", e);
            throw e;
        }
    }

    // 서비스 클래스 내 메서드
    @Override
    public void registerProduct(ProductReqDto productReqDto) {
        try {
            // 이미 동일한 이름의 제품이 존재하는지 확인
            boolean exists = productRepository.existsByName(productReqDto.getName());
            if (exists) {
                throw new AlreadyExistsException("Product with this name already exists.");
            }

            Product product = Product.builder()
                    .id(UUID.randomUUID()) // 새 제품 등록이므로 새로운 UUID 생성
                    .name(productReqDto.getName())
                    .category(productReqDto.getCategory())
                    .price(productReqDto.getPrice())
                    .description(productReqDto.getDescription())
                    .build();

            productRepository.save(product);
            log.info("Product registered successfully with ID: {}", product.getId());
        } catch (AlreadyExistsException e) { // 중복 제품 등록
            log.warn("Error during product registration - duplicate product name", e);
            throw e;
        } catch (Exception e) {
            log.error("Error occurred while registering product", e);
            throw e;
        }
    }

    @Override
    public Map<UUID, Product> getProductByIds(List<UUID> productIds) {
        try {
            List<Product> products = productRepository.findAllById(productIds);
            return products.stream()
                    .collect(Collectors.toMap(Product::getId, product -> product));
        } catch (Exception e) {
            log.error("Error occurred while fetching products by IDs", e);
            throw e;
        }
    }
}