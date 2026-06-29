package com.example.qn13;

import java.util.ArrayList;

// Controller class to manage cart operations
public class CartManager {
    private ArrayList<Product> cartItems;

    public CartManager(ArrayList<Product> cartItems) {
        this.cartItems = cartItems;
    }

    // Method to add a product to the cart
    public void addToCart(Product product) {
        cartItems.add(product);
    }

    // Method to remove a product from the cart
    public void removeFromCart(int index) {
        if (index >= 0 && index < cartItems.size()) {
            cartItems.remove(index);
        }
    }

    // Method to calculate the total price of items in the cart
    public double getTotalPrice() {
        double total = 0;
        for (Product product : cartItems) {
            total += product.getPrice();
        }
        return total;
    }

    public ArrayList<Product> getCartItems() {
        return cartItems;
    }
}
