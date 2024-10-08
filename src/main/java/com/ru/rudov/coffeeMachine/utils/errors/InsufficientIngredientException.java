package com.ru.rudov.coffeeMachine.utils.errors;

public class InsufficientIngredientException extends IllegalArgumentException{
    public InsufficientIngredientException(String message) {
        super(message);
    }
}
