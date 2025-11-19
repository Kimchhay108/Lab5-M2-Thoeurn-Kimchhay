package com.example.projectjava;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Expenselist extends Fragment {

    private RecyclerView recyclerView;
    private ExpenseAdapter adapter;
    private List<Expense> expenseList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_expense_list, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewExpenses);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Load expenses from ExpenseData
        expenseList = ExpenseData.getDummyExpenses();
        adapter = new ExpenseAdapter(getContext(), expenseList); // pass context
        recyclerView.setAdapter(adapter);

        // Listen for new expenses added from AddExpenseFragment
        getParentFragmentManager().setFragmentResultListener("addExpense", this, (requestKey, bundle) -> {
            String expenseId = bundle.getString("expenseId");
            Expense newExpense = ExpenseData.getExpenseById(expenseId);
            if (newExpense != null) {
                adapter.notifyItemInserted(expenseList.size() - 1);
                recyclerView.scrollToPosition(expenseList.size() - 1);
            }
        });

        return view;
    }
}
