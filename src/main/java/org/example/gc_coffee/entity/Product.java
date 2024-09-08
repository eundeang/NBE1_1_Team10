package org.example.gc_coffee.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "product")
public class Product extends TimeTracking {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID no;

    private String name;

    private Integer price;
}
