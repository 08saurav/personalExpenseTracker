package org.lld.personalexpensetracker.service.filter;

import org.lld.personalexpensetracker.model.Expense;

import java.util.List;

public class ExpenseFilterContext {
    private ExpenseFilterStrategy strategy;

    public void setStrategy(ExpenseFilterStrategy strategy) {
        this.strategy = strategy;
    }

    public List<Expense> filterExpenses(List<Expense> expenses) {
        return strategy.filter(expenses);
    }
}