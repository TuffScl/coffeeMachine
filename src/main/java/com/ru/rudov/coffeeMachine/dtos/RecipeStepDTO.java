package com.ru.rudov.coffeeMachine.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RecipeStepDTO {
    private Long quantity;
    private Long ingredientId;
    private String actionType;
}