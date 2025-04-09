package com.example.petstore.repository;

import com.example.petstore.entity.Pet;
import com.example.petstore.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Long> {

    // Custom query method to find pets by category
    List<Pet> findByCategory(Category category);
}
