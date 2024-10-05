package com.ru.rudov.coffeeMachine.models;

import com.ru.rudov.coffeeMachine.utils.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="\"order\"")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="recipe_id")
    private Recipe recipe;

    @Column(name="datetime_ordered")
    private Timestamp datetimeOrdered;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
}
