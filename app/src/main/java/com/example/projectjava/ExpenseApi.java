package com.example.projectjava;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ExpenseApi {

    @GET("expenses")
    Call<List<Expense>> getExpenses(@Header("X-DB-NAME") String dbName);

    @POST("expenses")
    Call<Expense> addExpense(@Header("X-DB-NAME") String dbName, @Body Expense expense);

    @DELETE("expenses/{id}")
    Call<Void> deleteExpense(@Header("X-DB-NAME") String dbName, @Path("id") String expenseId);
}
