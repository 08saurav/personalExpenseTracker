package org.lld.personalexpensetracker.service.filter;

import org.lld.personalexpensetracker.model.Expense;

import java.util.List;
import java.util.stream.Collectors;

public class CategoryFilterStrategy implements ExpenseFilterStrategy {
    private final String category;

    public CategoryFilterStrategy(String category) {
        this.category = category;
    }

    @Override
    public List<Expense> filter(List<Expense> expenses) {
        return expenses.stream()
                .filter(expense -> expense.getCategory().name().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }
}