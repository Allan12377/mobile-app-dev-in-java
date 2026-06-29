package com.example.qn14;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the layout for the Detail Screen
        setContentView(R.layout.activity_detail);

        // Find views in the layout
        ImageView imageView = findViewById(R.id.detailImageView);
        TextView nameTextView = findViewById(R.id.detailNameTextView);
        TextView descTextView = findViewById(R.id.detailDescriptionTextView);
        Button backButton = findViewById(R.id.backButton);
        Button shareButton = findViewById(R.id.shareButton);

        // Get data passed from MainActivity via Intent
        String name = getIntent().getStringExtra("attr_name");
        String description = getIntent().getStringExtra("attr_desc");
        int imageResId = getIntent().getIntExtra("attr_image", 0);

        // Populate the views with the received data
        nameTextView.setText(name);
        descTextView.setText(description);
        imageView.setImageResource(imageResId);

        // Back button functionality - returns to previous screen
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Closes current activity and goes back
            }
        });

        // Share button functionality - Implicit Intent to share text
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an implicit intent to send text
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                // Put the attraction name and description as the text to share
                String shareText = "Check out this place: " + name + "\n\n" + description;
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
                // Show the app chooser to the user
                startActivity(Intent.createChooser(shareIntent, "Share via"));
            }
        });
    }
}
