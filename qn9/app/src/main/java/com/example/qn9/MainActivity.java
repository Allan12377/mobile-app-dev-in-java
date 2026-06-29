package com.example.qn9;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // Request code for camera intent
    private static final int REQUEST_IMAGE_CAPTURE = 1;

    // UI elements
    private EditText etFarmerName, etDescription;
    private ImageView ivCropPhoto;
    private TextView tvSummary;
    private Bitmap photoBitmap; // To store the captured image

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initializing views
        etFarmerName = findViewById(R.id.etFarmerName);
        etDescription = findViewById(R.id.etDescription);
        ivCropPhoto = findViewById(R.id.ivCropPhoto);
        tvSummary = findViewById(R.id.tvSummary);
        Button btnTakePhoto = findViewById(R.id.btnTakePhoto);
        Button btnSubmit = findViewById(R.id.btnSubmit);

        // i) Take Photo button logic
        btnTakePhoto.setOnClickListener(v -> {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // Basic intent to launch camera
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        });

        // iv) Submit Report button logic with validation
        btnSubmit.setOnClickListener(v -> {
            String name = etFarmerName.getText().toString().trim();
            String description = etDescription.getText().toString().trim();

            // Validation checks
            if (name.isEmpty()) {
                Toast.makeText(this, "Please enter farmer's name", Toast.LENGTH_SHORT).show();
            } else if (photoBitmap == null) {
                Toast.makeText(this, "Please take a photo of the diseased crop", Toast.LENGTH_SHORT).show();
            } else if (description.isEmpty()) {
                Toast.makeText(this, "Please enter a description", Toast.LENGTH_SHORT).show();
            } else {
                // Display confirmation Toast
                Toast.makeText(this, "Report Submitted Successfully!", Toast.LENGTH_LONG).show();

                // Display all details in summary TextView
                String summary = "--- REPORT SUMMARY ---\n" +
                        "Farmer Name: " + name + "\n" +
                        "Problem: " + description;
                tvSummary.setText(summary);
            }
        });
    }

    // ii) Displaying the captured photo after it is taken
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK && data != null) {
            // Get the thumbnail from the intent data
            Bundle extras = data.getExtras();
            photoBitmap = (Bitmap) extras.get("data");
            // Set the image in the ImageView
            ivCropPhoto.setImageBitmap(photoBitmap);
        }
    }
}
