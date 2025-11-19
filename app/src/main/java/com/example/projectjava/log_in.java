package com.example.projectjava;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;

public class log_in extends AppCompatActivity {

    private EditText inputEmail, inputPassword;
    private Button btnLogin;
    private TextView txtSignUp;
    private FirebaseAuth mAuth; // <-- declare FirebaseAuth here

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Enable edge-to-edge display
        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_log_in);

        // Adjust for system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(
                    v.getPaddingLeft() + systemBars.left,
                    v.getPaddingTop() + systemBars.top,
                    v.getPaddingRight() + systemBars.right,
                    v.getPaddingBottom() + systemBars.bottom
            );
            return insets;
        });

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Connect XML views
        inputEmail = findViewById(R.id.input_email);
        inputPassword = findViewById(R.id.input_password);
        btnLogin = findViewById(R.id.btn_login);
        txtSignUp = findViewById(R.id.text_signup);

        // Login button click
        btnLogin.setOnClickListener(v -> {
            String email = inputEmail.getText().toString().trim();
            String password = inputPassword.getText().toString().trim();

            // Input validation
            if (email.isEmpty()) {
                inputEmail.setError("Email is required");
                inputEmail.requestFocus();
                return;
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                inputEmail.setError("Please enter a valid email");
                inputEmail.requestFocus();
                return;
            }
            if (password.isEmpty()) {
                inputPassword.setError("Password is required");
                inputPassword.requestFocus();
                return;
            }
            if (password.length() < 6) {
                inputPassword.setError("Password must be at least 6 characters");
                inputPassword.requestFocus();
                return;
            }

            // Firebase Authentication
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            // Login successful → go to MainActivity
                            Intent intent = new Intent(log_in.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // Login failed
                            Toast.makeText(log_in.this,
                                    "Login failed: " + task.getException().getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
        });

        // Navigate to sign up screen
        txtSignUp.setOnClickListener(v -> {
            startActivity(new Intent(log_in.this, sign_up.class));
        });
    }
}
