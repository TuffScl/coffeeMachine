package com.ru.rudov.coffeeMachine.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PopularRecipeDTO {
    private String name;

    private Long orderCount;
}
