package com.example.petstore.service;


import com.example.petstore.dto.OrderDTO;
import com.example.petstore.entity.Order;
import com.example.petstore.entity.Customer;
import com.example.petstore.entity.Pet;
import com.example.petstore.repository.OrderRepository;
import com.example.petstore.repository.CustomerRepository;
import com.example.petstore.repository.PetRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private ModelMapper modelMapper;

    public OrderDTO createOrder(OrderDTO orderDTO) {
        // Create shallow references (no DB fetch)
        Customer customerRef = new Customer();
        customerRef.setCustomerId(orderDTO.getCustomerId());

        Pet petRef = new Pet();
        petRef.setPetId(orderDTO.getPetId());

        // Manually create Order object to avoid ModelMapper conflicts
        Order order = new Order();
        order.setCustomer(customerRef);
        order.setPet(petRef);
        order.setOrderDate(new Date());
        order.setReturned(false);

        Order savedOrder = orderRepository.save(order);

        // Map back to DTO
        OrderDTO responseDTO = new OrderDTO();
//        responseDTO.setOrderId(savedOrder.getOrderId());
        responseDTO.setCustomerId(savedOrder.getCustomer().getCustomerId());
        responseDTO.setPetId(savedOrder.getPet().getPetId());
        responseDTO.setOrderDate(savedOrder.getOrderDate());
        responseDTO.setReturned(savedOrder.getReturned());

        return responseDTO;
    }


    public OrderDTO returnOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setReturned(true);
        Order updatedOrder = orderRepository.save(order);
        return modelMapper.map(updatedOrder, OrderDTO.class);
    }

    public List<OrderDTO> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map(order -> modelMapper.map(order, OrderDTO.class)).collect(Collectors.toList());
    }

    public OrderDTO getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return modelMapper.map(order, OrderDTO.class);
    }
}

