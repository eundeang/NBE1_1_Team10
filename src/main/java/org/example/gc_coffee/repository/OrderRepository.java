package org.example.gc_coffee.repository;

import org.example.gc_coffee.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository  extends JpaRepository<Order, UUID> {
    Optional<Order> findByEmail(String email);

    @Query("SELECT o FROM Order o JOIN FETCH o.orderProducts WHERE o.email = :email")
    List<Order> findAllByEmailWithOrderProducts(@Param("email") String email);

    @Query("SELECT o FROM Order o JOIN FETCH o.orderProducts")
    List<Order> findAllWithOrderProducts();
}
