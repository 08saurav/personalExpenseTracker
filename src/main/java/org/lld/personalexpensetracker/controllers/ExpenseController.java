package org.lld.personalexpensetracker.controller;

import jakarta.validation.Valid;
import org.lld.personalexpensetracker.dto.CreateExpenseDTO;
import org.lld.personalexpensetracker.dto.ExpenseResponseDTO;
import org.lld.personalexpensetracker.model.Expense;
import org.lld.personalexpensetracker.model.User;
import org.lld.personalexpensetracker.service.ExpenseService;
import org.lld.personalexpensetracker.service.UserService;
import org.lld.personalexpensetracker.service.filter.AmountFilterStrategy;
import org.lld.personalexpensetracker.service.filter.CategoryFilterStrategy;
import org.lld.personalexpensetracker.service.filter.DateRangeFilterStrategy;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {
    private final ExpenseService expenseService;
    private final UserService userService;

    public ExpenseController(ExpenseService expenseService, UserService userService) {
        this.expenseService = expenseService;
        this.userService = userService;
    }

    @GetMapping("/top-categories")
    public ResponseEntity<List<Map.Entry<String, Double>>> getTop3SpendingCategories(@RequestParam Long userId) {
        return ResponseEntity.ok(expenseService.getTop3SpendingCategories(userId));
    }

    @GetMapping("/total-by-category")
    public ResponseEntity<Double> getTotalExpensesByCategory(
            @RequestParam Long userId, @RequestParam String category) {
        return ResponseEntity.ok(expenseService.getTotalExpensesByCategory(userId, category));
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Expense>> getFilteredExpenses(
            @RequestParam Long userId,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Double minAmount,
            @RequestParam(required = false) Double maxAmount,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate) {

        if (category != null) {
            return ResponseEntity.ok(expenseService.getFilteredExpenses(userId, new CategoryFilterStrategy(category)));
        } else if (minAmount != null && maxAmount != null) {
            return ResponseEntity.ok(expenseService.getFilteredExpenses(userId, new AmountFilterStrategy(minAmount, maxAmount)));
        } else if (startDate != null && endDate != null) {
            return ResponseEntity.ok(expenseService.getFilteredExpenses(userId, new DateRangeFilterStrategy(startDate, endDate)));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
    @PostMapping
    public ResponseEntity<ExpenseResponseDTO> createExpense(@RequestBody @Valid CreateExpenseDTO createExpenseDTO) {
        // Ensure the user exists or throw an exception
        User user = userService.findById(createExpenseDTO.getUserId());

        // Create the expense object
        Expense expense = new Expense();
        expense.setCategory(createExpenseDTO.getCategory());
        expense.setAmount(createExpenseDTO.getAmount());
        expense.setDate(createExpenseDTO.getDate());
        expense.setUser(user);  // Set the User object directly

        // Call service to save the expense
        Expense savedExpense = expenseService.createExpense(expense);

        // Prepare the response DTO
        ExpenseResponseDTO responseDTO = new ExpenseResponseDTO();
        responseDTO.setId(savedExpense.getId());
        responseDTO.setCategory(savedExpense.getCategory());
        responseDTO.setAmount(savedExpense.getAmount());
        responseDTO.setDate(savedExpense.getDate());

        return ResponseEntity.ok(responseDTO);
    }
}