package com.ru.rudov.coffeeMachine.controllers;

import com.ru.rudov.coffeeMachine.dtos.OrderDTO;
import com.ru.rudov.coffeeMachine.models.Order;
import com.ru.rudov.coffeeMachine.services.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    private final OrderService orderService;
    private final ModelMapper modelMapper;
    @Autowired
    public OrderController(OrderService orderService, ModelMapper modelMapper) {
        this.orderService = orderService;
        this.modelMapper = modelMapper;
    }

    @Operation(summary = "Make an order")
    @PostMapping("/{recipeName}")
    public void makeOrder(@Parameter(description = "Name of the recipe to order", required = true) @PathVariable String recipeName) {
        orderService.makeOrder(recipeName);
    }

    @Operation(summary = "Get all orders")
    @GetMapping("/all")
    public List<OrderDTO> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return orders.stream()
                .map(order -> modelMapper.map(order, OrderDTO.class))
                .collect(Collectors.toList());
    }
}
