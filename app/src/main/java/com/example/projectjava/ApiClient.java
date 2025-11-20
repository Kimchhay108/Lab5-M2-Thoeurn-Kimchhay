package com.example.projectjava;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.util.Date;

public class ApiClient {
    private static final String BASE_URL = "https://expense-tracker-db-one.vercel.app/";
    private static Retrofit retrofit = null;

    public static ExpenseApi getApi() {
        if (retrofit == null) {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Date.class, new ISO8601DateAdapter())
                    .create();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit.create(ExpenseApi.class);
    }
}
