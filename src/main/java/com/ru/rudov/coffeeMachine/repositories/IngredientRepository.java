package com.ru.rudov.coffeeMachine.repositories;

import com.ru.rudov.coffeeMachine.models.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

}
