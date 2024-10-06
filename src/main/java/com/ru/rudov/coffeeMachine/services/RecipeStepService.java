package com.ru.rudov.coffeeMachine.services;

import com.ru.rudov.coffeeMachine.models.Recipe;
import com.ru.rudov.coffeeMachine.models.RecipeStep;
import com.ru.rudov.coffeeMachine.repositories.RecipeStepRepository;
import com.ru.rudov.coffeeMachine.utils.enums.ActionType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@Slf4j
public class RecipeStepService {
    private final RecipeStepRepository recipeStepRepository;

    @Autowired
    public RecipeStepService(RecipeStepRepository recipeStepRepository) {
        this.recipeStepRepository = recipeStepRepository;
    }


    public List<String> getActionTypeDescriptions() {
        return Arrays.stream(ActionType.values())
                .map(ActionType::getDescription)
                .collect(Collectors.toList());
    }





}
