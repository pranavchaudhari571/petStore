package com.example.petstore.controller;

import com.example.petstore.dto.PetDTO;
import com.example.petstore.service.PetService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/pet-api")
public class PetController {

    @Autowired
    private PetService petService;

    @PostMapping("/create")
    public ResponseEntity<?> createPet(@Valid @RequestBody PetDTO petDTO) {
        return ResponseEntity.status(201).body(petService.createPet(petDTO));
    }

    @PutMapping("/update-price/{id}/{price}")
    public ResponseEntity<?> updatePetPrice(@PathVariable Long id, @PathVariable Double price) {
        return ResponseEntity.ok(petService.updatePetPrice(id, price));
    }

    @DeleteMapping("/delete/{petId}")
    public ResponseEntity<?> deletePet(@PathVariable Long petId) {
        petService.deletePet(petId);
        return ResponseEntity.status(204).build();
    }

    @GetMapping("/getByCategory/{category}")
    public ResponseEntity<List<PetDTO>> getPetsByCategory(@PathVariable String category) {
        return ResponseEntity.ok(petService.getPetsByCategory(category));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<PetDTO> getPetById(@PathVariable Long id) {
        return ResponseEntity.ok(petService.getPetById(id));
    }

    @GetMapping("/get")
    public ResponseEntity<List<PetDTO>> getAllPets() {
        return ResponseEntity.ok(petService.getAllPets());
    }
}
        