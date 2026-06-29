package com.example.qn1practical;

// These are "imports" which tell Android which tools we want to use
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // 1. Declare variables for our screen elements (EditTexts and Buttons)
    EditText etName, etAge, etClass, etContact;
    Button btnSubmit, btnReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // 2. This connects this Java code to your layout file (activity_main.xml)
        setContentView(R.layout.activity_main);

        // 3. Link our Java variables to the actual elements on the screen using their IDs
        etName = findViewById(R.id.etStudentName);
        etAge = findViewById(R.id.etStudentAge);
        etClass = findViewById(R.id.etClassLevel);
        etContact = findViewById(R.id.ParentContact);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnReset = findViewById(R.id.btnReset);

        // 4. Set up what happens when the "Submit" button is clicked
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the text that the user typed into the boxes
                String name = etName.getText().toString();
                String age = etAge.getText().toString();
                String classLevel = etClass.getText().toString();
                String contact = etContact.getText().toString();

                // Check if any box is empty
                if (name.isEmpty() || age.isEmpty() || classLevel.isEmpty() || contact.isEmpty()) {
                    // Show a message if something is missing
                    Toast.makeText(MainActivity.this, "Please fill in all fields!", Toast.LENGTH_SHORT).show();
                } else {
                    // Show a success message if everything is filled
                    Toast.makeText(MainActivity.this, "Submission Successful for: " + name, Toast.LENGTH_LONG).show();
                }
            }
        });

        // 5. Set up what happens when the "Reset" button is clicked
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Clear all the text boxes (make them empty)
                etName.setText("");
                etAge.setText("");
                etClass.setText("");
                etContact.setText("");

                // Show a quick message to let the user know it was reset
                Toast.makeText(MainActivity.this, "Form Cleared", Toast.LENGTH_SHORT).show();
            }
        });
    }
}