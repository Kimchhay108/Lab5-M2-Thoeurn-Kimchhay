package com.example.projectjava;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Expenselist extends Fragment {
    private RecyclerView recyclerView;
    private ExpenseAdapter adapter;
    private List<Expense> expenseList;
    private final String DB_NAME = "ff4ad50d-834a-40a6-bcd3-7bbe2d6b8788"; // same new GUID

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expense_list, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewExpenses);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        fetchExpenses();

        // Listen for new expense added
        getParentFragmentManager().setFragmentResultListener("refreshExpenses", this, (requestKey, bundle) -> {
            fetchExpenses(); // reload list from API
        });

        return view;
    }

    private void fetchExpenses() {
        ApiClient.getApi().getExpenses(DB_NAME).enqueue(new Callback<List<Expense>>() {
            @Override
            public void onResponse(Call<List<Expense>> call, Response<List<Expense>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    expenseList = response.body();
                    adapter = new ExpenseAdapter(getContext(), expenseList);
                    recyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(getContext(), "Failed to fetch expenses", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Expense>> call, Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
