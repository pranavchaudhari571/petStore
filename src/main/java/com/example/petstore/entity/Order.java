package com.example.petstore.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "customer_order")  // Change the table name to avoid reserved word conflict
public class Order {

    @Id  // Primary key for Order
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto-generate the ID
    private Long orderId;

    @Temporal(TemporalType.TIMESTAMP)
    private Date orderDate;

    private Boolean returned;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "customerId")
    private Customer customer;

    @OneToOne
    @JoinColumn(name = "pet_id", referencedColumnName = "petId")
    private Pet pet;
}
