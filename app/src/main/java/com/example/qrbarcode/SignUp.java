package com.example.qrbarcode;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {

    private EditText nameField, emailField, passwordField, cardNumberField;
    private FirebaseAuth auth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        nameField = findViewById(R.id.First_Name);
        emailField = findViewById(R.id.signup_email);
        passwordField = findViewById(R.id.signup_password);
        cardNumberField = findViewById(R.id.Card_number);
        Button signUpButton = findViewById(R.id.signup_button);
        TextView loginRedirect = findViewById(R.id.loginRedirectText);

        // ✅ Sign Up Button Click
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpUser();
            }
        });

        // ✅ Redirect to Login
        loginRedirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUp.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void signUpUser() {
        String name = nameField.getText().toString().trim();
        String email = emailField.getText().toString().trim();
        String password = passwordField.getText().toString().trim();
        String cardNumber = cardNumberField.getText().toString().trim();

        // ✅ Check if fields are empty
        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || cardNumber.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // ✅ Check if password is at least 6 characters
        if (password.length() < 6) {
            Toast.makeText(this, "Password must be at least 6 characters!", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = auth.getCurrentUser();
                        if (user != null) {
                            saveUserData(user.getUid(), name, email, cardNumber);
                        }
                    } else {
                        Exception exception = task.getException();
                        if (exception != null) {
                            Log.e("SignUp", "Signup Error: " + exception.getMessage());
                            Toast.makeText(SignUp.this, "Signup Failed: " + exception.getMessage(), Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(SignUp.this, "Signup Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void saveUserData(String userId, String name, String email, String cardNumber) {
        Map<String, Object> user = new HashMap<>();
        user.put("name", name);
        user.put("email", email);
        user.put("cardNumber", cardNumber);

        db.collection("users").document(userId)
                .set(user)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(SignUp.this, "Signup Successful!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignUp.this, MainActivity.class));
                    finish();
                })
                .addOnFailureListener(e -> Log.e("Firestore", "Error saving user data", e));
    }
}
