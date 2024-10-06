package com.ru.rudov.coffeeMachine.controllers;

import com.ru.rudov.coffeeMachine.models.Recipe;
import com.ru.rudov.coffeeMachine.models.RecipeStep;
import com.ru.rudov.coffeeMachine.services.RecipeStepService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/recipe_step")
public class RecipeStepController {
    private final RecipeStepService recipeStepService;

    @Autowired
    public RecipeStepController(RecipeStepService recipeStepService) {
        this.recipeStepService = recipeStepService;
    }

    @Operation(summary = "Get action type descriptions for recipe steps")
    @GetMapping("/action_types")
    public List<String> getActionTypeDescriptions() {
        return recipeStepService.getActionTypeDescriptions();
    }
}
