package org.example.gc_coffee.service;

import jakarta.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.example.gc_coffee.dto.EmailGenerator;
import org.example.gc_coffee.dto.request.OrderProductReqDto;
import org.example.gc_coffee.dto.request.OrderReqDto;
import org.example.gc_coffee.dto.request.ProductReqDto;
import org.example.gc_coffee.entity.Product;
import org.example.gc_coffee.repository.OrderRepository;
import org.example.gc_coffee.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@DisplayName("OrderServiceImpl 클래스")
class OrderServiceImplTest {
    private static final Logger log = LoggerFactory.getLogger(OrderServiceImplTest.class);
    String email = EmailGenerator.generateRandomValidEmail();

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderServiceImpl orderService;

    @Nested
    @DisplayName("OrderByEmail 메소드는")
    class DescribeOrderByEmail {

        void saveOrder() {
            ProductReqDto productReqDto = ProductReqDto.of(
                    "콜롬비아 원두",
                    "원두",
                    10000L,
                    "참 힘들아여"
            );
            productService.registerProduct(productReqDto);
            Product product = productRepository.findByName("콜롬비아 원두").orElseThrow();

            OrderProductReqDto orderProductReqDto = OrderProductReqDto.of(
                    product.getId(),
                    "커피",
                    12000L,
                    1
            );
            List<OrderProductReqDto> orderProductReqDtoList = new ArrayList<>();
            orderProductReqDtoList.add(orderProductReqDto);
            OrderReqDto orderReqDto = OrderReqDto.of(
                    email,
                    "주소입니다",
                    "12321",
                    orderProductReqDtoList
            );
            orderService.registerOrder(orderReqDto);
        }

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
                saveOrder();
                Assertions.assertNotNull(orderRepository.findAllByEmailWithOrderProducts(email));
            }
        }
    }

    @Nested
    @DisplayName("getAllOrders 메소드는")
    class DescribeGetAllOrder {
        void saveOrder() {
            ProductReqDto productReqDto = ProductReqDto.of(
                    "콜롬비아 원두",
                    "원두",
                    10000L,
                    "참 힘들아여"
            );
            productService.registerProduct(productReqDto);
            Product product = productRepository.findByName("콜롬비아 원두").orElseThrow();

            OrderProductReqDto orderProductReqDto = OrderProductReqDto.of(
                    product.getId(),
                    "커피",
                    12000L,
                    1
            );
            List<OrderProductReqDto> orderProductReqDtoList = new ArrayList<>();
            orderProductReqDtoList.add(orderProductReqDto);
            OrderReqDto orderReqDto = OrderReqDto.of(
                    email,
                    "주소입니다",
                    "12321",
                    orderProductReqDtoList
            );
            orderService.registerOrder(orderReqDto);
        }

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
        @DisplayName("주문이 있는 경우")
        class ContextExistOrder {
            @Test
            @DisplayName("저장된 모든 주문 배열을 리턴한다.")
            void ItReturnsNotEmptyArray() {
                saveOrder();
                Assertions.assertNotNull(orderRepository.findAll());
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
                OrderProductReqDto orderProductReqDto = OrderProductReqDto.of(
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


            @Test
            @DisplayName("EntityNotFoundException을 리턴한다.")
            void ItReturnsAnEmptyList() {
                Assertions.assertThrows(EntityNotFoundException.class, () -> orderService.registerOrder(orderReqDto));
            }
        }

        @Nested

        @DisplayName("이메일에 해당하는 주문이 있는 경우")
        class ContextExistOrder {

            @Test
            @DisplayName("저장된 모든 주문 배열을 리턴한다.")
            void ItReturnsNotEmptyArray() {
                Assertions.assertEquals(orderRepository.findAll(),
                        orderService.getOrderByEmail(email));
            }
        }
    }
}