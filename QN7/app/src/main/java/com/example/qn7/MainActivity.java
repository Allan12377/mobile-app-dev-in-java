package com.example.qn7;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

// Main class that controls the screen's behavior
public class MainActivity extends AppCompatActivity {

    // A simple data model to store information about each meal
    static class MenuItem {
        String name;        // Name of the meal
        String description; // Details about the meal
        String price;       // Cost of the meal
        String category;    // Category: Breakfast, Lunch, or Dinner

        // Constructor to easily create a new MenuItem object
        MenuItem(String name, String description, String price, String category) {
            this.name = name;
            this.description = description;
            this.price = price;
            this.category = category;
        }
    }

    // List to store all our menu items
    private List<MenuItem> allMenuItems;
    // Container in the XML where we will add the menu cards
    private LinearLayout menuContainer;
    // Category selection buttons
    private Button btnBreakfast, btnLunch, btnDinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Link this Java file to the activity_main.xml layout
        setContentView(R.layout.activity_main);

        // 1. Link our Java variables to the views in the XML using their IDs
        menuContainer = findViewById(R.id.menuContainer);
        btnBreakfast = findViewById(R.id.btnBreakfast);
        btnLunch = findViewById(R.id.btnLunch);
        btnDinner = findViewById(R.id.btnDinner);

        // 2. Initialize the list with some sample restaurant data
        setupData();

        // 3. Set click listeners to handle what happens when a button is pressed
        // When Breakfast is clicked, call displayCategory with "Breakfast"
        btnBreakfast.setOnClickListener(v -> displayCategory("Breakfast"));
        // When Lunch is clicked, call displayCategory with "Lunch"
        btnLunch.setOnClickListener(v -> displayCategory("Lunch"));
        // When Dinner is clicked, call displayCategory with "Dinner"
        btnDinner.setOnClickListener(v -> displayCategory("Dinner"));

        // 4. Show Breakfast items by default when the app first opens
        displayCategory("Breakfast");
    }

    // Method to create the dummy data for our menu
    private void setupData() {
        allMenuItems = new ArrayList<>();
        // Adding Breakfast items to the list
        allMenuItems.add(new MenuItem("Pancakes", "Golden pancakes with maple syrup", "$8.50", "Breakfast"));
        allMenuItems.add(new MenuItem("Fruit Bowl", "Fresh seasonal fruits with yogurt", "$6.00", "Breakfast"));
        // Adding Lunch items to the list
        allMenuItems.add(new MenuItem("Beef Burger", "Juicy patty with cheese and fries", "$12.00", "Lunch"));
        allMenuItems.add(new MenuItem("Caesar Salad", "Crispy romaine with grilled chicken", "$10.50", "Lunch"));
        // Adding Dinner items to the list
        allMenuItems.add(new MenuItem("Grilled Steak", "Served with garlic mash and asparagus", "$25.00", "Dinner"));
        allMenuItems.add(new MenuItem("Pasta Alfredo", "Creamy sauce with fettuccine", "$18.00", "Dinner"));
        allMenuItems.add(new MenuItem("Roasted Chicken", "Half chicken with herbs and lemon", "$20.00", "Dinner"));
    }

    // Method to filter and show only items from a specific category
    private void displayCategory(String category) {
        // Clear all currently visible menu items from the container
        menuContainer.removeAllViews();

        // Change button colors to highlight which one is active
        updateButtonColors(category);

        // Loop through every item in our list
        for (MenuItem item : allMenuItems) {
            // If the item matches the category we want, show it on the screen
            if (item.category.equals(category)) {
                addCardToLayout(item);
            }
        }
    }

    // Helper method to change button colors for a better user experience
    private void updateButtonColors(String selected) {
        // Get our custom colors from the resources
        int gold = getResources().getColor(R.color.restaurant_gold);
        int dark = getResources().getColor(R.color.restaurant_dark);

        // If a button matches the selected category, make it gold, otherwise dark
        btnBreakfast.setBackgroundColor(selected.equals("Breakfast") ? gold : dark);
        btnLunch.setBackgroundColor(selected.equals("Lunch") ? gold : dark);
        btnDinner.setBackgroundColor(selected.equals("Dinner") ? gold : dark);
    }

    // Method to create a "Card" for a specific menu item and add it to the UI
    private void addCardToLayout(MenuItem item) {
        // Convert the XML file (item_menu.xml) into a View object we can use in Java
        View itemView = LayoutInflater.from(this).inflate(R.layout.item_menu, menuContainer, false);

        // Find the TextViews inside that newly created card
        TextView nameText = itemView.findViewById(R.id.tvMealName);
        TextView descText = itemView.findViewById(R.id.tvDescription);
        TextView priceText = itemView.findViewById(R.id.tvPrice);

        // Fill those TextViews with the meal's data
        nameText.setText(item.name);
        descText.setText(item.description);
        priceText.setText(item.price);

        // Add the finished card into the main scrollable list (menuContainer)
        menuContainer.addView(itemView);
    }
}
