package com.example.projectjava;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class activity_expense_detail extends AppCompatActivity {

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

        TextView tvAmount = findViewById(R.id.tvAmount);
        TextView tvCurrency = findViewById(R.id.tvCurrency);
        TextView tvCategory = findViewById(R.id.tvCategory);
        TextView tvRemark = findViewById(R.id.tvRemark);
        TextView tvDate = findViewById(R.id.tvDate);


        Button btnBackHome = findViewById(R.id.btnBackHome);

        // Receive data
        Intent intent = getIntent();
        tvAmount.setText(intent.getStringExtra("amount"));
        tvCurrency.setText(intent.getStringExtra("currency"));
        tvCategory.setText(intent.getStringExtra("category"));
        tvRemark.setText(intent.getStringExtra("remark"));
        tvDate.setText(intent.getStringExtra("date"));



        // Navigate back to MainActivity
        btnBackHome.setOnClickListener(v -> finish());
    }
}
