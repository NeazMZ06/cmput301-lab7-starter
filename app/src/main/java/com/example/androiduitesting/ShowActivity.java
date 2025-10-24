package com.example.androiduitesting;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ShowActivity extends AppCompatActivity {

    public static final String EXTRA_CITY_NAME = "EXTRA_CITY_NAME";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        TextView cityText = findViewById(R.id.text_city_name);
        Button back = findViewById(R.id.button_back);

        // Get the city name passed from MainActivity and show it
        Intent intent = getIntent();
        String city = intent != null ? intent.getStringExtra(EXTRA_CITY_NAME) : null;
        if (city != null) {
            cityText.setText(city);
        }

        // Back button returns to MainActivity
        back.setOnClickListener(v -> finish());
    }
}
