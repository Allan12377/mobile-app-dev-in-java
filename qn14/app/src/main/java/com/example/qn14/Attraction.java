package com.example.qn14;

// Custom class to represent a Tourist Attraction
public class Attraction {
    private String name;        // Name of the attraction
    private String description; // Detailed description
    private int imageResourceId; // ID of the image in drawable folder

    // Constructor to initialize the attraction object
    public Attraction(String name, String description, int imageResourceId) {
        this.name = name;
        this.description = description;
        this.imageResourceId = imageResourceId;
    }

    // Getter methods to retrieve the data
    public String getName() { return name; }
    public String getDescription() { return description; }
    public int getImageResourceId() { return imageResourceId; }
}
