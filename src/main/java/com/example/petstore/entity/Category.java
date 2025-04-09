package com.example.petstore.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
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
public class Category {

    @Id  // This is the identifier for the entity
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Automatically generate IDs
    private Long categoryId;

    @NotBlank(message = "Category name is required")
    private String categoryName;
}
