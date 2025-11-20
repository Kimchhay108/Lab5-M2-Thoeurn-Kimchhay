package com.example.projectjava;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.io.Serializable;
import java.util.List;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder> {

    private final Context context;
    private final List<Expense> expenseList;

    public ExpenseAdapter(Context context, List<Expense> expenseList) {
        this.context = context;
        this.expenseList = expenseList;
    }

    @NonNull
    @Override
    public ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_expense, parent, false);
        return new ExpenseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseViewHolder holder, int position) {
        Expense expense = expenseList.get(position);

        holder.tvAmountCurrency.setText(String.format("%.2f %s", expense.getAmount(), expense.getCurrency()));
        holder.tvDate.setText(expense.getFormattedDate());
        holder.tvCategory.setText(expense.getCategory());
        holder.tvRemark.setText(expense.getRemark());

        // --- Add click listener ---
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailExpenseActivity.class);
            intent.putExtra("expense", (Serializable) expense); // pass Expense object
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return expenseList.size();
    }

    static class ExpenseViewHolder extends RecyclerView.ViewHolder {
        TextView tvAmountCurrency, tvDate, tvCategory, tvRemark;

        public ExpenseViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAmountCurrency = itemView.findViewById(R.id.tvAmountCurrency);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            tvRemark = itemView.findViewById(R.id.tvRemark);
        }
    }
}
