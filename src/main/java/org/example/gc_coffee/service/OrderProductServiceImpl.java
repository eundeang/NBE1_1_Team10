package org.example.gc_coffee.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.gc_coffee.entity.OrderProduct;
import org.example.gc_coffee.repository.OrderProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderProductServiceImpl implements OrderProductService {

    private final OrderProductRepository orderProductRepository;

    @Override
    public void registerOrderProducts(List<OrderProduct> orderProducts) {
        orderProductRepository.saveAll(orderProducts);

    }
}
