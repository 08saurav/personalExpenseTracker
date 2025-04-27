package org.lld.personalexpensetracker.dto;

import lombok.Getter;
import lombok.Setter;
import org.lld.personalexpensetracker.model.ExpenseCategory;

import java.time.LocalDate;

@Getter
@Setter
public class CreateExpenseDTO {
    private ExpenseCategory category;
    private double amount;
    private LocalDate date;
    private Long userId;
}