package com.example.qn11;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // Declare variables for our UI layouts
    private LinearLayout layoutHome, layoutSearch;
    private ScrollView layoutMyBooks;
    
    // Data for the search tab
    private List<String> bookList;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. Initialize Layouts (Tabs)
        layoutHome = findViewById(R.id.layout_home);
        layoutSearch = findViewById(R.id.layout_search);
        layoutMyBooks = findViewById(R.id.layout_my_books);

        // 2. Setup Bottom Navigation
        BottomNavigationView navView = findViewById(R.id.bottom_navigation);
        navView.setOnItemSelectedListener(item -> {
            // Hide all layouts first
            layoutHome.setVisibility(View.GONE);
            layoutSearch.setVisibility(View.GONE);
            layoutMyBooks.setVisibility(View.GONE);

            // Show the layout corresponding to the clicked tab
            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {
                layoutHome.setVisibility(View.VISIBLE);
            } else if (itemId == R.id.nav_search) {
                layoutSearch.setVisibility(View.VISIBLE);
            } else if (itemId == R.id.nav_my_books) {
                layoutMyBooks.setVisibility(View.VISIBLE);
            }
            return true;
        });

        // 3. Setup Search Tab Logic
        setupSearchTab();
    }

    private void setupSearchTab() {
        EditText etSearch = findViewById(R.id.et_search);
        Button btnSearch = findViewById(R.id.btn_search);
        ListView lvBooks = findViewById(R.id.lv_books);

        // Initial book data
        bookList = new ArrayList<>();
        bookList.add("Android Programming");
        bookList.add("Java for Beginners");
        bookList.add("Data Structures");
        bookList.add("Clean Code");
        bookList.add("The Hobbit");

        // Set up the adapter for ListView
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, bookList);
        lvBooks.setAdapter(adapter);

        // Filter list when Search button is clicked
        btnSearch.setOnClickListener(v -> {
            String query = etSearch.getText().toString().toLowerCase();
            List<String> filteredList = new ArrayList<>();
            for (String book : bookList) {
                if (book.toLowerCase().contains(query)) {
                    filteredList.add(book);
                }
            }
            // Update ListView with filtered results
            ArrayAdapter<String> filteredAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, filteredList);
            lvBooks.setAdapter(filteredAdapter);
        });

        // Open BookDetailActivity when a book is clicked
        lvBooks.setOnItemClickListener((parent, view, position, id) -> {
            String selectedBook = (String) parent.getItemAtPosition(position);
            Intent intent = new Intent(MainActivity.this, BookDetailActivity.class);
            intent.putExtra("BOOK_TITLE", selectedBook);
            startActivity(intent);
        });
    }
}
