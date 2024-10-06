package com.ru.rudov.coffeeMachine.utils.enums;

import lombok.Getter;

import java.util.List;

@Getter
public enum ActionType {
    ADD_INGREDIENT("ADD_INGREDIENT"),
    HEAT_WATER("HEAT_WATER"),
    FROTH_MILK("FROTH_MILK"),
    MIX("MIX"),
    WAIT("WAIT"),
    POUR_INTO_CUP("POUR_INTO_CUP");

    private final String description;

    ActionType(String description){
        this.description = description;
    }

}
