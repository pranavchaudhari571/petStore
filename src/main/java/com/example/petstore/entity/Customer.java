package com.example.petstore.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;


import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;

    @NotBlank(message = "Customer name is required")
    private String customerName;

    @NotBlank(message = "Password is required")
    private String customerPassword;

    @NotBlank(message = "Customer address is required")
    private String customerAddress;

    @Email(message = "Invalid email format")
    private String customerEmail;

    @Pattern(regexp = "^[0-9]{10}$", message = "Invalid phone number")
    private String customerPhone;

    @OneToMany(mappedBy = "customer",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders;
}

