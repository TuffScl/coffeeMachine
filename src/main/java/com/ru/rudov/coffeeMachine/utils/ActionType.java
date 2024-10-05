package com.ru.rudov.coffeeMachine.utils;

import lombok.Getter;

@Getter
public enum ActionType {
    ADD_INGREDIENT("Добавить ингредиент"),
    HEAT_WATER("Нагреть воду"),
    FROTH_MILK("Взбить молоко"),
    MIX("Перемешать"),
    WAIT("Ожидание"),
    POUR_INTO_CUP("Налить в чашку");

    private final String description;

    ActionType(String description){
        this.description = description;
    }

}
