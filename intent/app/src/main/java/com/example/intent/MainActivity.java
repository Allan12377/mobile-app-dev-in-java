package com.example.intent;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find the button from the layout
        Button myButton = findViewById(R.id.myButton);

        // Set a click listener on the button
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 1. Create an Intent. 
                // An Intent is like a "message" that tells Android you want to do something.
                // Here, we want to go from 'MainActivity' (this) to 'SecondActivity'.
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);

                // 2. You can also pass data with the intent using 'putExtra'
                intent.putExtra("user_name", "John Doe");

                // 3. Start the activity
                startActivity(intent);
            }
        });
    }
}