package com.example.sqlite;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

// This is the main screen of our app
public class MainActivity extends AppCompatActivity {

    // Declare variables for UI elements and DatabaseHelper
    EditText editTextName;
    Button buttonAdd, buttonShow;
    TextView textViewUsers;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Links this Java file to activity_main.xml

        // Initialize UI components by finding them in the XML layout
        editTextName = findViewById(R.id.editTextName);
        buttonAdd = findViewById(R.id.buttonAdd);
        buttonShow = findViewById(R.id.buttonShow);
        textViewUsers = findViewById(R.id.textViewUsers);

        // Initialize our database helper class
        dbHelper = new DatabaseHelper(this);

        // Action when 'Add User' button is clicked
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString(); // Get text from EditText

                if (!name.isEmpty()) {
                    dbHelper.addUser(name); // Save name to SQLite
                    editTextName.setText(""); // Clear the input field
                    Toast.makeText(MainActivity.this, "User Added!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Please enter a name", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Action when 'Show Users' button is clicked
        buttonShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String users = dbHelper.getAllUsers(); // Get list of names from SQLite
                textViewUsers.setText(users); // Display names in the TextView
            }
        });
    }
}
