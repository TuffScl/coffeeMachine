package com.ru.rudov.coffeeMachine.services;

import com.ru.rudov.coffeeMachine.repositories.RecipeStepRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@Slf4j
public class RecipeStepService {
    private final RecipeStepRepository recipeStepRepository;

    @Autowired
    public RecipeStepService(RecipeStepRepository recipeStepRepository) {
        this.recipeStepRepository = recipeStepRepository;
    }



}
