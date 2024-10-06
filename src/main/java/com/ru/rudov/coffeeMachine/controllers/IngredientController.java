package com.ru.rudov.coffeeMachine.controllers;

import com.ru.rudov.coffeeMachine.models.Ingredient;
import com.ru.rudov.coffeeMachine.services.IngredientService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequestMapping("/api/ingredient")
public class IngredientController {
    private final IngredientService ingredientService;

    @Autowired
    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @Operation(summary = "Add a new ingredient")
    @PostMapping("/add")
    public void addNewIngredient(@Valid @RequestBody Ingredient ingredient) {
        ingredientService.createIngredient(ingredient);
    }

    @Operation(summary = "Get all ingredients")
    @GetMapping("/all")
    public List<Ingredient> getAllIngredients(){
        return ingredientService.getAllIngredients();
    }

    @Operation(summary = "Get an ingredient by ID")
    @GetMapping("/{id}")
    public Ingredient getIngredientById(@PathVariable Long id){
        return ingredientService.getIngredientById(id);
    }

    @Operation(summary = "Increase ingredient stock by ID")
    @PatchMapping("/increase/{id}")
    public void increaseIngredientStock(@PathVariable Long id, Long quantity){
        ingredientService.increaseIngredientStockById(id, quantity);
    }

    @Operation(summary = "Decrease ingredient stock by ID")
    @PatchMapping("/decrease/{id}")
    public void decreaseIngredientStock(@PathVariable Long id, Long quantity){
        ingredientService.decreaseIngredientStockById(id, quantity);
    }

    @Operation(summary = "Delete an ingredient by ID")
    @DeleteMapping("/{id}")
    public void deleteIngredientById(@PathVariable Long id){
        ingredientService.deleteIngredientById(id);
    }

    @Operation(summary = "Update an ingredient by ID")
    @PostMapping("/{id}")
    public void updateIngredientById(@PathVariable Long id, @RequestBody Ingredient ingredient){
        ingredientService.updateIngredientById(id, ingredient);
    }
}
