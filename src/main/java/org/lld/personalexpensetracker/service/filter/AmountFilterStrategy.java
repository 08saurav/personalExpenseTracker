package org.lld.personalexpensetracker.service.filter;

import org.lld.personalexpensetracker.model.Expense;

import java.util.List;
import java.util.stream.Collectors;

public class AmountFilterStrategy implements ExpenseFilterStrategy {
    private final double minAmount;
    private final double maxAmount;

    public AmountFilterStrategy(double minAmount, double maxAmount) {
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;
    }

    @Override
    public List<Expense> filter(List<Expense> expenses) {
        return expenses.stream()
                .filter(expense -> expense.getAmount() >= minAmount && expense.getAmount() <= maxAmount)
                .collect(Collectors.toList());
    }
}