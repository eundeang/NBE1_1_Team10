package org.example.gc_coffee.service;

import org.example.gc_coffee.entity.OrderProduct;

import java.util.List;

public interface OrderProductService {

    void registerOrderProducts(List<OrderProduct> orderProducts);
}
