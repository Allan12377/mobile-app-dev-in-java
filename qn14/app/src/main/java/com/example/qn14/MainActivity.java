package com.example.qn14;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the layout for the Home Screen
        setContentView(R.layout.activity_main);

        // Initialize the list of attractions
        ArrayList<Attraction> attractionList = new ArrayList<>();
        // Adding sample data (using default launcher icon as placeholder image)
        attractionList.add(new Attraction("Victoria Falls", "A spectacular waterfall located on the Zambezi River.", R.drawable.ic_launcher_background));
        attractionList.add(new Attraction("Table Mountain", "A flat-topped mountain forming a prominent landmark overlooking Cape Town.", R.drawable.ic_launcher_background));
        attractionList.add(new Attraction("Kruger Park", "One of the largest game reserves in Africa.", R.drawable.ic_launcher_background));

        // Create the custom adapter
        AttractionAdapter adapter = new AttractionAdapter(this, attractionList);

        // Find the ListView and attach the adapter
        ListView listView = findViewById(R.id.attractionListView);
        listView.setAdapter(adapter);

        // Handle item clicks to navigate to the Detail Screen
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected attraction
                Attraction selectedAttraction = attractionList.get(position);

                // Create an explicit Intent to open DetailActivity
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                // Pass data to the next activity
                intent.putExtra("attr_name", selectedAttraction.getName());
                intent.putExtra("attr_desc", selectedAttraction.getDescription());
                intent.putExtra("attr_image", selectedAttraction.getImageResourceId());

                // Start the activity
                startActivity(intent);
            }
        });
    }
}
