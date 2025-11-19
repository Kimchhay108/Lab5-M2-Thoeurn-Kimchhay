package com.example.projectjava;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class sign_up extends AppCompatActivity {

    private EditText inputEmail, inputPassword, inputFullName;
    private Button btnRegister;
    private TextView txtLogin;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Initialize Firebase Auth and Firestore
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Link UI elements
        inputEmail = findViewById(R.id.input_email);
        inputPassword = findViewById(R.id.input_password);
        inputFullName = findViewById(R.id.input_fullname);
        btnRegister = findViewById(R.id.btn_register);
        txtLogin = findViewById(R.id.login_right);

        // Navigate to Login screen if user clicks "Already have an account"
        txtLogin.setOnClickListener(v -> startActivity(new Intent(sign_up.this, log_in.class)));

        // Register button click
        btnRegister.setOnClickListener(v -> {
            String email = inputEmail.getText().toString().trim();
            String password = inputPassword.getText().toString().trim();
            String fullName = inputFullName.getText().toString().trim();

            // Simple validations
            if (email.isEmpty()) { inputEmail.setError("Email required"); return; }
            if (password.isEmpty()) { inputPassword.setError("Password required"); return; }
            if (fullName.isEmpty()) { inputFullName.setError("Full Name required"); return; }

            // Firebase Authentication
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            // Firebase sign-up succeeded, add user to Firestore
                            String userId = mAuth.getCurrentUser().getUid();
                            Map<String, Object> user = new HashMap<>();
                            user.put("fullName", fullName);
                            user.put("email", email);

                            db.collection("users").document(userId)
                                    .set(user)
                                    .addOnSuccessListener(aVoid -> {
                                        Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show();
                                        // Go to login screen
                                        startActivity(new Intent(sign_up.this, log_in.class));
                                        finish();
                                    })
                                    .addOnFailureListener(e ->
                                            Toast.makeText(this, "Firestore error: " + e.getMessage(), Toast.LENGTH_LONG).show()
                                    );

                        } else {
                            // If sign-up fails, show detailed error
                            Exception e = task.getException();
                            Toast.makeText(this, "Sign-up failed: " + (e != null ? e.getMessage() : "Unknown error"), Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    });
        });
    }
}
