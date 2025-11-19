package com.example.projectjava;

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
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.Date;

public class AddExpenseFragment extends Fragment {

    private Spinner spinnerCategory;
    private EditText editAmount, editRemark;
    private RadioGroup radioGroupCurrency;
    private Button btnAddExpense;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_add_expesnse, container, false);

        // Initialize views
        spinnerCategory = view.findViewById(R.id.spinner);
        editAmount = view.findViewById(R.id.editTextText);
        radioGroupCurrency = view.findViewById(R.id.radioGroup1);
        editRemark = view.findViewById(R.id.remark2);
        btnAddExpense = view.findViewById(R.id.button);

        // Spinner setup
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                new String[]{"Food", "Transport", "Shopping", "Entertainment", "Other"}
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapter);

        // Add Expense Button click
        btnAddExpense.setOnClickListener(v -> {

            // 1️⃣ Check currency selection
            int selectedId = radioGroupCurrency.getCheckedRadioButtonId();
            if (selectedId == -1) {
                Toast.makeText(getContext(), "Please select a currency", Toast.LENGTH_SHORT).show();
                return;
            }
            RadioButton selectedButton = view.findViewById(selectedId);

            // 2️⃣ Validate amount
            String amountText = editAmount.getText().toString().trim();
            if (amountText.isEmpty()) {
                Toast.makeText(getContext(), "Please enter an amount", Toast.LENGTH_SHORT).show();
                return;
            }

            double amount;
            try {
                amount = Double.parseDouble(amountText);
            } catch (NumberFormatException e) {
                Toast.makeText(getContext(), "Invalid amount", Toast.LENGTH_SHORT).show();
                return;
            }

            // 3️⃣ Collect other inputs
            String currency = selectedButton.getText().toString();
            String category = spinnerCategory.getSelectedItem().toString();
            String remark = editRemark.getText().toString().trim();
            Date date = new Date(); // current date

            // 4️⃣ Create Expense object and add to ExpenseData
            Expense newExpense = new Expense(remark, amount, currency, category, date);
            ExpenseData.addExpense(newExpense);

            // 5️⃣ Send result to parent fragment (Expenselist)
            Bundle result = new Bundle();
            result.putString("expenseId", newExpense.getId());
            getParentFragmentManager().setFragmentResult("addExpense", result);

            // 6️⃣ Close AddExpenseFragment properly
            requireActivity().getSupportFragmentManager().popBackStack();

            Toast.makeText(getContext(), "Expense added!", Toast.LENGTH_SHORT).show();
        });

        return view;
    }
}
