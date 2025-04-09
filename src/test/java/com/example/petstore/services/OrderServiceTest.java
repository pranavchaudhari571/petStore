package com.example.petstore.services;

// File: OrderServiceTest.java
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.example.petstore.dto.OrderDTO;
import com.example.petstore.entity.Customer;
import com.example.petstore.entity.Order;
import com.example.petstore.entity.Pet;
import com.example.petstore.repository.CustomerRepository;
import com.example.petstore.repository.OrderRepository;
import com.example.petstore.repository.PetRepository;
import com.example.petstore.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private PetRepository petRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private OrderService orderService;

    @Test
    public void testCreateOrder() {
        // Prepare input OrderDTO with customerId and petId (assumed to be part of OrderDTO)
        OrderDTO inputDTO = new OrderDTO();
        inputDTO.setCustomerId(1L);
        inputDTO.setPetId(2L);

        // Prepare corresponding entities for customer and pet
        Customer customer = new Customer();
        customer.setCustomerId(1L);
        customer.setCustomerName("Pranav Chaudhari");
        Pet pet = new Pet();
        pet.setPetId(2L);
        pet.setPetName("Buddy");

        // Mock repository calls for customer and pet lookup
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(petRepository.findById(2L)).thenReturn(Optional.of(pet));

        // Prepare Order entity and saved Order entity
        Order orderEntity = new Order();
        Order savedOrder = new Order();

        // Define mapping behavior
        when(modelMapper.map(inputDTO, Order.class)).thenReturn(orderEntity);
        when(orderRepository.save(orderEntity)).thenReturn(savedOrder);
        // For simplicity, assume the output DTO is identical to inputDTO
        when(modelMapper.map(savedOrder, OrderDTO.class)).thenReturn(inputDTO);

        // Execute service method
        OrderDTO result = orderService.createOrder(inputDTO);
        verify(orderRepository, times(1)).save(orderEntity);
        assertEquals(1L, result.getCustomerId());
        assertEquals(2L, result.getPetId());
    }

    @Test
    public void testReturnOrder() {
        Order order = new Order();
        order.setReturned(false);
        order.setOrderId(1L);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderRepository.save(order)).thenReturn(order);

        OrderDTO dto = new OrderDTO();
        when(modelMapper.map(order, OrderDTO.class)).thenReturn(dto);

        OrderDTO result = orderService.returnOrder(1L);
        // Verify that the returned flag is set to true
        assertEquals(true, order.getReturned());
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    public void testGetAllOrders() {
        Order order1 = new Order();
        Order order2 = new Order();
        when(orderRepository.findAll()).thenReturn(Arrays.asList(order1, order2));

        OrderDTO dto1 = new OrderDTO();
        OrderDTO dto2 = new OrderDTO();
        when(modelMapper.map(order1, OrderDTO.class)).thenReturn(dto1);
        when(modelMapper.map(order2, OrderDTO.class)).thenReturn(dto2);

        List<OrderDTO> result = orderService.getAllOrders();
        assertEquals(2, result.size());
    }

    @Test
    public void testGetOrderById() {
        Order order = new Order();
        order.setOrderId(1L);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        OrderDTO dto = new OrderDTO();
        when(modelMapper.map(order, OrderDTO.class)).thenReturn(dto);

        OrderDTO result = orderService.getOrderById(1L);
        assertEquals(dto, result);
    }
}
