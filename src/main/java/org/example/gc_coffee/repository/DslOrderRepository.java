package org.example.gc_coffee.repository;

import org.example.gc_coffee.entity.Order;

import java.util.List;

public interface DslOrderRepository {

    public List<Order> findAllByEmailWithOrderProducts(String email);
    public List<Order> findAllWithOrderProducts();
}
