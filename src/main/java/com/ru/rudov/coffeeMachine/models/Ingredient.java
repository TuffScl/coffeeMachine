package com.ru.rudov.coffeeMachine.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="ingredient")
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "Название ингредиента не должно быть пустым")
    @Column(name="name")
    private String name;

    @NotNull(message = "Количество не может быть null")
    @Min(value = 0, message = "Запас ингредиента должен быть не отрицательным")
    @Column(name="stock")
    private Long stock;


    @OneToMany(mappedBy = "ingredient")
    @JsonIgnore
    private List<RecipeStep> steps;

}
