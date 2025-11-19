package com.example.projectjava;

import com.example.projectjava.Expense;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ExpenseData {
    private static final List<Expense> expenses = new ArrayList<>();

    static {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

            expenses.add(new Expense("Lunch", 20000, "KHR", "Food", dateFormat.parse("2025-03-01")));
            expenses.add(new Expense("Taxi", 2.5, "USD", "Transport", dateFormat.parse("2025-03-02")));
            expenses.add(new Expense("Groceries", 80000, "KHR", "Shopping", dateFormat.parse("2025-03-03")));
            expenses.add(new Expense("Coffee", 3.0, "USD", "Beverage", dateFormat.parse("2025-03-04")));
            expenses.add(new Expense("Movie Ticket", 32000, "KHR", "Entertainment", dateFormat.parse("2025-03-05")));
            expenses.add(new Expense("Dinner", 12.0, "USD", "Food", dateFormat.parse("2025-03-06")));
            expenses.add(new Expense("Bus Fare", 6000, "KHR", "Transport", dateFormat.parse("2025-03-07")));
            expenses.add(new Expense("Gym Membership", 30.0, "USD", "Fitness", dateFormat.parse("2025-03-08")));
            expenses.add(new Expense("Electricity Bill", 200000, "KHR", "Utilities", dateFormat.parse("2025-03-09")));
            expenses.add(new Expense("Internet Bill", 25.0, "USD", "Utilities", dateFormat.parse("2025-03-10")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Get all expenses
    public static List<Expense> getDummyExpenses() {
        return expenses;
    }

    // Find expense by ID
    public static Expense getExpenseById(String id) {
        for (Expense expense : expenses) {
            if (expense.getId().equals(id)) {
                return expense;
            }
        }
        return null;
    }

    // Get last added expense
    public static Expense getLastExpense() {
        if (!expenses.isEmpty()) {
            return expenses.get(expenses.size() - 1);
        }
        return null;
    }

    // Add a new expense dynamically
    public static void addExpense(Expense expense) {
        expenses.add(expense);
    }
}
