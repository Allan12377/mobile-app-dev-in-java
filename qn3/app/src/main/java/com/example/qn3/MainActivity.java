package com.example.qn3;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Finding buttons from the layout
        Button btnMaize = findViewById(R.id.btnMaize);
        Button btnBeans = findViewById(R.id.btnBeans);
        Button btnCoffee = findViewById(R.id.btnCoffee);
        Button btnContactUs = findViewById(R.id.btnContactUs);

        // Setting click listeners for crop buttons
        btnMaize.setOnClickListener(v -> openCropDetail("Maize"));
        btnBeans.setOnClickListener(v -> openCropDetail("Beans"));
        btnCoffee.setOnClickListener(v -> openCropDetail("Coffee"));

        // Setting click listener for Contact Us button
        btnContactUs.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ContactActivity.class);
            startActivity(intent);
        });
    }

    // Helper method to open Detail Activity with a name
    private void openCropDetail(String cropName) {
        Intent intent = new Intent(MainActivity.this, CropDetailActivity.class);
        intent.putExtra("CROP_NAME", cropName); // Passing data as Intent Extra
        startActivity(intent);
    }
}
