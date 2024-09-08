package org.example.gc_coffee.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.UUID;

@Entity(name = "manager")
public class Manager extends TimeTracking {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID no;

    @Column(length = 100)
    private String id;

    @Column(length = 100)
    private String pw;
}
