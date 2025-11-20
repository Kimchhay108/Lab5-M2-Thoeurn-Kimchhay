package com.example.projectjava;

import com.google.gson.annotations.JsonAdapter;
import java.io.Serializable;
import java.util.Date;

public class Expense implements Serializable {
    private String id;
    private double amount;
    private String currency;
    private String category;
    private String remark;
    private String createdBy;
    @JsonAdapter(ISO8601DateAdapter.class)
    private Date createdDate;

    // Full constructor
    public Expense(String id, double amount, String currency, String category, String remark, String createdBy, Date createdDate) {
        this.id = id;
        this.amount = amount;
        this.currency = currency;
        this.category = category;
        this.remark = remark;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
    }

    // Getters
    public String getId() { return id; }
    public double getAmount() { return amount; }
    public String getCurrency() { return currency; }
    public String getCategory() { return category; }
    public String getRemark() { return remark; }
    public String getCreatedBy() { return createdBy; }
    public Date getCreatedDate() { return createdDate; }

    // Helper methods
    public String getFormattedAmount() {
        return String.format("%.2f %s", amount, currency);
    }

    public String getFormattedDate() {
        if (createdDate == null) return "";
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(createdDate);
    }
}
