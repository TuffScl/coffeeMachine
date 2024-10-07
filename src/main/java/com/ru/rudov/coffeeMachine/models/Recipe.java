package com.ru.rudov.coffeeMachine.models;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="recipe")
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @NotBlank(message = "Название рецепта не должно быть пустым")
    @Column(name="name")
    private String name;

    @Valid
    @NotEmpty(message = "Список шагов не может быть пустым")
    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(FetchMode.JOIN) // Инициализация при загрузке рецепта
    private List<RecipeStep> steps;

    @OneToMany(mappedBy = "recipe")
    private List<Order> orders;

}
