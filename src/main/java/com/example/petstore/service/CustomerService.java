package com.example.petstore.service;

import com.example.petstore.dto.CustomerDTO;
import com.example.petstore.dto.CustomerResponseDTO;
import com.example.petstore.entity.Customer;
import com.example.petstore.repository.CustomerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Create customer with encoded password and return response DTO
    public CustomerResponseDTO createCustomer(CustomerDTO customerDTO) {
        // Encode the password before saving
        customerDTO.setCustomerPassword(passwordEncoder.encode(customerDTO.getCustomerPassword()));
        // Map the DTO to the entity (assuming you have a Customer entity)
        Customer customer = modelMapper.map(customerDTO, Customer.class);
        // Save the entity
        Customer savedCustomer = customerRepository.save(customer);
        // Map the saved entity to a response DTO to hide sensitive fields
        return modelMapper.map(savedCustomer, CustomerResponseDTO.class);
    }

    // Update customer details and password if provided
    public CustomerResponseDTO updateCustomer(Long customerId, CustomerDTO customerDTO) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        customer.setCustomerName(customerDTO.getCustomerName());
        customer.setCustomerAddress(customerDTO.getCustomerAddress());
        customer.setCustomerEmail(customerDTO.getCustomerEmail());
        customer.setCustomerPhone(customerDTO.getCustomerPhone());

        // Update password if provided (check for null or empty)
        if (customerDTO.getCustomerPassword() != null && !customerDTO.getCustomerPassword().isEmpty()) {
            customer.setCustomerPassword(passwordEncoder.encode(customerDTO.getCustomerPassword()));
        }
        Customer updatedCustomer = customerRepository.save(customer);
        return modelMapper.map(updatedCustomer, CustomerResponseDTO.class);
    }

    // Delete a customer
    public void deleteCustomer(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        customerRepository.delete(customer);
    }

    // Get all customers (returning response DTOs)
    public List<CustomerResponseDTO> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream()
                .map(customer -> modelMapper.map(customer, CustomerResponseDTO.class))
                .collect(Collectors.toList());
    }

    // Get customer by ID (returning response DTO)
    public CustomerResponseDTO getCustomerById(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        return modelMapper.map(customer, CustomerResponseDTO.class);
    }
}