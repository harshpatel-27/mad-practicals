package com.example.sharedpreferencesapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    LinearLayout mainLayout;
    Button btnRed, btnGreen, btnBlue;

    SharedPreferences sharedPreferences;
    String PREF_NAME = "ColorPref";
    String KEY_COLOR = "backgroundColor";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainLayout = findViewById(R.id.mainLayout);
        btnRed = findViewById(R.id.btnRed);
        btnGreen = findViewById(R.id.btnGreen);
        btnBlue = findViewById(R.id.btnBlue);

        // Load SharedPreferences
        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        String savedColor = sharedPreferences.getString(KEY_COLOR, "WHITE"); // default White

        // Apply saved color
        applyColor(savedColor);

        // Button Listeners
        btnRed.setOnClickListener(v -> saveAndApplyColor("RED"));
        btnGreen.setOnClickListener(v -> saveAndApplyColor("GREEN"));
        btnBlue.setOnClickListener(v -> saveAndApplyColor("BLUE"));
    }

    private void saveAndApplyColor(String color) {
        // Save in SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_COLOR, color);
        editor.apply();

        // Apply color immediately
        applyColor(color);
    }

    private void applyColor(String color) {
        switch (color) {
            case "RED":
                mainLayout.setBackgroundColor(Color.RED);
                break;
            case "GREEN":
                mainLayout.setBackgroundColor(Color.GREEN);
                break;
            case "BLUE":
                mainLayout.setBackgroundColor(Color.BLUE);
                break;
            default:
                mainLayout.setBackgroundColor(Color.WHITE);
        }
    }
}