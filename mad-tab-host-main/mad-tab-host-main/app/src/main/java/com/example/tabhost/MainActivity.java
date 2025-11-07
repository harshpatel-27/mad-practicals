package com.example.tabhost;

import android.os.Bundle;
import android.widget.TabHost;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    TabHost tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabHost = findViewById(android.R.id.tabhost);
        tabHost.setup();

        // Tab 1
        TabHost.TabSpec spec1 = tabHost.newTabSpec("Tab One");
        spec1.setContent(R.id.tab1);
        spec1.setIndicator("Home");
        tabHost.addTab(spec1);

        // Tab 2
        TabHost.TabSpec spec2 = tabHost.newTabSpec("Tab Two");
        spec2.setContent(R.id.tab2);
        spec2.setIndicator("Profile");
        tabHost.addTab(spec2);

        // Tab 3
        TabHost.TabSpec spec3 = tabHost.newTabSpec("Tab Three");
        spec3.setContent(R.id.tab3);
        spec3.setIndicator("Settings");
        tabHost.addTab(spec3);
    }
}
