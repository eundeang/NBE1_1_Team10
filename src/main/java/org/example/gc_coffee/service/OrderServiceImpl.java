package org.example.gc_coffee.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.gc_coffee.dto.OrderDto;
import org.example.gc_coffee.dto.OrderProductDto;
import org.example.gc_coffee.entity.Order;
import org.example.gc_coffee.entity.OrderProduct;
import org.example.gc_coffee.entity.Product;
import org.example.gc_coffee.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductService productService;

    @Override
    public List<OrderDto> getOrderByEmail(String email) {
        try {
            // 페치 조인을 사용한 메서드를 호출하여 Order와 연관된 OrderProduct들을 한 번에 가져옴
            List<Order> orders = orderRepository.findAllByEmailWithOrderProducts(email);

            return orders.stream()
                    .map(OrderServiceImpl::buildOrderDto)
                    .toList();
        } catch (EntityNotFoundException e) {
            log.error("Order not found with email: {}", email, e);
            throw e; // 또는 적절한 사용자 정의 예외로 변경 가능
        } catch (Exception e) {
            log.error("Error occurred while fetching order by email: {}", email, e);
            throw e; // 또는 적절한 사용자 정의 예외로 변경 가능
        }
    }

    @Override
    public List<OrderDto> getAllOrders() {
        try {
            // 페치 조인을 사용하여 모든 Order와 연관된 OrderProduct를 한 번에 가져옴
            List<Order> orders = orderRepository.findAllWithOrderProducts();

            // Order 엔티티들을 OrderDto로 변환
            return orders.stream()
                    .map(OrderServiceImpl::buildOrderDto)
                    .toList();
        } catch (Exception e) {
            log.error("Error occurred while fetching all orders", e);
            throw e; // 또는 적절한 사용자 정의 예외로 변경 가능
        }
    }

    @Override
    @Transactional
    public void registerOrder(OrderDto orderDto) {
        try {
            Order order = Order.builder()
                    .id(UUID.randomUUID())
                    .email(orderDto.getEmail())
                    .address(orderDto.getAddress())
                    .postcode(orderDto.getPostcode())
                    .orderStatus(orderDto.getOrderStatus())
                    .orderProducts(new ArrayList<>())
                    .build();

            List<UUID> productIds = orderDto.getOrderProducts().stream()
                    .map(OrderProductDto::getProductId)
                    .toList();

            Map<UUID, Product> productMap = productService.getProductByIds(productIds);

            orderDto.getOrderProducts().forEach(orderProductDto -> {
                Product product = productMap.get(orderProductDto.getProductId());

                if (product == null) {
                    log.error("Product not found: {}", orderProductDto.getProductId());
                    throw new EntityNotFoundException("Product not found: " + orderProductDto.getProductId());
                }

                OrderProduct orderProduct = OrderProduct.builder()
                        .order(order)
                        .product(product)
                        .category(orderProductDto.getCategory())
                        .price(orderProductDto.getPrice())
                        .quantity(orderProductDto.getQuantity())
                        .build();
                order.addOrderProduct(orderProduct);
            });

            orderRepository.save(order);
            log.info("Order successfully registered with ID: {}", order.getId());
        } catch (EntityNotFoundException e) {
            log.error("Error during order registration - Product not found", e);
            throw e; // 또는 적절한 사용자 정의 예외로 변경 가능
        } catch (Exception e) {
            log.error("Error occurred while registering order", e);
            throw e; // 또는 적절한 사용자 정의 예외로 변경 가능
        }
    }

    private static OrderDto buildOrderDto(Order order) {
        try {
            return OrderDto.builder()
                    .id(order.getId())
                    .email(order.getEmail())
                    .address(order.getAddress())
                    .postcode(order.getPostcode())
                    .orderStatus(order.getOrderStatus())
                    .orderProducts(order.getOrderProducts().stream()
                            .map(opd -> OrderProductDto.builder()
                                    .seq(opd.getSeq())
                                    .orderId(opd.getOrder().getId())
                                    .productId(opd.getProduct().getId())
                                    .category(opd.getCategory())
                                    .price(opd.getPrice())
                                    .quantity(opd.getQuantity())
                                    .createdAt(opd.getCreatedAt())
                                    .updatedAt(opd.getUpdatedAt())
                                    .build())
                            .collect(Collectors.toList()))
                    .createdAt(order.getCreatedAt())
                    .updatedAt(order.getUpdatedAt())
                    .build();
        } catch (Exception e) {
            log.error("Error occurred while building OrderDto for order ID: {}", order.getId(), e);
            throw e;
        }
    }
}
