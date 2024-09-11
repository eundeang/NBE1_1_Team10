package org.example.gc_coffee.service;

import jakarta.persistence.EntityNotFoundException;
import org.example.gc_coffee.dto.request.OrderReqDto;
import org.example.gc_coffee.dto.response.OrderResDto;
import org.example.gc_coffee.entity.Order;
import org.example.gc_coffee.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductService productService;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    void testGetOrderByEmail_ShouldReturnOrderDto_WhenOrdersExist() {
        // Given
        String email = "test@example.com";
        Order order = Order.builder()
                .id(UUID.randomUUID())
                .email(email)
                .address("Test Address")
                .postcode("12345")
                .orderStatus("PENDING")
                .orderProducts(new ArrayList<>())
                .build();

        when(orderRepository.findAllByEmailWithOrderProducts(email)).thenReturn(List.of(order));

        // When
        List<OrderResDto> result = orderService.getOrderByEmail(email);

        // Then
        assertNotNull(result);
        for (OrderResDto orderResDto : result) {
            assertEquals(email, orderResDto.getEmail());
        }
        verify(orderRepository, times(1)).findAllByEmailWithOrderProducts(email);
    }

    @Test
    void testRegisterOrder_ShouldThrowException_WhenProductNotFound() {
        // Given
        OrderReqDto orderReqDto = OrderReqDto.builder()
                .email("test@example.com")
                .address("Test Address")
                .postcode("12345")
                .orderProductList(null)
                .build();

        when(productService.getProductByIds(any())).thenReturn(Collections.emptyMap());

        // When & Then
        assertThrows(EntityNotFoundException.class, () -> orderService.registerOrder(orderReqDto));
        verify(orderRepository, never()).save(any(Order.class));
    }

    @Test
    void testGetAllOrders_ShouldReturnListOfOrderDtos_WhenOrdersExist() {
        // Given
        Order order = Order.builder()
                .id(UUID.randomUUID())
                .email("test@example.com")
                .address("Test Address")
                .postcode("12345")
                .orderStatus("PENDING")
                .orderProducts(new ArrayList<>())
                .build();

        when(orderRepository.findAllWithOrderProducts()).thenReturn(Collections.singletonList(order));

        // When
        List<OrderResDto> result = orderService.getAllOrders();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(orderRepository, times(1)).findAllWithOrderProducts();
    }
}