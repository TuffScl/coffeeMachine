package com.ru.rudov.coffeeMachine.controllers;

import com.ru.rudov.coffeeMachine.dtos.RecipeDTO;
import com.ru.rudov.coffeeMachine.models.Recipe;
import com.ru.rudov.coffeeMachine.services.RecipeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/recipe")
public class RecipeController {

    private final RecipeService recipeService;
    private final ModelMapper modelMapper;

    @Autowired
    public RecipeController(RecipeService recipeService, ModelMapper modelMapper) {
        this.recipeService = recipeService;
        this.modelMapper = modelMapper;
    }

    @Operation(summary = "Add a new recipe")
    @PostMapping("/add")
    public void addNewRecipe(@Valid @RequestBody Recipe recipe) {
        recipeService.createRecipe(recipe);
    }

    @Operation(summary = "Get all recipes")
    @GetMapping("/all")
    public List<RecipeDTO> getAllRecipes() {
        List<Recipe> recipes = recipeService.getAllRecipes();
        return recipes.stream()
                .map(recipe -> modelMapper.map(recipe, RecipeDTO.class)) // Используйте правильный маппер
                .collect(Collectors.toList());
    }

    @Operation(summary = "Update a recipe by ID")
    @PutMapping("/{id}")
    public void changeRecipeById(@Parameter(description = "ID of the recipe to update", required = true) @PathVariable Long id,
                                 @Valid @RequestBody Recipe recipe) {
        recipeService.updateRecipeById(id, recipe);
    }

    @Operation(summary = "Delete a recipe by ID")
    @DeleteMapping("/{id}")
    public void deleteRecipeById(@Parameter(description = "ID of the recipe to delete", required = true) @PathVariable Long id) {
        recipeService.deleteRecipeById(id);
    }
}
