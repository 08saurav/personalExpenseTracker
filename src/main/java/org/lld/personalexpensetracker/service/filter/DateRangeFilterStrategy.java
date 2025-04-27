package org.lld.personalexpensetracker.service.filter;

import org.lld.personalexpensetracker.model.Expense;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class DateRangeFilterStrategy implements ExpenseFilterStrategy {
    private final LocalDate startDate;
    private final LocalDate endDate;

    public DateRangeFilterStrategy(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public List<Expense> filter(List<Expense> expenses) {
        return expenses.stream()
                .filter(expense -> !expense.getDate().isBefore(startDate) && !expense.getDate().isAfter(endDate))
                .collect(Collectors.toList());
    }
}