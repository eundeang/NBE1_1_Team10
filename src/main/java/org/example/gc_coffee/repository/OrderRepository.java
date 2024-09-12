package org.example.gc_coffee.repository;

import org.example.gc_coffee.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository  extends JpaRepository<Order, UUID>, DslOrderRepository{
    Optional<Order> findByEmail(String email);


}
