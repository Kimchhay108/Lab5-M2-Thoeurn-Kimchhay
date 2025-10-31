package com.example.projectjava;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddExpenseFragment extends Fragment {

    private Spinner spinner;
    private EditText amountEdit, remarkEdit;
    private RadioGroup currencyGroup;
    private Button addBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate fragment layout
        View view = inflater.inflate(R.layout.activity_add_expesnse, container, false);

        // Edge-to-edge padding
        ViewCompat.setOnApplyWindowInsetsListener(view.findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize views
        spinner = view.findViewById(R.id.spinner);
        amountEdit = view.findViewById(R.id.editTextText);
        currencyGroup = view.findViewById(R.id.radioGroup1);
        remarkEdit = view.findViewById(R.id.remark2);
        addBtn = view.findViewById(R.id.button);

        // Spinner setup
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                new String[]{"Food", "Transport", "Shopping", "Entertainment", "Other"}
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // Add button click
        addBtn.setOnClickListener(v -> {
            String amount = amountEdit.getText().toString();
            String currency = ((RadioButton) view.findViewById(currencyGroup.getCheckedRadioButtonId())).getText().toString();
            String category = spinner.getSelectedItem().toString();
            String remark = remarkEdit.getText().toString();
            String date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());

            // Return data to parent fragment/activity
            Intent result = new Intent();
            result.putExtra("amount", amount);
            result.putExtra("currency", currency);
            result.putExtra("category", category);
            result.putExtra("remark", remark);
            result.putExtra("date", date);

            // Send result back
            if (getActivity() != null) {
                getActivity().setResult(Activity.RESULT_OK, result);
                getActivity().finish();
            }
        });

        return view;
    }
}
