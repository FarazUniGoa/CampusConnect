package com.example.campusconnect;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    private ImageView imageView; // Declare ImageView rpszr5pxhw6b
    private TextView bookTextView; // Declare TextView for "BOOK"
    private TextView bookTextView3; // Declare TextView for "BOOK"
    private TextView bookTextView2; // Declare another TextView for navigation


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Initialize the views ru0uukg43uva
        bookTextView3 = findViewById(R.id.ru0uukg43uva);
        imageView = findViewById(R.id.rnyca6tj3vwr); // Replace with your actual ImageView ID if needed
        bookTextView = findViewById(R.id.rmkf9d6l4rw8); // Reference the first TextView for "BOOK"
        bookTextView2 = findViewById(R.id.rjzu5qnjthv); // Reference the second TextView for navigation

        // Set OnClickListener for the first TextView
        bookTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start FeaturedActivity when the first "BOOK" is clicked
                Intent intent = new Intent(HomeActivity.this, FeaturedActivity.class);
                startActivity(intent);
            }
        });

        // Set OnClickListener for the first TextView
        bookTextView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start FeaturedActivity when the first "BOOK" is clicked
                Intent intent = new Intent(HomeActivity.this, Profile.class);
                startActivity(intent);
            }
        });

        // Set OnClickListener for the second TextView
        bookTextView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start undergroundparty Activity when the second TextView is clicked
                Intent intent = new Intent(HomeActivity.this, undergroundparty.class);
                startActivity(intent);
            }
        });
    }
}
