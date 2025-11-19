package com.example.projectjava;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetailExpenseActivity extends AppCompatActivity {

    private TextView tvRemark, tvAmount, tvCurrency, tvCategory, tvDate;
    private Button btnBackHome, btnAddDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_detail);

        // Initialize TextViews
        tvRemark = findViewById(R.id.tvRemark);
        tvAmount = findViewById(R.id.tvAmount);
        tvCurrency = findViewById(R.id.tvCurrency);
        tvCategory = findViewById(R.id.tvCategory);
        tvDate = findViewById(R.id.tvDate);

        // Initialize Buttons
        btnBackHome = findViewById(R.id.btnBackHome);
        btnAddDetail = findViewById(R.id.btn_add_detail);

        // --- Display Expense Data ---
        String expenseId = getIntent().getStringExtra("expenseId");
        if (expenseId != null) {
            Expense expense = ExpenseData.getExpenseById(expenseId);
            if (expense != null) {
                tvRemark.setText("Remark: " + expense.getRemark());
                // show formatted amount to avoid scientific notation
                tvAmount.setText("Amount: " + expense.getFormattedAmount() + " " + expense.getCurrency());
                tvCurrency.setText(""); // optional - already in amount
                tvCategory.setText("Category: " + expense.getCategory());
                tvDate.setText("Created Date: " + expense.getFormattedDate());
            }
        }

        // --- Button: Back to Home ---
        btnBackHome.setOnClickListener(v -> {
            // Bring existing MainActivity to front (or start it) and tell it to show HomeFragment
            Intent intent = new Intent(DetailExpenseActivity.this, MainActivity.class);
            intent.putExtra("navigateTo", "home");
            // If MainActivity already exists, deliver to it instead of creating another instance
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });

        // --- Button: Add New Detail ---
        btnAddDetail.setOnClickListener(v -> {
            // Tell MainActivity to show AddExpenseFragment
            Intent intent = new Intent(DetailExpenseActivity.this, MainActivity.class);
            intent.putExtra("navigateTo", "addExpense");
            // If MainActivity already exists, deliver to it
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });
    }
}
