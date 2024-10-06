package com.ru.rudov.coffeeMachine.models;

import com.ru.rudov.coffeeMachine.utils.enums.ActionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="recipe_step")
public class RecipeStep {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="quantity")
    private Long quantity;

    @ManyToOne
    @JoinColumn(name="recipe_id")
    private Recipe recipe;

    @ManyToOne
    @JoinColumn(name="ingredient_id", nullable = true)
    private Ingredient ingredient;

    @Enumerated(EnumType.STRING)
    @Column(name = "action_type")
    private ActionType actionType;
}
