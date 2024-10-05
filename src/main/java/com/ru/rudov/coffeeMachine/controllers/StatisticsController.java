package com.ru.rudov.coffeeMachine.controllers;

import com.ru.rudov.coffeeMachine.dtos.LastOrderedReceiptDTO;
import com.ru.rudov.coffeeMachine.dtos.PopularRecipeDTO;
import com.ru.rudov.coffeeMachine.models.Order;
import com.ru.rudov.coffeeMachine.models.Recipe;
import com.ru.rudov.coffeeMachine.services.OrderService;
import com.ru.rudov.coffeeMachine.services.RecipeService;

import io.swagger.v3.oas.annotations.Operation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/statistics")

public class StatisticsController {

    private final OrderService orderService;
    private final RecipeService recipeService;

    @Autowired
    public StatisticsController(OrderService orderService, RecipeService recipeService) {
        this.orderService = orderService;
        this.recipeService = recipeService;
    }

    @Operation(summary = "Get the most popular recipe")
    @GetMapping("/most_popular")
    public PopularRecipeDTO getMostPopularReceipt() {
        Recipe mostPopular = recipeService.getMostPopular();
        return new PopularRecipeDTO(mostPopular.getName(),
                orderService.countOrdersByRecipeId(mostPopular.getId()));
    }

    @Operation(summary = "Get the last ordered recipe")
    @GetMapping("/last_ordered")
    public LastOrderedReceiptDTO getLastOrderedReceipt() {
        Order last = orderService.getLastOrder();
        return new LastOrderedReceiptDTO(last.getRecipe().getName(),
                last.getDatetimeOrdered());
    }
}
