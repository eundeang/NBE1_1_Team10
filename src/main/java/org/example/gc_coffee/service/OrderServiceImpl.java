package org.example.gc_coffee.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.gc_coffee.dto.request.OrderProductReqDto;
import org.example.gc_coffee.dto.request.OrderReqDto;
import org.example.gc_coffee.dto.response.OrderResDto;
import org.example.gc_coffee.entity.Order;
import org.example.gc_coffee.entity.OrderProduct;
import org.example.gc_coffee.entity.Product;
import org.example.gc_coffee.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final OrderProductService orderProductService;

    @Override
    public List<OrderResDto> getOrderByEmail(String email) {
        try {
            // 페치 조인을 사용한 메서드를 호출하여 Order와 연관된 OrderProduct들을 한 번에 가져옴
            List<Order> orders = orderRepository.findAllByEmailWithOrderProducts(email);

            return orders.stream()
                    .map(OrderResDto::from) // DTO의 정적 메서드 사용
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

            // Order 엔티티들을 OrderResDto로 변환
            return orders.stream()
                    .map(OrderResDto::from) // DTO의 정적 메서드 사용
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
            Order order = orderReqDto.toEntity();
            orderRepository.save(order);

            // 상품 ID 목록 추출
            List<UUID> productIds = orderReqDto.orderProductList().stream()
                    .map(OrderProductReqDto::productId)
                    .toList();

            // 상품 ID로 Product 엔티티 조회
            List<Product> products = productService.getProductByIds(productIds);

            // Product 엔티티를 Map 형태로 변환하여 매핑
            Map<UUID, Product> productMap = products.stream()
                    .collect(Collectors.toMap(Product::getId, product -> product));

            // OrderProduct 리스트 생성
            List<OrderProduct> orderProductEntities = orderReqDto.orderProductList().stream()
                    .map(orderProductDto -> {
                        Product product = productMap.get(orderProductDto.productId());

                        if (product == null) {
                            throw new EntityNotFoundException("Product not found with ID: " + orderProductDto.productId());
                        }

                        return orderProductDto.toEntity(order, product); // DTO의 toEntity 메서드 사용
                    })
                    .collect(Collectors.toList());

            // OrderReqDto의 toEntity 메서드를 사용하여 Order 엔티티 생성
//            Order order = orderReqDto.toEntity(orderProductEntities); // DTO의 toEntity 메서드 사용

            // 주문 상품 저장
            orderProductService.registerOrderProducts(orderProductEntities);

            log.info("Order successfully registered with ID: {}", order.getId());
        } catch (EntityNotFoundException e) {
            log.error("Error during order registration - Product not found", e);
            throw e;
        } catch (Exception e) {
            log.error("Error occurred while registering order", e);
            throw e;
        }
    }
}