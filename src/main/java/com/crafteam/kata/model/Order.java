package com.crafteam.kata.model;

import com.crafteam.kata.enums.DeliveryType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;
@Getter
@Setter
@ToString
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    @ManyToMany
    @JoinTable(
            name = "order_product",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> products;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DeliveryType deliveryType;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;


    @Column(nullable = false)
    private LocalDateTime orderAt;

    private LocalDateTime dateBeginOrder;



    private LocalDateTime dateEndOrder;

    private  String Status;

    private LocalDateTime deliveryDate;
    private String timeSlot;
    private boolean isReserved;




}
