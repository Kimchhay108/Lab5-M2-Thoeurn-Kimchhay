package com.example.projectjava;

import java.util.UUID;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class Expense {
    private String id;
    private String remark;
    private double amount;
    private String currency;
    private String category;
    private Date date;

    public Expense(String remark, double amount, String currency, String category, Date date) {
        this.id = UUID.randomUUID().toString();
        this.remark = remark;
        this.amount = amount;
        this.currency = currency;
        this.category = category;
        this.date = date;
    }

    public String getId() { return id; }
    public String getRemark() { return remark; }
    public double getAmount() { return amount; }
    public String getCurrency() { return currency; }
    public String getCategory() { return category; }
    public Date getDate() { return date; }

    public String getFormattedDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return dateFormat.format(date);
    }

    public String getFormattedAmount() {
        // Format with 2 decimal places
        return String.format(Locale.getDefault(), "%.2f %s", amount, currency);
    }
}
