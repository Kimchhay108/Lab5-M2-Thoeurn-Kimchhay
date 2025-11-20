package com.example.projectjava;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetailExpenseActivity extends AppCompatActivity {

    private TextView tvRemark, tvAmount, tvCategory, tvDate;
    private Button btnBackHome, btnAddDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_detail);

        // Initialize TextViews
        tvRemark = findViewById(R.id.tvRemark);
        tvAmount = findViewById(R.id.tvAmount);
        tvCategory = findViewById(R.id.tvCategory);
        tvDate = findViewById(R.id.tvDate);

        // Initialize Buttons
        btnBackHome = findViewById(R.id.btnBackHome);
        btnAddDetail = findViewById(R.id.btn_add_detail);

        // --- Display Expense Data ---
        Expense expense = (Expense) getIntent().getSerializableExtra("expense");
        if (expense != null) {
            tvRemark.setText("Remark: " + expense.getRemark());
            tvAmount.setText("Amount: " + expense.getFormattedAmount());
            tvCategory.setText("Category: " + expense.getCategory());
            tvDate.setText("Created Date: " + expense.getFormattedDate());
        }

        // --- Button: Back to Home ---
        btnBackHome.setOnClickListener(v -> {
            Intent intent = new Intent(DetailExpenseActivity.this, MainActivity.class);
            intent.putExtra("navigateTo", "home");
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });

        // --- Button: Add New Detail ---
        btnAddDetail.setOnClickListener(v -> {
            Intent intent = new Intent(DetailExpenseActivity.this, MainActivity.class);
            intent.putExtra("navigateTo", "addExpense");
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });
    }
}
