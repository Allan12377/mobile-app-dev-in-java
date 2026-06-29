package com.example.qn13;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Product> products;
    private ArrayList<Product> cart = new ArrayList<>(); // To store selected items

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize at least five products
        products = new ArrayList<>();
        products.add(new Product("Apple", 1.50));
        products.add(new Product("Milk", 2.20));
        products.add(new Product("Bread", 1.80));
        products.add(new Product("Eggs", 3.00));
        products.add(new Product("Rice", 5.50));

        ListView productListView = findViewById(R.id.productListView);
        Button btnViewCart = findViewById(R.id.btnViewCart);

        // Simple adapter to display products with an "Add" button
        ArrayAdapter<Product> adapter = new ArrayAdapter<Product>(this, R.layout.product_item, products) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = getLayoutInflater().inflate(R.layout.product_item, parent, false);
                }
                Product product = products.get(position);
                
                TextView name = convertView.findViewById(R.id.tvProductName);
                TextView price = convertView.findViewById(R.id.tvProductPrice);
                Button btnAdd = convertView.findViewById(R.id.btnAdd);

                name.setText(product.getName());
                price.setText(String.format("Price: $%.2f", product.getPrice()));

                btnAdd.setOnClickListener(v -> {
                    cart.add(product);
                    Toast.makeText(MainActivity.this, product.getName() + " added to cart", Toast.LENGTH_SHORT).show();
                });

                return convertView;
            }
        };
        productListView.setAdapter(adapter);

        // Open Cart screen and pass the current cart data
        btnViewCart.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CartActivity.class);
            intent.putExtra("cart_data", cart);
            startActivity(intent);
        });
    }
}
