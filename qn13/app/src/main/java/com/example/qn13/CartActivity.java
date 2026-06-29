package com.example.qn13;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {
    private CartManager cartManager;
    private ArrayAdapter<Product> adapter;
    private TextView tvTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        // Retrieve cart data passed via Intent
        ArrayList<Product> cartItems = (ArrayList<Product>) getIntent().getSerializableExtra("cart_data");
        cartManager = new CartManager(cartItems); // Using Controller/Manager for logic

        ListView cartListView = findViewById(R.id.cartListView);
        tvTotal = findViewById(R.id.tvTotal);

        // Adapter to display cart items with a "Remove" button
        adapter = new ArrayAdapter<Product>(this, R.layout.product_item, cartManager.getCartItems()) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = getLayoutInflater().inflate(R.layout.product_item, parent, false);
                }
                Product product = cartManager.getCartItems().get(position);
                
                TextView name = convertView.findViewById(R.id.tvProductName);
                TextView price = convertView.findViewById(R.id.tvProductPrice);
                Button btnAction = convertView.findViewById(R.id.btnAdd); // Reuse button as "Remove"

                name.setText(product.getName());
                price.setText(String.format("Price: $%.2f", product.getPrice()));
                btnAction.setText("Remove"); // Change label to Remove

                btnAction.setOnClickListener(v -> {
                    cartManager.removeFromCart(position); // Logic handled by CartManager
                    notifyDataSetChanged(); // Refresh the list
                    updateTotal(); // Recalculate total
                });

                return convertView;
            }
        };
        cartListView.setAdapter(adapter);
        updateTotal(); // Initial total calculation
    }

    // Method to update the total label
    private void updateTotal() {
        tvTotal.setText(String.format("Total: $%.2f", cartManager.getTotalPrice()));
    }
}
