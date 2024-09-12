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

            // 엔티티에서 DTO로 변환하는 메서드 사용
            return ProductResDto.from(product);
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

            // 엔티티 리스트를 DTO 리스트로 변환
            return products.stream()
                    .map(ProductResDto::from)
                    .toList();
        } catch (Exception e) {
            log.error("Error occurred while fetching all products", e);
            throw e;
        }
    }

    @Override
    public void registerProduct(ProductReqDto productReqDto) {
        try {
            // 이미 동일한 이름의 제품이 존재하는지 확인
            boolean exists = productRepository.existsByName(productReqDto.name());
            if (exists) {
                throw new AlreadyExistsException("Product with this name already exists.");
            }

            // DTO에서 엔티티로 변환하는 메서드 사용
            Product product = productReqDto.toEntity();

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

    public void modifyProduct(ProductReqDto productReqDto, UUID productId) {
        try{
            // 수정하려는 제품이 존재하는 지 확인
            boolean exists = productRepository.existsByName(productReqDto.name());
            if (!exists) {
                throw new EntityNotFoundException("Product with this name does not exist.");
            }

            if(productId == null){
                throw new EntityNotFoundException("Product id cannot be null.");
            }

            // id외의 다른 data만 변경하여 엔티티 생성
            Product product = productReqDto.toEntity(productId);

            productRepository.save(product);
            log.info("Product has been Modified Successfully with ID: {}", product.getId());
        } catch (EntityNotFoundException e) { //
            log.warn("Error editing product - Product does not exist.", e);
            throw e;
        } catch (Exception e) {
            log.error("Error occurred while editing the product.", e);
            throw e;
        }
    }

    public void deleteProduct(UUID id) {
        try{
            // 수정하려는 제품이 존재하는 지 확인 //id가 null일 경우에는?
            boolean exists = productRepository.existsById(id);
            if (!exists) {
                throw new EntityNotFoundException("Product with this name does not exist.");
            }

            productRepository.deleteById(id);
            log.info("Product has been successfully deleted with ID:: {}", id);
        } catch (EntityNotFoundException e) { //
            log.warn("Error editing product - Product does not exist.", e);
            throw e;
        } catch (Exception e) {
            log.error("Error occurred while editing the product.", e);
            throw e;
        }
    }

    @Override
    public List<Product> getProductByIds(List<UUID> productIds) {
        try {
            return productRepository.findAllById(productIds);
        } catch (Exception e) {
            log.error("Error occurred while fetching products by IDs", e);
            throw e;
        }
    }
}