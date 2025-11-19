package com.example.projectjava;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class HomeFragment extends Fragment {

    private TextView lastExpenseText;
    private Button viewDetailBtn;
    private Button addExpenseBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        lastExpenseText = view.findViewById(R.id.textView4);
        viewDetailBtn = view.findViewById(R.id.button3);
        addExpenseBtn = view.findViewById(R.id.button2);

        // Initially disable "View Detail" button if no expenses exist
        updateLastExpense();

        // Navigate to AddExpenseFragment when button clicked
        addExpenseBtn.setOnClickListener(v -> {
            getParentFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new AddExpenseFragment())
                    .addToBackStack(null)
                    .commit();
        });

        // Listen for newly added expenses
        getParentFragmentManager().setFragmentResultListener("addExpense", this, (requestKey, bundle) -> {
            updateLastExpense();
        });

        // Update last expense when coming back to this fragment
        getParentFragmentManager().registerFragmentLifecycleCallbacks(new FragmentManager.FragmentLifecycleCallbacks() {
            @Override
            public void onFragmentResumed(FragmentManager fm, Fragment f) {
                super.onFragmentResumed(fm, f);
                updateLastExpense();
            }
        }, false);

        return view;
    }

    // Update the last expense displayed
    private void updateLastExpense() {
        Expense lastExpense = ExpenseData.getLastExpense();

        if (lastExpense != null) {
            // Display formatted amount (already includes currency)
            lastExpenseText.setText(lastExpense.getFormattedAmount());
            setViewDetailEnabled(true);

            // Open DetailExpenseActivity when "View Detail" is clicked
            viewDetailBtn.setOnClickListener(v -> {
                Intent intent = new Intent(requireContext(), DetailExpenseActivity.class);
                intent.putExtra("expenseId", lastExpense.getId());
                startActivity(intent);
            });
        } else {
            lastExpenseText.setText("No expenses yet");
            setViewDetailEnabled(false);
        }
    }

    // Enable or disable the "View Detail" button
    private void setViewDetailEnabled(boolean enabled) {
        viewDetailBtn.setEnabled(enabled);
        viewDetailBtn.setAlpha(enabled ? 1.0f : 0.5f);
    }
}
