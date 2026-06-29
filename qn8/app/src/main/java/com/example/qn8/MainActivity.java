package com.example.qn8;

// Import necessary Android classes
import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    // Declare UI variables to link with the XML layout
    private EditText etName, etPhone;
    private Spinner spinnerDoctor;
    private Button btnPickDate, btnConfirm;
    private TextView tvSelectedDate, tvSummary, tvLastBooking;
    
    // Variable to store the date chosen by the user
    private String selectedDate = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Connect this Java file with the activity_main.xml layout
        setContentView(R.layout.activity_main);

        // Link Java variables to the XML components using their IDs
        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);
        spinnerDoctor = findViewById(R.id.spinnerDoctor);
        btnPickDate = findViewById(R.id.btnPickDate);
        btnConfirm = findViewById(R.id.btnConfirm);
        tvSelectedDate = findViewById(R.id.tvSelectedDate);
        tvSummary = findViewById(R.id.tvSummary);
        tvLastBooking = findViewById(R.id.tvLastBooking);

        // 1. Setup Spinner: Create a list of doctors for the patient to choose from
        String[] doctors = {"Dr. Smith", "Dr. Johnson", "Dr. Williams", "Dr. Brown"};
        // Use an ArrayAdapter to tell the Spinner how to display the list items
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, doctors);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDoctor.setAdapter(adapter);

        // 2. Date Picker Logic: Handle clicking the "Select Appointment Date" button
        btnPickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the current date (today) to show in the calendar by default
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                // Open the Android DatePickerDialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this,
                        (view, year1, month1, dayOfMonth) -> {
                            // When date is picked, save it and show it on the screen
                            selectedDate = dayOfMonth + "/" + (month1 + 1) + "/" + year1;
                            tvSelectedDate.setText("Selected Date: " + selectedDate);
                        }, year, month, day);

                // Constraint: Setting minimum date to today (prevents picking past dates)
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show(); // Show the dialog to the user
            }
        });

        // 3. Confirm Booking Logic: Handle clicking the "Confirm Booking" button
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the text entered by the user
                String name = etName.getText().toString().trim();
                String phone = etPhone.getText().toString().trim();
                String doctor = spinnerDoctor.getSelectedItem().toString();

                // Validation: Check if name and phone fields are empty
                if (name.isEmpty() || phone.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter name and phone", Toast.LENGTH_SHORT).show();
                } 
                // Validation: Check if the user has picked a date
                else if (selectedDate.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please select an appointment date", Toast.LENGTH_SHORT).show();
                } 
                else {
                    // Create a summary message of the booking
                    String summary = "Booking Summary:\nName: " + name + "\nDoctor: " + doctor + "\nDate: " + selectedDate;
                    tvSummary.setText(summary); // Display summary in the TextView

                    // 4. Data Storage: Save the summary locally using SharedPreferences
                    SharedPreferences sharedPreferences = getSharedPreferences("BookingPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("lastBooking", summary); // Store the summary string
                    editor.apply(); // Save changes

                    Toast.makeText(MainActivity.this, "Booking Confirmed Successfully!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Load the saved booking info when the app starts
        loadLastBooking();
    }

    // Function to retrieve the most recent booking from SharedPreferences
    private void loadLastBooking() {
        SharedPreferences sharedPreferences = getSharedPreferences("BookingPrefs", MODE_PRIVATE);
        // If nothing is found, display "No previous booking"
        String lastBooking = sharedPreferences.getString("lastBooking", "No previous booking found.");
        tvLastBooking.setText("Last Appointment: \n" + lastBooking);
    }
}
