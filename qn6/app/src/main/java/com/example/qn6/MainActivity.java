package com.example.qn6;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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

public class MainActivity extends AppCompatActivity {

    private EditText etCityName;
    private Button btnGetWeather;
    private ProgressBar progressBar;
    private LinearLayout resultsLayout;
    private TextView tvCity, tvTemp, tvCondition;

    // Replace with your actual OpenWeatherMap API Key
    private final String API_KEY = "YOUR_API_KEY_HERE";
    private final String BASE_URL = "https://api.openweathermap.org/data/2.5/weather";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI components
        etCityName = findViewById(R.id.etCityName);
        btnGetWeather = findViewById(R.id.btnGetWeather);
        progressBar = findViewById(R.id.progressBar);
        resultsLayout = findViewById(R.id.resultsLayout);
        tvCity = findViewById(R.id.tvCity);
        tvTemp = findViewById(R.id.tvTemp);
        tvCondition = findViewById(R.id.tvCondition);

        btnGetWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city = etCityName.getText().toString().trim();
                if (city.isEmpty()) {
                    etCityName.setError("Please enter a city name");
                } else {
                    fetchWeather(city);
                }
            }
        });
    }

    private void fetchWeather(String city) {
        // Show loading indicator and hide previous results
        progressBar.setVisibility(View.VISIBLE);
        resultsLayout.setVisibility(View.GONE);

        // Construct the URL
        String url = BASE_URL + "?q=" + city + "&appid=" + API_KEY + "&units=metric";

        // Create a Volley Request
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressBar.setVisibility(View.GONE);
                        try {
                            // Parse JSON response
                            String cityName = response.getString("name");
                            JSONObject main = response.getJSONObject("main");
                            double temp = main.getDouble("temp");
                            String condition = response.getJSONArray("weather").getJSONObject(0).getString("description");

                            // Display data
                            tvCity.setText(cityName);
                            tvTemp.setText(String.format("%.1f°C", temp));
                            tvCondition.setText(condition.substring(0, 1).toUpperCase() + condition.substring(1));
                            
                            resultsLayout.setVisibility(View.VISIBLE);
                        } catch (JSONException e) {
                            Toast.makeText(MainActivity.this, "Error parsing data", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, "Unable to fetch weather. Please check your connection.", Toast.LENGTH_LONG).show();
            }
        });

        // Add the request to the RequestQueue
        queue.add(jsonObjectRequest);
    }
}
