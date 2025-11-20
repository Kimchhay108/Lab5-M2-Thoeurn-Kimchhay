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

import com.google.firebase.auth.FirebaseAuth;

import java.util.Date;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddExpenseFragment extends Fragment {

    private Spinner spinnerCategory;
    private EditText editAmount, editRemark;
    private RadioGroup radioGroupCurrency;
    private Button btnAddExpense;
    private final String DB_NAME = "ff4ad50d-834a-40a6-bcd3-7bbe2d6b8788"; // your new GUID

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_add_expesnse, container, false);

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

        btnAddExpense.setOnClickListener(v -> {
            // 1️⃣ Validate currency
            int selectedId = radioGroupCurrency.getCheckedRadioButtonId();
            if (selectedId == -1) {
                Toast.makeText(getContext(), "Please select a currency", Toast.LENGTH_SHORT).show();
                return;
            }
            RadioButton selectedButton = view.findViewById(selectedId);
            String currency = selectedButton.getText().toString();

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

            // 3️⃣ Collect other fields
            Object selected = spinnerCategory.getSelectedItem();
            if (selected == null) {
                Toast.makeText(getContext(), "Please select a category", Toast.LENGTH_SHORT).show();
                return;
            }
            String category = selected.toString();
            String remark = editRemark.getText().toString().trim();
            String createdBy = FirebaseAuth.getInstance().getCurrentUser() != null ?
                    FirebaseAuth.getInstance().getCurrentUser().getUid() : "dummyUserId";
            Date createdDate = new Date();

            // 4️⃣ Create Expense object with correct constructor
            Expense newExpense = new Expense(
                    UUID.randomUUID().toString(), // id
                    amount,
                    currency,
                    category,
                    remark,
                    createdBy,
                    createdDate
            );

            // 5️⃣ Send to backend API
            ApiClient.getApi().addExpense(DB_NAME, newExpense).enqueue(new Callback<Expense>() {
                @Override
                public void onResponse(Call<Expense> call, Response<Expense> response) {
                    if (!isAdded()) return; // safe check
                    if (response.isSuccessful() && response.body() != null) {
                        // Add to local list
                        ExpenseData.addExpense(response.body());

                        // Notify parent fragments
                        Bundle result = new Bundle();
                        result.putString("expenseId", response.body().getId());
                        getParentFragmentManager().setFragmentResult("addExpense", result);

                        // Close fragment
                        requireActivity().getSupportFragmentManager().popBackStack();

                        Toast.makeText(getContext(), "Expense added!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Failed to add expense", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Expense> call, Throwable t) {
                    if (!isAdded()) return;
                    Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        });

        return view;
    }
}
