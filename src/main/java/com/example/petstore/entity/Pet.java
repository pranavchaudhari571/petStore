package com.example.petstore.entity;



import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long petId;

    @NotBlank(message = "Pet name is required")
    private String petName;

    @Positive(message = "Age should be positive")
    private Integer age;

    private String breed;
    private String gender;

    @Positive(message = "Price must be a positive number")
    private Double price;

    private Boolean available;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
