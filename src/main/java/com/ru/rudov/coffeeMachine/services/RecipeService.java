package com.ru.rudov.coffeeMachine.services;

import com.ru.rudov.coffeeMachine.models.Recipe;
import com.ru.rudov.coffeeMachine.models.RecipeStep;
import com.ru.rudov.coffeeMachine.repositories.RecipeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@Slf4j
public class RecipeService {
    private final RecipeRepository recipeRepository;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Transactional
    public void createRecipe(Recipe recipe){
        log.info("Creating new recipe with id: {}", recipe.getId());
        recipe.getSteps().forEach(step -> step.setRecipe(recipe));
        recipeRepository.save(recipe);
    }

    public Recipe getRecipeById(Long id){
        log.info("Fetching recipe by id: {}", id);
        return recipeRepository.findById(id).orElse(null);
    }

    public List<Recipe> getAllRecipes(){
        log.info("Fetching all recipes");
        return recipeRepository.findAll();
    }

    @Transactional
    public void updateRecipeById(Long id, Recipe recipe) {
        log.info("Updating recipe with id: {}", id);
        recipeRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Recipe with id: " + id + " does not exist"));
        recipe.setId(id);
        for (RecipeStep step : recipe.getSteps()) {
            step.setRecipe(recipe);
        }
        recipeRepository.save(recipe);
    }

    @Transactional
    public void deleteRecipeById(Long id){
        log.info("Deleting recipe with id: {}", id);
        recipeRepository.deleteById(id);
    }

    public Recipe getMostPopular(){
        log.info("Fetching the most popular recipe");
        return recipeRepository.findMostPopularRecipe().stream().findFirst().orElseThrow(() ->
                new EntityNotFoundException("There are no recipes!"));
    }




}
