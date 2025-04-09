package com.example.petstore.services;

// File: CustomerServiceTest.java
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.example.petstore.dto.CustomerDTO;
import com.example.petstore.dto.CustomerResponseDTO;
import com.example.petstore.entity.Customer;
import com.example.petstore.exception.ResourceNotFoundException;
import com.example.petstore.repository.CustomerRepository;
import com.example.petstore.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private CustomerService customerService;

    @Test
    public void testCreateCustomer() {
        // Prepare input DTO (CustomerDTO contains plain-text password)
        CustomerDTO inputDTO = new CustomerDTO();
        inputDTO.setCustomerName("Pranav Chaudhari");
        inputDTO.setCustomerPassword("secret");
        inputDTO.setCustomerAddress("456 Main St");
        inputDTO.setCustomerEmail("pranav@example.com");
        inputDTO.setCustomerPhone("9876543210");

        // Prepare expected entity and response DTO (CustomerResponseDTO does not include the password)
        Customer customerEntity = new Customer();
        Customer savedCustomer = new Customer();
        CustomerResponseDTO responseDTO = new CustomerResponseDTO();
        responseDTO.setCustomerName("Pranav Chaudhari");
        responseDTO.setCustomerAddress("456 Main St");
        responseDTO.setCustomerEmail("pranav@example.com");
        responseDTO.setCustomerPhone("9876543210");

        // Define mock behavior
        when(passwordEncoder.encode("secret")).thenReturn("encodedSecret");
        // Set encoded password in DTO for mapping (simulate that encoding happened)
        inputDTO.setCustomerPassword("encodedSecret");
        when(modelMapper.map(inputDTO, Customer.class)).thenReturn(customerEntity);
        when(customerRepository.save(customerEntity)).thenReturn(savedCustomer);
        when(modelMapper.map(savedCustomer, CustomerResponseDTO.class)).thenReturn(responseDTO);

        // Execute service method
        CustomerResponseDTO result = customerService.createCustomer(inputDTO);

        // Verify and assert
        verify(customerRepository, times(1)).save(customerEntity);
        assertEquals("Pranav Chaudhari", result.getCustomerName());
    }

    @Test
    public void testGetAllCustomers() {
        // Prepare list of Customer entities
        Customer cust1 = new Customer();
        cust1.setCustomerName("Pranav Chaudhari");
        Customer cust2 = new Customer();
        cust2.setCustomerName("Some Other Name"); // You can change this if needed

        when(customerRepository.findAll()).thenReturn(Arrays.asList(cust1, cust2));

        // Prepare corresponding response DTOs
        CustomerResponseDTO dto1 = new CustomerResponseDTO();
        dto1.setCustomerName("Pranav Chaudhari");
        CustomerResponseDTO dto2 = new CustomerResponseDTO();
        dto2.setCustomerName("Some Other Name");
        when(modelMapper.map(cust1, CustomerResponseDTO.class)).thenReturn(dto1);
        when(modelMapper.map(cust2, CustomerResponseDTO.class)).thenReturn(dto2);

        // Execute service method
        List<CustomerResponseDTO> result = customerService.getAllCustomers();

        // Verify and assert
        assertEquals(2, result.size());
        assertEquals("Pranav Chaudhari", result.get(0).getCustomerName());
        assertEquals("Some Other Name", result.get(1).getCustomerName());
    }



    @Test
    public void testDeleteCustomer() {
        Customer cust = new Customer();
        cust.setCustomerId(1L);
        when(customerRepository.findById(1L)).thenReturn(Optional.of(cust));

        customerService.deleteCustomer(1L);
        verify(customerRepository, times(1)).delete(cust);
    }

    @Test
    public void testGetCustomerById() {
        Customer cust = new Customer();
        cust.setCustomerId(1L);
        cust.setCustomerName("Pranav");

        CustomerResponseDTO dto = new CustomerResponseDTO();
        dto.setCustomerName("Pranav");

        when(customerRepository.findById(1L)).thenReturn(Optional.of(cust));
        when(modelMapper.map(cust, CustomerResponseDTO.class)).thenReturn(dto);

        CustomerResponseDTO result = customerService.getCustomerById(1L);
        assertEquals("Pranav", result.getCustomerName());
    }


}
