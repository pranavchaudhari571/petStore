package com.example.petstore.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
public class PetDTO {

    @NotBlank(message = "Pet name is required")
    private String petName;

    @Positive(message = "Age must be positive")
    private Integer age;

    private String breed;
    private String gender;

    @Positive(message = "Price must be a positive number")
    private Double price;

    private Boolean available;
    private Long categoryId; // used for linking to category
}
