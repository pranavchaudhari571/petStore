package com.example.petstore.service;

import com.example.petstore.dto.PetDTO;
import com.example.petstore.entity.Pet;
import com.example.petstore.entity.Category;
import com.example.petstore.repository.PetRepository;
import com.example.petstore.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PetService {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    public PetDTO createPet(PetDTO petDTO) {
        Pet pet = modelMapper.map(petDTO, Pet.class);
        Category category = categoryRepository.findById(petDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        pet.setCategory(category);
        Pet savedPet = petRepository.save(pet);
        return modelMapper.map(savedPet, PetDTO.class);
    }

    public PetDTO updatePetPrice(Long petId, Double price) {
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new RuntimeException("Pet not found"));
        pet.setPrice(price);
        Pet updatedPet = petRepository.save(pet);
        return modelMapper.map(updatedPet, PetDTO.class);
    }

    public void deletePet(Long petId) {
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new RuntimeException("Pet not found"));
        petRepository.delete(pet);
    }

    public List<PetDTO> getPetsByCategory(String categoryName) {
        Category category = categoryRepository.findByCategoryName(categoryName)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        List<Pet> pets = petRepository.findByCategory(category);
        return pets.stream().map(pet -> modelMapper.map(pet, PetDTO.class)).collect(Collectors.toList());
    }

    public PetDTO getPetById(Long id) {
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pet not found"));
        return modelMapper.map(pet, PetDTO.class);
    }

    public List<PetDTO> getAllPets() {
        List<Pet> pets = petRepository.findAll();
        return pets.stream().map(pet -> modelMapper.map(pet, PetDTO.class)).collect(Collectors.toList());
    }
}
