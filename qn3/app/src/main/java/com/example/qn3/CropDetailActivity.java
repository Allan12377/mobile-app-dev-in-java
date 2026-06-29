package com.example.qn3;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class CropDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_detail);

        TextView tvCropName = findViewById(R.id.tvCropName);
        TextView tvSeason = findViewById(R.id.tvSeason);
        TextView tvCareTips = findViewById(R.id.tvCareTips);
        Button btnBack = findViewById(R.id.btnBack);

        String cropName = getIntent().getStringExtra("CROP_NAME");

        if (cropName != null) {
            tvCropName.setText(cropName);
            displayCropDetails(cropName, tvSeason, tvCareTips);
        }

        btnBack.setOnClickListener(v -> finish());
    }

    private void displayCropDetails(String name, TextView tvSeason, TextView tvCareTips) {
        switch (name) {
            case "Maize":
                tvSeason.setText("Season: Rainy Season");
                tvCareTips.setText("Care Tips: Requires fertile soil and regular weeding.");
                break;
            case "Beans":
                tvSeason.setText("Season: Moderate Rain");
                tvCareTips.setText("Care Tips: Needs well-drained soil and support for climbing.");
                break;
            case "Coffee":
                tvSeason.setText("Season: High Altitude / Cool");
                tvCareTips.setText("Care Tips: Requires pruning and protection from pests.");
                break;
            default:
                tvSeason.setText("Season: Unknown");
                tvCareTips.setText("Care Tips: Not available.");
                break;
        }
    }
}
