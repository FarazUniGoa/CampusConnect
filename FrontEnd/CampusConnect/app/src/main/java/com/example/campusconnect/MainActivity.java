package com.example.campusconnect;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText userIdInput;
    private EditText passwordInput;
    private Button loginButton;
    private CheckBox rememberMeCheckbox;
    private TextView signUpText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Ensure this matches your XML layout filename

        // Initialize UI elements
        userIdInput = findViewById(R.id.userIdInput);
        passwordInput = findViewById(R.id.passwordInput);
        loginButton = findViewById(R.id.loginButton);
        rememberMeCheckbox = findViewById(R.id.rememberMeCheckbox);
        signUpText = findViewById(R.id.signUpText);

        // Set the click listener for the login button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performLogin();
            }
        });

        // Optional: Handle the Sign Up Text click
        signUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle sign-up logic or navigate to the sign-up activity
                Toast.makeText(MainActivity.this, "Navigate to Sign Up", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void performLogin() {
        String userId = userIdInput.getText().toString();
        String password = passwordInput.getText().toString();

        // Simple validation (replace with your own logic)
        if (userId.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter User ID and Password", Toast.LENGTH_SHORT).show();
        } else {
            // Here you would normally perform your login logic
            Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();

            // Navigate to HomeActivity (or another activity)
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(intent);
            finish(); // Close the current activity if desired
        }
    }
}
