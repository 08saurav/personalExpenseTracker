package org.lld.personalexpensetracker;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.lld.personalexpensetracker.model.ExpenseCategory;
import org.lld.personalexpensetracker.model.User;
import org.lld.personalexpensetracker.service.UserService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.lld.personalexpensetracker.dto.CreateExpenseDTO;
import org.lld.personalexpensetracker.dto.ExpenseResponseDTO;
import org.lld.personalexpensetracker.model.Expense;
import org.lld.personalexpensetracker.service.ExpenseService;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExpenseControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private ExpenseService expenseService;

    @InjectMocks
    private org.lld.personalexpensetracker.controller.ExpenseController expenseController;

    @Test
    void testCreateExpense() {
        // Arrange
        CreateExpenseDTO createExpenseDTO = new CreateExpenseDTO();
        createExpenseDTO.setCategory(ExpenseCategory.FOOD);
        createExpenseDTO.setAmount(100.0);
        createExpenseDTO.setDate(LocalDate.now());
        createExpenseDTO.setUserId(1L);

        User user = new User();
        user.setId(1L);

        Expense expense = new Expense();
        expense.setId(1L);
        expense.setCategory(ExpenseCategory.FOOD);
        expense.setAmount(100.0);
        expense.setDate(LocalDate.now());
        expense.setUser(user);

        // Correct way to mock the service method that returns Optional<User>
        when(userService.findById(1L)).thenReturn(user);  // Mocking correctly

        // Mocking the creation of an expense
        when(expenseService.createExpense(any(Expense.class))).thenReturn(expense);

        // Act
        ResponseEntity<ExpenseResponseDTO> response = expenseController.createExpense(createExpenseDTO);

        // Assert
        assertEquals(1L, response.getBody().getId());
        verify(userService, times(1)).findById(1L);  // Verify interaction with userService
        verify(expenseService, times(1)).createExpense(any(Expense.class)); // Verify interaction with expenseService
    }


    @Test
    void testGetTop3SpendingCategories() {
        // Arrange
        List<Map.Entry<String, Double>> mockCategories = List.of(
                Map.entry("Food", 200.0),
                Map.entry("Travel", 150.0),
                Map.entry("Shopping", 100.0)
        );

        // Mock the service call
        when(expenseService.getTop3SpendingCategories(1L)).thenReturn(mockCategories);

        // Act
        ResponseEntity<List<Map.Entry<String, Double>>> response = expenseController.getTop3SpendingCategories(1L);

        // Assert
        assertEquals(3, response.getBody().size());
        assertEquals("Food", response.getBody().get(0).getKey());
        verify(expenseService, times(1)).getTop3SpendingCategories(1L);  // Verifying that the service method is called once
    }
}
