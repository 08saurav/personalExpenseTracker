package org.lld.personalexpensetracker.service.filter;

import org.lld.personalexpensetracker.model.Expense;

import java.util.List;

public interface ExpenseFilterStrategy {
    List<Expense> filter(List<Expense> expenses);
}