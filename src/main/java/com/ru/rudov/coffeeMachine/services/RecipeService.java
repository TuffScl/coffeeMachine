package com.ru.rudov.coffeeMachine.services;

import com.ru.rudov.coffeeMachine.models.Recipe;
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
    public Recipe updateRecipeId(Long id, Recipe recipe){
        log.info("Updating recipe with id: {}", id);
        return recipeRepository.findById(id).map(existingRecipe->{
            recipe.setId(id);
            return recipeRepository.save(recipe);
        }).orElseThrow(()->{
            log.error("Recipe with id {} does not exist!", id);
            return new EntityNotFoundException("Recipe with id "+ id+ " does not exist!");});
    }

    @Transactional
    public void deleteRecipeById(Long id){
        log.info("Deleting recipe with id: {}", id);
        recipeRepository.deleteById(id);
    }

    public Recipe getMostPopular(){
        log.info("Fetching the most popular recipe");
        return recipeRepository.findMostPopularRecipe().stream().findFirst().orElseThrow(() -> {
            log.error("No recipes found");
            return new EntityNotFoundException("There are no recipes!");
        });
    }




}
