package com.example.qn10;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class MainActivity extends AppCompatActivity {

    // Declare UI components
    private TextView tvLatitude, tvLongitude, tvMapLink;
    private Button btnGetLocation, btnShareLocation;
    private LocationManager locationManager;
    private static final int LOCATION_REQUEST_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Set the layout file

        // Initialize UI components by their IDs from the XML
        tvLatitude = findViewById(R.id.tvLatitude);
        tvLongitude = findViewById(R.id.tvLongitude);
        tvMapLink = findViewById(R.id.tvMapLink);
        btnGetLocation = findViewById(R.id.btnGetLocation);
        btnShareLocation = findViewById(R.id.btnShareLocation);

        // Initialize LocationManager to access system location services
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // Set listener for the "Get My Location" button
        btnGetLocation.setOnClickListener(v -> checkPermissionAndGetLocation());

        // Set listener for the "Share Location" button
        btnShareLocation.setOnClickListener(v -> shareCurrentLocation());
    }

    private void checkPermissionAndGetLocation() {
        // Check if GPS permission is granted at runtime
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // If not granted, request the permission from the user
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
        } else {
            // If permission is already granted, proceed to get coordinates
            retrieveLocation();
        }
    }

    private void retrieveLocation() {
        // Check if GPS is turned on
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(this, "Location not available. Please turn on GPS.", Toast.LENGTH_LONG).show();
            // Guide user to Location settings to enable GPS
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            return;
        }

        try {
            // Get the last known location from the GPS provider
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null) {
                // Update TextViews with retrieved Latitude and Longitude
                double lat = location.getLatitude();
                double lon = location.getLongitude();
                tvLatitude.setText("Latitude: " + lat);
                tvLongitude.setText("Longitude: " + lon);
                // Update map link label with current coordinates
                tvMapLink.setText("Coordinates: " + lat + ", " + lon + "\nTap Share to send to office.");
            } else {
                Toast.makeText(this, "Waiting for GPS signal...", Toast.LENGTH_SHORT).show();
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    private void shareCurrentLocation() {
        String latText = tvLatitude.getText().toString();
        String lonText = tvLongitude.getText().toString();

        // Validate if location is already fetched before sharing
        if (latText.contains("--")) {
            Toast.makeText(this, "Please get location first!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Prepare the message body for sharing
        String message = "BodaBoda Rider Location:\n" + latText + "\n" + lonText +
                "\nView on Map: https://www.google.com/maps/search/?api=1&query=" + 
                latText.replace("Latitude: ", "") + "," + lonText.replace("Longitude: ", "");

        // Create an Intent to share the text message
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, message);
        sendIntent.setType("text/plain");

        // Start the activity to show sharing options (e.g., WhatsApp, SMS, Email)
        startActivity(Intent.createChooser(sendIntent, "Share Location via"));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_REQUEST_CODE) {
            // Check if the user granted the requested permission
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                retrieveLocation(); // Permission granted, get location
            } else {
                // Explain why the permission is needed if denied
                Toast.makeText(this, "Permission Denied! Location access is required for this app.", Toast.LENGTH_LONG).show();
            }
        }
    }
}