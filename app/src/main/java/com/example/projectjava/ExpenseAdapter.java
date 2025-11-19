package com.example.projectjava;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.List;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder> {

    private final List<Expense> expenseList;
    private final Context context;

    // Constructor accepts context
    public ExpenseAdapter(Context context, List<Expense> expenseList) {
        this.context = context;
        this.expenseList = expenseList;
    }

    @NonNull
    @Override
    public ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_expense, parent, false);
        return new ExpenseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseViewHolder holder, int position) {
        Expense expense = expenseList.get(position);

        // Format amount without scientific notation
        DecimalFormat df = new DecimalFormat("#,###.##");
        String formattedAmount = df.format(expense.getAmount()) + " " + expense.getCurrency();

        holder.tvAmountCurrency.setText(formattedAmount);
        holder.tvDate.setText(expense.getFormattedDate());
        holder.tvCategory.setText(expense.getCategory());
        holder.tvRemark.setText(expense.getRemark());

        // Open detail activity on item click
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailExpenseActivity.class);
            intent.putExtra("expenseId", expense.getId());
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
