package org.example.gc_coffee.entity;

import static jakarta.persistence.FetchType.LAZY;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "order_product")
public class OrderProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID no;

    private int quantity;

    @ManyToOne(fetch = LAZY)
    @Column(name = "order_no")
    private Order orderNo;

    @OneToMany(mappedBy = "order_product")
    @Column(name = "product_no")
    private List<Product> productNo;

}
