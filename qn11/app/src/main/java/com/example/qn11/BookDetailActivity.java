package com.example.qn11;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class BookDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        // 1. Get the data passed from MainActivity via Intent
        String title = getIntent().getStringExtra("BOOK_TITLE");
        
        // 2. Find the TextViews in the detail layout
        TextView tvTitle = findViewById(R.id.tv_detail_title);
        TextView tvAuthor = findViewById(R.id.tv_detail_author);
        TextView tvStatus = findViewById(R.id.tv_detail_status);

        // 3. Set the data to the TextViews
        tvTitle.setText(title);
        tvAuthor.setText("Author: Unknown"); // Static for this simple example
        tvStatus.setText("Status: Available"); // Static for this simple example

        // Add a simple enter transition animation
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
