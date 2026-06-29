package com.example.qn14;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

// Custom Adapter to display Attraction objects in a ListView
public class AttractionAdapter extends ArrayAdapter<Attraction> {

    public AttractionAdapter(Context context, ArrayList<Attraction> attractions) {
        super(context, 0, attractions);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Attraction attraction = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_attraction, parent, false);
        }

        // Lookup view for data population
        ImageView imageView = convertView.findViewById(R.id.itemImageView);
        TextView nameTextView = convertView.findViewById(R.id.itemNameTextView);

        // Populate the data into the template view using the data object
        nameTextView.setText(attraction.getName());
        imageView.setImageResource(attraction.getImageResourceId());

        // Return the completed view to render on screen
        return convertView;
    }
}
