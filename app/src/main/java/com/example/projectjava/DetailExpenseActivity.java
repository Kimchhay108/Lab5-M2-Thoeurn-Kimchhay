package com.example.projectjava;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetailExpenseActivity extends AppCompatActivity {

    private TextView tvRemark, tvAmount, tvCurrency, tvCategory, tvDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_detail); // Your layout

        tvRemark = findViewById(R.id.tvRemark);
        tvAmount = findViewById(R.id.tvAmount);
        tvCurrency = findViewById(R.id.tvCurrency);
        tvCategory = findViewById(R.id.tvCategory);
        tvDate = findViewById(R.id.tvDate);

        // Get expenseId from Intent
        String expenseId = getIntent().getStringExtra("expenseId");
        if (expenseId != null) {
            Expense expense = ExpenseData.getExpenseById(expenseId);
            if (expense != null) {
                tvRemark.setText(expense.getRemark());
                tvAmount.setText(String.valueOf(expense.getAmount()));
                tvCurrency.setText(expense.getCurrency());
                tvCategory.setText(expense.getCategory());
                tvDate.setText(expense.getFormattedDate());
            }
        }
    }
}
