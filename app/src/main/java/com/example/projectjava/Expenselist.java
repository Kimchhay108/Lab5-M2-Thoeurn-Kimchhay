package com.example.projectjava;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Expenselist extends Fragment {

    private RecyclerView recyclerView;
    private ExpenseAdapter adapter;
    private List<Expense> expenseList = new ArrayList<>();

    // Pagination
    private boolean isLoading = false;
    private int currentPage = 1;
    private int pageSize = 20;

    private final String DB_NAME = "ff4ad50d-834a-40a6-bcd3-7bbe2d6b8788";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_expense_list, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewExpenses);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        adapter = new ExpenseAdapter(requireContext(), expenseList);
        recyclerView.setAdapter(adapter);

        // Load first page
        loadExpenses(currentPage);

        // Infinite scroll
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView rv, int dx, int dy) {
                if (!rv.canScrollVertically(1) && !isLoading) {
                    currentPage++;
                    loadExpenses(currentPage);
                }
            }
        });

        // Swipe-to-delete
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                return false; // not supporting move
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Expense toDelete = expenseList.get(position);

                // Optional: Call backend API to delete
                ApiClient.getApi().deleteExpense(DB_NAME, toDelete.getId())
                        .enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if (response.isSuccessful()) {
                                    expenseList.remove(position);
                                    adapter.notifyItemRemoved(position);
                                    Toast.makeText(requireContext(), "Expense deleted", Toast.LENGTH_SHORT).show();
                                } else {
                                    adapter.notifyItemChanged(position);
                                    Toast.makeText(requireContext(), "Failed to delete expense", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                adapter.notifyItemChanged(position);
                                Toast.makeText(requireContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        };

        new ItemTouchHelper(simpleCallback).attachToRecyclerView(recyclerView);

        return view;
    }

    private void loadExpenses(int page) {
        isLoading = true;

        ApiClient.getApi().getExpenses(DB_NAME).enqueue(new Callback<List<Expense>>() {
            @Override
            public void onResponse(Call<List<Expense>> call, Response<List<Expense>> response) {
                if (!isAdded()) return;
                if (response.isSuccessful() && response.body() != null) {
                    List<Expense> allExpenses = response.body();
                    int start = (page - 1) * pageSize;
                    int end = Math.min(start + pageSize, allExpenses.size());
                    if (start >= end) {
                        isLoading = false;
                        return;
                    }
                    List<Expense> pageItems = allExpenses.subList(start, end);
                    expenseList.addAll(pageItems);
                    adapter.notifyDataSetChanged();
                }
                isLoading = false;
            }

            @Override
            public void onFailure(Call<List<Expense>> call, Throwable t) {
                isLoading = false;
                Toast.makeText(requireContext(), "Failed to load expenses", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
