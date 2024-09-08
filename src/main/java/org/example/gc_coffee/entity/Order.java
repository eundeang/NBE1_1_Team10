package org.example.gc_coffee.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "order")
public class Order extends TimeTracking {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID no;

    @Column(length = 100)
    private String email;
}
