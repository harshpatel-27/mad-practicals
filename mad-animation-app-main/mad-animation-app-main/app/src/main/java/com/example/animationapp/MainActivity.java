package com.example.animationapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.*;

public class MainActivity extends AppCompatActivity {

    Spinner spinner;
    ImageView imageView;

    String[] animations = {"Rotate", "Fade", "Zoom"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = findViewById(R.id.spinnerAnimations);
        imageView = findViewById(R.id.imageView);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, animations);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, android.view.View view, int position, long id) {
                Animation anim = null;

                switch (animations[position]) {
                    case "Rotate":
                        anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);
                        break;
                    case "Fade":
                        anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade);
                        break;
                    case "Zoom":
                        anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom);
                        break;
                }

                if (anim != null) {
                    imageView.startAnimation(anim);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }
}