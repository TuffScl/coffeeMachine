package com.ru.rudov.coffeeMachine.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LastOrderedReceiptDTO {
    private String name;
    private Timestamp lastOrdered;
}
