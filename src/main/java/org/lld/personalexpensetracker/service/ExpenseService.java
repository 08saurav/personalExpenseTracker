package org.lld.personalexpensetracker.service;

import org.lld.personalexpensetracker.model.Expense;
import org.lld.personalexpensetracker.repository.ExpenseRepository;
import org.lld.personalexpensetracker.repository.UserRepository;
import org.lld.personalexpensetracker.service.filter.ExpenseFilterContext;
import org.lld.personalexpensetracker.service.filter.ExpenseFilterStrategy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;

    public ExpenseService(ExpenseRepository expenseRepository, UserRepository userRepository) {
        this.expenseRepository = expenseRepository;
        this.userRepository = userRepository;
    }
    public Expense createExpense(Expense expense) {
        if(expense.getAmount() <= 0) {
            throw new IllegalArgumentException("Expense amount must be greater than zero");
        }
        return expenseRepository.save(expense);
    }
    public List<Map.Entry<String, Double>> getTop3SpendingCategories(Long userId) {
        List<Expense> expenses = expenseRepository.findByUserId(userId);
        return expenses.stream()
                .collect(Collectors.groupingBy(expense -> expense.getCategory().name(), Collectors.summingDouble(Expense::getAmount)))
                .entrySet()
                .stream()
                .sorted((e1, e2) -> Double.compare(e2.getValue(), e1.getValue()))
                .limit(3)
                .collect(Collectors.toList());
    }

    public double getTotalExpensesByCategory(Long userId, String category) {
        return expenseRepository.findByUserId(userId).stream()
                .filter(expense -> expense.getCategory().name().equalsIgnoreCase(category))
                .mapToDouble(Expense::getAmount)
                .sum();
    }
    public List<Expense> getFilteredExpenses(Long userId, ExpenseFilterStrategy filterStrategy) {
        List<Expense> expenses = expenseRepository.findByUserId(userId);
        ExpenseFilterContext filterContext = new ExpenseFilterContext();
        filterContext.setStrategy(filterStrategy);
        return filterContext.filterExpenses(expenses);
    }
}