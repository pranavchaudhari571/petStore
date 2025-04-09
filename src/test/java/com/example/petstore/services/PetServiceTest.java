package com.example.petstore.services;
// File: PetServiceTest.java
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.example.petstore.dto.PetDTO;
import com.example.petstore.entity.Category;
import com.example.petstore.entity.Pet;
import com.example.petstore.repository.CategoryRepository;
import com.example.petstore.repository.PetRepository;
import com.example.petstore.service.PetService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PetServiceTest {

    @Mock
    private PetRepository petRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private PetService petService;

    @Test
    public void testCreatePet() {
        // Prepare input PetDTO (assume PetDTO has fields: name, price, and categoryId)
        PetDTO inputDTO = new PetDTO();
        inputDTO.setPetName("Buddy"); // Mapping from 'name' to 'petName'
        inputDTO.setPrice(100.0);
        inputDTO.setCategoryId(1L);

        // Prepare entity objects
        Pet petEntity = new Pet();
        petEntity.setPetName("Buddy");
        petEntity.setPrice(100.0);
        Category category = new Category();
        category.setCategoryId(1L);
        category.setCategoryName("Dogs");

        // Set up repository mocks
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(modelMapper.map(inputDTO, Pet.class)).thenReturn(petEntity);
        when(petRepository.save(petEntity)).thenReturn(petEntity);
        when(modelMapper.map(petEntity, PetDTO.class)).thenReturn(inputDTO);

        PetDTO result = petService.createPet(inputDTO);
        verify(petRepository, times(1)).save(petEntity);
        assertEquals("Buddy", result.getPetName());
    }

    @Test
    public void testUpdatePetPrice() {
        Pet pet = new Pet();
        pet.setPetId(1L);
        pet.setPrice(100.0);

        when(petRepository.findById(1L)).thenReturn(Optional.of(pet));
        pet.setPrice(150.0);
        when(petRepository.save(pet)).thenReturn(pet);

        PetDTO dto = new PetDTO();
        dto.setPrice(150.0);
        when(modelMapper.map(pet, PetDTO.class)).thenReturn(dto);

        PetDTO result = petService.updatePetPrice(1L, 150.0);
        assertEquals(150.0, result.getPrice());
    }

    @Test
    public void testDeletePet() {
        Pet pet = new Pet();
        pet.setPetId(1L);
        when(petRepository.findById(1L)).thenReturn(Optional.of(pet));

        petService.deletePet(1L);
        verify(petRepository, times(1)).delete(pet);
    }

    @Test
    public void testGetPetsByCategory() {
        Category category = new Category();
        category.setCategoryName("Dogs");

        Pet pet1 = new Pet();
        pet1.setPetName("Buddy");
        Pet pet2 = new Pet();
        pet2.setPetName("Max");

        when(categoryRepository.findByCategoryName("Dogs")).thenReturn(Optional.of(category));
        when(petRepository.findByCategory(category)).thenReturn(Arrays.asList(pet1, pet2));

        PetDTO dto1 = new PetDTO();
        dto1.setPetName("Buddy");
        PetDTO dto2 = new PetDTO();
        dto2.setPetName("Max");
        when(modelMapper.map(pet1, PetDTO.class)).thenReturn(dto1);
        when(modelMapper.map(pet2, PetDTO.class)).thenReturn(dto2);

        List<PetDTO> result = petService.getPetsByCategory("Dogs");
        assertEquals(2, result.size());
        assertEquals("Buddy", result.get(0).getPetName());
        assertEquals("Max", result.get(1).getPetName());
    }

    @Test
    public void testGetPetById() {
        Pet pet = new Pet();
        pet.setPetId(1L);
        pet.setPetName("Buddy");
        when(petRepository.findById(1L)).thenReturn(Optional.of(pet));

        PetDTO dto = new PetDTO();
        dto.setPetName("Buddy");
        when(modelMapper.map(pet, PetDTO.class)).thenReturn(dto);

        PetDTO result = petService.getPetById(1L);
        assertEquals("Buddy", result.getPetName());
    }

    @Test
    public void testGetAllPets() {
        Pet pet1 = new Pet();
        pet1.setPetName("Buddy");
        Pet pet2 = new Pet();
        pet2.setPetName("Max");

        when(petRepository.findAll()).thenReturn(Arrays.asList(pet1, pet2));
        PetDTO dto1 = new PetDTO();
        dto1.setPetName("Buddy");
        PetDTO dto2 = new PetDTO();
        dto2.setPetName("Max");
        when(modelMapper.map(pet1, PetDTO.class)).thenReturn(dto1);
        when(modelMapper.map(pet2, PetDTO.class)).thenReturn(dto2);

        List<PetDTO> result = petService.getAllPets();
        assertEquals(2, result.size());
    }
}
