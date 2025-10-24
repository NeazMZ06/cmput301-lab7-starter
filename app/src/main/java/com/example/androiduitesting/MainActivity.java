package com.example.androiduitesting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // Declare the variables so that you will be able to reference them later.
    ListView cityList;
    EditText newName;
    LinearLayout nameField;
    ArrayAdapter<String> cityAdapter;
    ArrayList<String> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameField = findViewById(R.id.field_nameEntry);
        newName = findViewById(R.id.editText_name);
        cityList = findViewById(R.id.city_list);

        dataList = new ArrayList<>();
        cityAdapter = new ArrayAdapter<>(this, R.layout.content, dataList);
        cityList.setAdapter(cityAdapter);

        // Add button: shows the input field
        final Button addButton = findViewById(R.id.button_add);
        addButton.setOnClickListener(v -> nameField.setVisibility(View.VISIBLE));

        // Confirm button: adds a new city
        final Button confirmButton = findViewById(R.id.button_confirm);
        confirmButton.setOnClickListener(v -> {
            String cityName = newName.getText().toString().trim();
            if (!cityName.isEmpty()) {
                cityAdapter.add(cityName);
                newName.getText().clear();
            }
            nameField.setVisibility(View.INVISIBLE);
        });

        // Clear button: removes all cities
        final Button deleteButton = findViewById(R.id.button_clear);
        deleteButton.setOnClickListener(v -> cityAdapter.clear());

        // ✅ NEW CODE: When user clicks a city, open ShowActivity
        cityList.setOnItemClickListener((parent, view, position, id) -> {
            String selectedCity = dataList.get(position);
            Intent intent = new Intent(MainActivity.this, ShowActivity.class);
            intent.putExtra(ShowActivity.EXTRA_CITY_NAME, selectedCity);
            startActivity(intent);
        });
    }
}