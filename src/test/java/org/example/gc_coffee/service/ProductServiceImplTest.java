package org.example.gc_coffee.service;

import jakarta.persistence.EntityNotFoundException;
import org.example.gc_coffee.dto.response.ProductResDto;
import org.example.gc_coffee.entity.Product;
import org.example.gc_coffee.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void testGetProductByNames_ShouldReturnProductDto_WhenProductExists() {
        // Given
        String productName = "Columbia";
        Product product = Product.builder()
                .id(UUID.randomUUID())
                .name(productName)
                .category("Coffee Beans")
                .price(16000L)
                .description("Great coffee")
                .build();

        when(productRepository.findByName(productName)).thenReturn(Optional.of(product));

        // When
        ProductResDto result = productService.getProductByNames(productName);

        // Then
        assertNotNull(result);
        assertEquals(productName, result.getName());
        verify(productRepository, times(1)).findByName(productName);
    }

    @Test
    void testGetProductByNames_ShouldThrowException_WhenProductNotFound() {
        // Given
        String productName = "NonExistentProduct";
        when(productRepository.findByName(productName)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(EntityNotFoundException.class, () -> productService.getProductByNames(productName));
        verify(productRepository, times(1)).findByName(productName);
    }

//    @Test
//    void testRegisterProduct_ShouldSaveProduct_WhenProductDoesNotExist() {
//        // Given
//        ProductResDto productResDto = ProductResDto.builder()
//                .name("Columbia Quindío")
//                .category("Coffee Beans")
//                .price(16000L)
//                .description("A unique coffee with a white wine fermentation process.")
//                .build();
//        when(productRepository.existsByName(productResDto.getName())).thenReturn(false);
//
//        // When
//        productService.registerProduct(productResDto);
//
//        // Then
//        verify(productRepository, times(1)).save(any(Product.class));
//    }
//
//    @Test
//    void testRegisterProduct_ShouldThrowException_WhenProductAlreadyExists() {
//        // Given
//        ProductResDto productResDto = ProductResDto.builder()
//                .name("Columbia Quindío")
//                .category("Coffee Beans")
//                .price(16000L)
//                .description("A unique coffee with a white wine fermentation process.")
//                .build();
//        when(productRepository.existsByName(productResDto.getName())).thenReturn(true);
//
//        // When & Then
//        assertThrows(AlreadyExistsException.class, () -> productService.registerProduct(productResDto));
//        verify(productRepository, never()).save(any(Product.class));
//    }

    @Test
    void testGetAllProducts_ShouldReturnListOfProductDtos_WhenProductsExist() {
        // Given
        Product product = Product.builder()
                .id(UUID.randomUUID())
                .name("Brazil Serra")
                .category("Coffee Beans")
                .price(16000L)
                .description("Great coffee")
                .build();

        when(productRepository.findAll()).thenReturn(Collections.singletonList(product));

        // When
        List<ProductResDto> result = productService.getAllProducts();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(productRepository, times(1)).findAll();
    }
}