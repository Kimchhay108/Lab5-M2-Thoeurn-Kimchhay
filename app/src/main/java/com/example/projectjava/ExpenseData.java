package com.example.projectjava;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class ExpenseData {

    private static final List<Expense> expenses = new ArrayList<>();

    static {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

            // Add dummy expenses with proper UUID and remark
            expenses.add(new Expense(UUID.randomUUID().toString(), 20000.0, "KHR", "Food", "Lunch", "dummyUserId", dateFormat.parse("2025-03-01")));
            expenses.add(new Expense(UUID.randomUUID().toString(), 1500.0, "KHR", "Transport", "Bus ticket", "dummyUserId", dateFormat.parse("2025-03-02")));
            expenses.add(new Expense(UUID.randomUUID().toString(), 8000.0, "KHR", "Entertainment", "Movie night", "dummyUserId", dateFormat.parse("2025-03-03")));
            expenses.add(new Expense(UUID.randomUUID().toString(), 50000.0, "KHR", "Shopping", "Shopping", "dummyUserId", dateFormat.parse("2025-03-04")));
            expenses.add(new Expense(UUID.randomUUID().toString(), 4000.0, "KHR", "Food", "Coffee", "dummyUserId", dateFormat.parse("2025-03-05")));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static List<Expense> getDummyExpenses() {
        return expenses;
    }

    public static void addExpense(Expense expense) {
        expenses.add(expense);
    }

    public static Expense getExpenseById(String id) {
        for (Expense e : expenses) {
            if (e.getId().equals(id)) return e;
        }
        return null;
    }

    public static Expense getLastExpense() {
        if (expenses.isEmpty()) return null;
        return expenses.get(expenses.size() - 1);
    }
}
