package com.ru.rudov.coffeeMachine.dtos;

import com.ru.rudov.coffeeMachine.utils.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private Long id;
    private Long recipeId;
    private Timestamp datetimeOrdered;
    private OrderStatus status;
}
