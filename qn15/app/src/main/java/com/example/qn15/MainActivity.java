package com.example.qn15;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    // Declare UI components
    private EditText etName, etAddress, etDescription;
    private Spinner spinnerCategory;
    private Button btnSubmit;
    private TextView tvConfirmation, lastComplaintText;

    // SharedPreferences for local storage
    private SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "ComplaintPrefs";
    private static final String KEY_LAST_COMPLAINT = "last_complaint";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI components
        etName = findViewById(R.id.etName);
        etAddress = findViewById(R.id.etAddress);
        etDescription = findViewById(R.id.etDescription);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        btnSubmit = findViewById(R.id.btnSubmit);
        tvConfirmation = findViewById(R.id.tvConfirmation);
        lastComplaintText = findViewById(R.id.lastComplaintText);

        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        // Display the last submitted complaint from SharedPreferences
        displayLastComplaint();

        // Handle Submit Button Click
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateAndSubmit();
            }
        });
    }

    private void validateAndSubmit() {
        // Get input values
        String name = etName.getText().toString().trim();
        String address = etAddress.getText().toString().trim();
        String description = etDescription.getText().toString().trim();
        String category = spinnerCategory.getSelectedItem().toString();

        // 1. Thorough Input Validation
        boolean isValid = true;

        if (name.isEmpty()) {
            etName.setError("Name is required");
            isValid = false;
        }
        if (address.isEmpty()) {
            etAddress.setError("Address is required");
            isValid = false;
        }
        if (description.isEmpty()) {
            etDescription.setError("Description is required");
            isValid = false;
        } else if (description.length() < 20) {
            etDescription.setError("Description must be at least 20 characters");
            isValid = false;
        }

        if (isValid) {
            // 2. Automatically generate reference number
            int refNumber = new Random().nextInt(900000) + 100000; // 6-digit random number
            String confirmationMsg = "Complaint Submitted!\nRef: #" + refNumber;
            
            // 3. Display confirmation message
            tvConfirmation.setText(confirmationMsg);
            Toast.makeText(this, "Submission Successful", Toast.LENGTH_SHORT).show();

            // 4. Save to SharedPreferences
            String record = "Last Report: " + category + " at " + address + " (Ref: #" + refNumber + ")";
            sharedPreferences.edit().putString(KEY_LAST_COMPLAINT, record).apply();

            // Update UI to show the last complaint
            displayLastComplaint();
            
            // Optional: Clear form
            clearForm();
        }
    }

    private void displayLastComplaint() {
        String lastRecord = sharedPreferences.getString(KEY_LAST_COMPLAINT, null);
        if (lastRecord != null) {
            lastComplaintText.setText(lastRecord);
            lastComplaintText.setVisibility(View.VISIBLE);
        }
    }

    private void clearForm() {
        etName.setText("");
        etAddress.setText("");
        etDescription.setText("");
        spinnerCategory.setSelection(0);
    }
}
