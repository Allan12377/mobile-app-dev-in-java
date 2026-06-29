package com.example.spinner;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;



/**
 * MainActivity class that handles the Weather Information Application logic.
 * This app allows users to search for a city's weather using the OpenWeatherMap API.
 */
public class MainActivity extends AppCompatActivity {

    // --- UI Elements ---
    // EditText where the user types the city name
    EditText citySearch;
    // Button that triggers the network request
    Button getWeatherBtn;
    // TextViews to display the results fetched from the API
    TextView cityName, temp, condition;
    // ProgressBar to show a loading indicator during the network call
    ProgressBar loader;
    // The container layout for results, used to show/hide the results section
    View resultSection;

    // --- API Configuration ---
    // Your unique API Key from OpenWeatherMap (used to authorize requests)
    private final String API_KEY = "6543b59df39371076b1f3c307044238e"; 
    // The base URL for the weather API
    private final String BASE_URL = "https://api.openweathermap.org/data/2.5/weather?q=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Link the Java code to the XML layout file
        setContentView(R.layout.activity_main);

        // 1. Initialize UI elements by finding them by their IDs defined in activity_main.xml
        citySearch = findViewById(R.id.citySearch);
        getWeatherBtn = findViewById(R.id.getWeatherBtn);
        cityName = findViewById(R.id.cityName);
        temp = findViewById(R.id.temp);
        condition = findViewById(R.id.condition);
        loader = findViewById(R.id.loader);
        resultSection = findViewById(R.id.resultSection);

        // 2. Set up the Click Listener for the "Get Weather" button
        getWeatherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the text from the search box and remove extra spaces
                String city = citySearch.getText().toString().trim();
                
                // Simple Validation: Check if the user actually typed something
                if (city.isEmpty()) {
                    citySearch.setError("Enter a city name");
                } else {
                    // Start the process to fetch weather data for the entered city
                    getWeatherData(city);
                }
            }
        });
    }

    /**
     * This method performs the network request to fetch weather data.
     * @param city The name of the city to get weather for.
     */
    private void getWeatherData(String city) {
        // Prepare the UI: Show the loading spinner and hide any previous results
        loader.setVisibility(View.VISIBLE);
        resultSection.setVisibility(View.INVISIBLE);

        // Construct the full API URL (City + API Key + Units in Celsius)
        String url = BASE_URL + city + "&appid=" + API_KEY + "&units=metric";

        // Initialize the Volley RequestQueue (handles the background network work)
        RequestQueue queue = Volley.newRequestQueue(this);

        // Create a JsonObjectRequest to fetch the weather data (which comes in JSON format)
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Once data is received, hide the loading spinner
                        loader.setVisibility(View.GONE); 
                        try {
                            // --- Parsing the JSON response ---
                            // 1. Get the city name from the "name" field
                            String name = response.getString("name");
                            
                            // 2. Get the main object which contains the temperature
                            JSONObject main = response.getJSONObject("main");
                            double temperature = main.getDouble("temp");
                            
                            // 3. Get the weather condition from the first item in the "weather" array
                            String weatherCond = response.getJSONArray("weather")
                                    .getJSONObject(0).getString("main");

                            // --- Update the UI with the fetched data ---
                            cityName.setText(name);
                            temp.setText(temperature + "°C");
                            condition.setText(weatherCond);
                            
                            // Make the results section visible to the user
                            resultSection.setVisibility(View.VISIBLE); 
                            
                        } catch (JSONException e) {
                            // Handle cases where the JSON data might be formatted incorrectly
                            Toast.makeText(MainActivity.this, "Error parsing data", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle network errors (e.g., no internet, wrong city name, etc.)
                loader.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, "Unable to fetch weather. Please check your connection.", Toast.LENGTH_LONG).show();
            }
        });

        // Add the request to the queue to execute it
        queue.add(request);
    }
}
