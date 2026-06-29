package com.example.qn12;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // UI elements
    private EditText etName, etSubject, etScore;
    private Button btnAdd, btnClear;
    private ListView listView;
    private TextView tvCount;

    // Data handling
    private ArrayList<Student> studentList;
    private GradeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. Initialize UI elements
        etName = findViewById(R.id.et_name);
        etSubject = findViewById(R.id.et_subject);
        etScore = findViewById(R.id.et_score);
        btnAdd = findViewById(R.id.btn_add);
        btnClear = findViewById(R.id.btn_clear);
        listView = findViewById(R.id.list_view);
        tvCount = findViewById(R.id.tv_count);

        // 2. Initialize the list and adapter
        studentList = new ArrayList<>();
        adapter = new GradeAdapter(this, studentList);
        listView.setAdapter(adapter);

        // 3. Add Grade Button Click Listener
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addGrade();
            }
        });

        // 4. Clear All Button Click Listener
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearGrades();
            }
        });
    }

    private void addGrade() {
        // Read input values
        String name = etName.getText().toString().trim();
        String subject = etSubject.getText().toString().trim();
        String scoreStr = etScore.getText().toString().trim();

        // Validation: Ensure fields are not empty
        if (name.isEmpty() || subject.isEmpty() || scoreStr.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Convert score to integer
        int score = Integer.parseInt(scoreStr);

        // Create new Student object and add to list
        Student newStudent = new Student(name, subject, score);
        studentList.add(newStudent);

        // Notify adapter to refresh the ListView
        adapter.notifyDataSetChanged();

        // Update total count
        updateCount();

        // Clear input fields for next entry
        etName.setText("");
        etSubject.setText("");
        etScore.setText("");
        
        Toast.makeText(this, "Grade Added Successfully", Toast.LENGTH_SHORT).show();
    }

    private void clearGrades() {
        // Remove all items from the list
        studentList.clear();
        
        // Refresh UI
        adapter.notifyDataSetChanged();
        updateCount();
        
        Toast.makeText(this, "All entries cleared", Toast.LENGTH_SHORT).show();
    }

    private void updateCount() {
        // Update the count label with current list size
        tvCount.setText("Total entries: " + studentList.size());
    }
}
