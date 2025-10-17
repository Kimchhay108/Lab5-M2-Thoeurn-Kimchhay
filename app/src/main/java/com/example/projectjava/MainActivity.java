package com.example.projectjava;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView lastExpenseText;
    private Button viewDetailBtn;

    // Store last expense details
    private String amount = "";
    private String currency = "";
    private String category = "";
    private String remark = "";
    private String date = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lastExpenseText = findViewById(R.id.textView4);
        Button addExpenseBtn = findViewById(R.id.button2);
        viewDetailBtn = findViewById(R.id.button3);


        viewDetailBtn.setEnabled(false);
        viewDetailBtn.setAlpha(0.5f);


        ActivityResultLauncher<Intent> addExpenseLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Intent data = result.getData();
                        amount = data.getStringExtra("amount");
                        currency = data.getStringExtra("currency");
                        category = data.getStringExtra("category");
                        remark = data.getStringExtra("remark");
                        date = data.getStringExtra("date");

                        lastExpenseText.setText(amount + " " + currency);

                        viewDetailBtn.setEnabled(true);
                        viewDetailBtn.setAlpha(1.0f);
                    }
                }
        );


        addExpenseBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Add_Expesnse_activity.class);
            addExpenseLauncher.launch(intent);
        });


        viewDetailBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, activity_expense_detail.class);
            intent.putExtra("amount", amount);
            intent.putExtra("currency", currency);
            intent.putExtra("category", category);
            intent.putExtra("remark", remark);
            intent.putExtra("date", date);
            startActivity(intent);
        });
    }
}
