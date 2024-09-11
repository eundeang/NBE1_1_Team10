package org.example.gc_coffee.repository;

import org.example.gc_coffee.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    boolean existsByName(String name);

    Optional<Product> findByName(String name);
}
