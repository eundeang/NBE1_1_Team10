package org.example.gc_coffee.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.gc_coffee.dto.request.OrderProductReqDto;
import org.example.gc_coffee.dto.request.OrderReqDto;
import org.example.gc_coffee.dto.response.OrderResDto;
import org.example.gc_coffee.dto.response.OrderProductResDto;
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
    public List<OrderResDto> getOrderByEmail(String email) {
        try {
            // 페치 조인을 사용한 메서드를 호출하여 Order와 연관된 OrderProduct들을 한 번에 가져옴
            List<Order> orders = orderRepository.findAllByEmailWithOrderProducts(email);

            return orders.stream()
                    .map(OrderServiceImpl::buildOrderDto)
                    .toList();

        } catch (Exception e) {
            log.error("Error occurred while fetching order by email: {}", email, e);
            throw e;
        }
    }

    @Override
    public List<OrderResDto> getAllOrders() {
        try {
            // 페치 조인을 사용하여 모든 Order와 연관된 OrderProduct를 한 번에 가져옴
            List<Order> orders = orderRepository.findAllWithOrderProducts();

            // Order 엔티티들을 OrderDto로 변환
            return orders.stream()
                    .map(OrderServiceImpl::buildOrderDto)
                    .toList();
        } catch (Exception e) {
            log.error("Error occurred while fetching all orders", e);
            throw e;
        }
    }

    @Override
    @Transactional
    public void registerOrder(OrderReqDto orderReqDto) {
        try {
            Order order = Order.builder()
                    .id(UUID.randomUUID())
                    .email(orderReqDto.getEmail())
                    .address(orderReqDto.getAddress())
                    .postcode(orderReqDto.getPostcode())
                    .orderStatus("REGISTER") // TODO ENUM
                    .orderProducts(new ArrayList<>())
                    .build();

            List<UUID> productIds = orderReqDto.getOrderProductList().stream()
                    .map(OrderProductReqDto::getProductId)
                    .toList();

            Map<UUID, Product> productMap = productService.getProductByIds(productIds);

            orderReqDto.getOrderProductList().forEach(orderProductDto -> {
                Product product = productMap.get(orderProductDto.getProductId());

                if (product == null) {
                    throw new EntityNotFoundException("Product not found with ID: " + orderProductDto.getProductId());
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
            throw e;
        } catch (Exception e) {
            log.error("Error occurred while registering order", e);
            throw e;
        }
    }

    private static OrderResDto buildOrderDto(Order order) {
        try {
            return OrderResDto.builder()
                    .id(order.getId())
                    .email(order.getEmail())
                    .address(order.getAddress())
                    .postcode(order.getPostcode())
                    .orderStatus(order.getOrderStatus())
                    .orderProducts(order.getOrderProducts().stream()
                            .map(opd -> OrderProductResDto.builder()
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
