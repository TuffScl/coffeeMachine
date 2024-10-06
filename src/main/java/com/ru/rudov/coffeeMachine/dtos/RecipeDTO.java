package com.ru.rudov.coffeeMachine.dtos;

import com.ru.rudov.coffeeMachine.models.RecipeStep;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RecipeDTO {
    private Long id;
    private String name;
    private List<RecipeStepDTO> steps;
}
