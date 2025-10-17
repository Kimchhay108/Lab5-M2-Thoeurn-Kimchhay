package com.example.projectjava;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Add_Expesnse_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_expesnse);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                new String[]{"Food", "Transport", "Shopping", "Entertainment", "Other"}
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        EditText amountEdit = findViewById(R.id.editTextText);
        RadioGroup currencyGroup = findViewById(R.id.radioGroup1);
        EditText remarkEdit = findViewById(R.id.remark2);

        Button addBtn = findViewById(R.id.button);
        addBtn.setOnClickListener(v -> {
            String amount = amountEdit.getText().toString();
            String currency = ((RadioButton) findViewById(currencyGroup.getCheckedRadioButtonId())).getText().toString();
            String category = spinner.getSelectedItem().toString();
            String remark = remarkEdit.getText().toString();
            String date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());

            Intent result = new Intent();
            result.putExtra("amount", amount);
            result.putExtra("currency", currency);
            result.putExtra("category", category);
            result.putExtra("remark", remark);
            result.putExtra("date", date);
            setResult(RESULT_OK, result);
            finish();
        });
    }
}
