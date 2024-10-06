package com.ru.rudov.coffeeMachine.controllers;

import com.ru.rudov.coffeeMachine.dtos.PopularRecipeDTO;
import com.ru.rudov.coffeeMachine.models.Order;
import com.ru.rudov.coffeeMachine.services.OrderService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Operation(summary = "Make an order")
    @PostMapping("/{recipeName}")
    public void makeOrder(@Parameter(description = "Name of the recipe to order", required = true) @PathVariable String recipeName) {
        orderService.makeOrder(recipeName);
    }

    @Operation(summary = "Get all orders with pagination")
    @GetMapping("/all")
    public Page<Order> getAllOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){
        return orderService.getAllOrders(page,size);
    }
}
