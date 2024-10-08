package com.ru.rudov.coffeeMachine.services;

import com.ru.rudov.coffeeMachine.models.Ingredient;
import com.ru.rudov.coffeeMachine.models.Recipe;
import com.ru.rudov.coffeeMachine.repositories.IngredientRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@Slf4j
public class IngredientService {
    private final IngredientRepository ingredientRepository;

    @Autowired
    public IngredientService(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @Transactional
    public void createIngredient(Ingredient ingredient){
        log.info("Creating new ingredient with id: {}", ingredient.getId());
        ingredientRepository.save(ingredient);
    }

    public Ingredient getIngredientById(Long id){
        log.info("Fetching ingredient by id: {}", id);
        return ingredientRepository.findById(id).orElseThrow(()->
                new EntityNotFoundException("Ingredient with id "+ id+ " does not exist!"));
    }

    public List<Ingredient> getAllIngredients(){
        log.info("Fetching all ingredients");
        return ingredientRepository.findAll();
    }

    public void updateIngredientById(Long id, Ingredient ingredient) {
        log.info("Updating ingredient with id: {}", id);
        ingredientRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Ingredient with id " + id + " does not exist!"));
        ingredient.setId(id);
        ingredientRepository.save(ingredient);
    }

    @Transactional
    public void deleteIngredientById(Long id){
        log.info("Deleting ingredient with id: {}", id);
        ingredientRepository.deleteById(id);
    }

    @Transactional
    public void increaseIngredientStockById(Long id, Long quantity){
        log.info("Increasing ingredient's stock with id: {}, by: {}", id, quantity);
        Ingredient ingredient = ingredientRepository.findById(id).orElseThrow(()->
                new EntityNotFoundException("Ingredient with id "+ id+ " does not exist!"));
        ingredient.setStock(ingredient.getStock()+quantity);
        ingredientRepository.save(ingredient);
    }

    @Transactional
    public void decreaseIngredientStockById(Long id, Long quantity){
        log.info("Decreasing ingredient's stock with id: {}, by: {}", id, quantity);
        Ingredient ingredient = ingredientRepository.findById(id).orElseThrow(()->
                new EntityNotFoundException("Ingredient with id "+ id+ " does not exist!"));
        if (ingredient.getStock()>=quantity)
            ingredient.setStock(ingredient.getStock()-quantity);
        else ingredient.setStock(0L);
        ingredientRepository.save(ingredient);
    }


}
