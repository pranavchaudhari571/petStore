package com.example.petstore.controller;


import com.example.petstore.dto.OrderDTO;
import com.example.petstore.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/shop")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/order/create")
    public ResponseEntity<OrderDTO> createOrder(@Valid @RequestBody OrderDTO orderDTO) {
        return ResponseEntity.status(201).body(orderService.createOrder(orderDTO));
    }

    @PutMapping("/return/{orderId}")
    public ResponseEntity<OrderDTO> returnOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.returnOrder(orderId));
    }

    @GetMapping("/get")
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/get/{orderId}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.getOrderById(orderId));
    }
}
