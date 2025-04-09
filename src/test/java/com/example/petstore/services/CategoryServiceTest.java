package com.example.petstore.services;
// File: CategoryServiceTest.java
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.example.petstore.dto.CategoryDTO;
import com.example.petstore.entity.Category;
import com.example.petstore.repository.CategoryRepository;
import com.example.petstore.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private CategoryService categoryService;

    @Test
    public void testCreateCategory() {
        // Prepare input DTO (assume CategoryDTO has a field "categoryName")
        CategoryDTO inputDTO = new CategoryDTO();
        inputDTO.setCategoryName("Dogs");

        // Prepare entity objects
        Category categoryEntity = new Category();
        categoryEntity.setCategoryName("Dogs");
        Category savedEntity = new Category();
        savedEntity.setCategoryName("Dogs");

        // Define ModelMapper behavior
        when(modelMapper.map(inputDTO, Category.class)).thenReturn(categoryEntity);
        when(categoryRepository.save(categoryEntity)).thenReturn(savedEntity);
        when(modelMapper.map(savedEntity, CategoryDTO.class)).thenReturn(inputDTO);

        // Execute service method
        CategoryDTO result = categoryService.createCategory(inputDTO);

        // Verify and assert
        verify(categoryRepository, times(1)).save(categoryEntity);
        assertEquals("Dogs", result.getCategoryName());
    }

    @Test
    public void testGetAllCategories() {
        // Create two sample Category entities
        Category cat1 = new Category();
        cat1.setCategoryName("Dogs");
        Category cat2 = new Category();
        cat2.setCategoryName("Cats");

        // Repository returns the two categories
        when(categoryRepository.findAll()).thenReturn(Arrays.asList(cat1, cat2));

        // Prepare DTOs for mapping
        CategoryDTO dto1 = new CategoryDTO();
        dto1.setCategoryName("Dogs");
        CategoryDTO dto2 = new CategoryDTO();
        dto2.setCategoryName("Cats");
        when(modelMapper.map(cat1, CategoryDTO.class)).thenReturn(dto1);
        when(modelMapper.map(cat2, CategoryDTO.class)).thenReturn(dto2);

        // Execute service method
        List<CategoryDTO> result = categoryService.getAllCategories();

        // Verify results
        assertEquals(2, result.size());
        assertEquals("Dogs", result.get(0).getCategoryName());
        assertEquals("Cats", result.get(1).getCategoryName());
    }

    @Test
    public void testDeleteCategory() {
        // Prepare a sample Category entity with an ID (categoryId)
        Category category = new Category();
        category.setCategoryId(1L);
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        // Execute deletion
        categoryService.deleteCategory(1L);

        // Verify that delete was called
        verify(categoryRepository, times(1)).delete(category);
    }
}

