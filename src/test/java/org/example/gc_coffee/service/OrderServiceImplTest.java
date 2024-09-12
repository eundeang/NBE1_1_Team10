package org.example.gc_coffee.service;

import static org.example.gc_coffee.dto.common.OrderStatus.ORDER_PLACED;

import jakarta.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.aspectj.lang.annotation.Before;
import org.example.gc_coffee.dto.EmailGenerator;
import org.example.gc_coffee.dto.request.OrderProductReqDto;
import org.example.gc_coffee.dto.request.OrderReqDto;
import org.example.gc_coffee.dto.request.ProductReqDto;
import org.example.gc_coffee.entity.Order;
import org.example.gc_coffee.repository.OrderRepository;
import org.example.gc_coffee.repository.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("OrderServiceImpl 클래스")
class OrderServiceImplTest {
    String email = EmailGenerator.generateRandomValidEmail();

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductService productService;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Nested
    @DisplayName("OrderByEmail 메소드는")
    class DescribeOrderByEmail {

        @Nested
        @DisplayName("이메일에 해당하는 주문이 없는 경우")
        class ContextGetOrderByInvalidEmail {
            @Test
            @DisplayName("빈 배열을 리턴한다.")
            void ItReturnsAnEmptyList() {
                Assertions.assertTrue(orderService.getOrderByEmail(email).isEmpty());
            }
        }

        @Nested
        @DisplayName("이메일에 해당하는 주문이 있는 경우")
        class ContextGetOrderByValidEmail {
            @Test
            @DisplayName("해당 이메일이 지정한 배열을 리턴한다.")
            void ItReturnsNotEmptyArray() {
                orderRepository.save(
                        Order.builder()
                                .email(email)
                                .build()
                );
                Assertions.assertEquals(orderRepository.findAllByEmailWithOrderProducts(email),
                        orderService.getOrderByEmail(email));
            }
        }
    }

    @Nested
    @DisplayName("getAllOrders 메소드는")
    class DescribeGetAllOrder {
        @Nested
        @DisplayName("주문이 하나도 없는 경우")
        class ContextNoOrder {
            @Test
            @DisplayName("빈 배열을 리턴한다.")
            void ItReturnsAnEmptyList() {
                Assertions.assertTrue(orderService.getAllOrders().isEmpty());
            }
        }

        @Nested
        @DisplayName("이메일에 해당하는 주문이 있는 경우")
        class ContextExistOrder {

            @BeforeEach
            void saveOrder() {
                Order order = Order.builder()
                        .email(email)
                        .address("주소")
                        .postcode("12322")
                        .orderStatus(ORDER_PLACED)
                        .build();
                orderRepository.save(order);
            }

            @AfterEach
            void DeleteAllOrders() {
                orderRepository.deleteAll();
            }

            @Test
            @DisplayName("저장된 모든 주문 배열을 리턴한다.")
            void ItReturnsNotEmptyArray() {
                orderRepository.save(
                        Order.builder()
                                .email(email)
                                .build()
                );
                Assertions.assertEquals(orderRepository.findAll(),
                        orderService.getOrderByEmail(email));
            }
        }
    }

    @Nested
    @DisplayName("registerOrder 메소드는")
    class DescribeRegisterOrder {
        @Nested
        @DisplayName("주문 내역에 물품이 하나도 없을 경우")
        class ContextNoItem {
            OrderReqDto orderReqDto;
            @BeforeEach
            void makeInvalidRequest() {
                OrderProductReqDto orderProductReqDto =OrderProductReqDto.of(
                        UUID.randomUUID(),
                        "커피",
                        12000L,
                        1
                );
                List<OrderProductReqDto> orderProductReqDtoList = new ArrayList<>();
                orderProductReqDtoList.add(orderProductReqDto);
                orderReqDto = OrderReqDto.of(
                        email,
                        "주소입니다",
                        "12321",
                        orderProductReqDtoList

                );
            }
            @AfterEach
            void deleteOrder() {
                productRepository.deleteAll();
                orderRepository.deleteAll();
            }

            @Test
            @DisplayName("EntityNotFoundException을 리턴한다.")
            void ItReturnsAnEmptyList() {
                Assertions.assertThrows(EntityNotFoundException.class, () -> orderService.registerOrder(orderReqDto));
            }
        }

        @Nested
        @DisplayName("이메일에 해당하는 주문이 있는 경우")
        class ContextExistOrder {

            OrderReqDto orderReqDto;

            @BeforeEach
            void makeValidRequest() {
                ProductReqDto productReqDto = ProductReqDto.of(
                     "콜롬비아 원두",
                        "원두",
                        10000L,
                        "참 힘들아여"
                );
                productService.registerProduct(productReqDto);

                OrderProductReqDto orderProductReqDto =OrderProductReqDto.of(
                        UUID.randomUUID(),
                        "커피",
                        12000L,
                        1
                );
                List<OrderProductReqDto> orderProductReqDtoList = new ArrayList<>();
                orderProductReqDtoList.add(orderProductReqDto);
                orderReqDto = OrderReqDto.of(
                        email,
                        "주소입니다",
                        "12321",
                        orderProductReqDtoList
                );
                orderService.registerOrder(orderReqDto);
            }


            @AfterEach
            void deleteProductAndOrder() {
                productRepository.deleteAll();
                orderRepository.deleteAll();
            }
            @Test
            @DisplayName("저장된 모든 주문 배열을 리턴한다.")
            void ItReturnsNotEmptyArray() {
                Assertions.assertEquals(orderRepository.findAll(),
                        orderService.getOrderByEmail(email));
            }
        }
    }
}