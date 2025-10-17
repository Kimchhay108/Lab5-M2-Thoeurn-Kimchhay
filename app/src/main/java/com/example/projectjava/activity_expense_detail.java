package com.example.projectjava;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.graphics.Insets;
import androidx.activity.EdgeToEdge;

public class activity_expense_detail extends AppCompatActivity {

    private TextView tvAmount, tvCurrency, tvCategory, tvRemark, tvDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_expense_detail);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tvAmount = findViewById(R.id.tvAmount);
        tvCurrency = findViewById(R.id.tvCurrency);
        tvCategory = findViewById(R.id.tvCategory);
        tvRemark = findViewById(R.id.tvRemark);
        tvDate = findViewById(R.id.tvDate);

        Button btnBackHome = findViewById(R.id.btnBackHome);
        Button btnAddDetail = findViewById(R.id.btn_add_detail);

        // Receive existing expense
        Intent intent = getIntent();
        if (intent != null) {
            tvAmount.setText(intent.getStringExtra("amount"));
            tvCurrency.setText(intent.getStringExtra("currency"));
            tvCategory.setText(intent.getStringExtra("category"));
            tvRemark.setText(intent.getStringExtra("remark"));
            tvDate.setText(intent.getStringExtra("date"));
        }

        btnBackHome.setOnClickListener(v -> finish());

        // === Launcher for Add Expense from detail screen ===
        ActivityResultLauncher<Intent> addExpenseLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        Intent data = result.getData();

                        // Update detail screen
                        tvAmount.setText(data.getStringExtra("amount"));
                        tvCurrency.setText(data.getStringExtra("currency"));
                        tvCategory.setText(data.getStringExtra("category"));
                        tvRemark.setText(data.getStringExtra("remark"));
                        tvDate.setText(data.getStringExtra("date"));

                        // Return to MainActivity as well
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("amount", data.getStringExtra("amount"));
                        resultIntent.putExtra("currency", data.getStringExtra("currency"));
                        resultIntent.putExtra("category", data.getStringExtra("category"));
                        resultIntent.putExtra("remark", data.getStringExtra("remark"));
                        resultIntent.putExtra("date", data.getStringExtra("date"));
                        setResult(Activity.RESULT_OK, resultIntent);
                    }
                }
        );

        btnAddDetail.setOnClickListener(v -> {
            Intent addIntent = new Intent(activity_expense_detail.this, Add_Expesnse_activity.class);
            addExpenseLauncher.launch(addIntent);
        });
    }
}
