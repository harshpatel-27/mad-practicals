package com.example.generatedynamiclist;

import static com.example.generatedynamiclist.R.layout.activity_second;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {
    LinearLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_second);

        container = findViewById(R.id.container);

        // Get number from intent
        int count = getIntent().getIntExtra("count", 0);

        // Dynamically generate TextViews
        for (int i = 1; i <= count; i++) {
            TextView tv = new TextView(this);
            tv.setText("Item " + i);
            tv.setTextSize(18f);
            tv.setPadding(10, 10, 10, 10);
            container.addView(tv);
        }
    }
}