package com.ru.rudov.coffeeMachine.repositories;

import com.ru.rudov.coffeeMachine.models.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    Optional<Recipe> findByName(String name);

    @Query("SELECT r FROM Order o JOIN o.recipe r " +
            "GROUP BY r " +
            "ORDER BY COUNT(o) DESC")
    List<Recipe> findMostPopularRecipe();


}
