package com.example.bgcolorchanger;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    RelativeLayout mainLayout;
    TextView tvMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainLayout = findViewById(R.id.main_layout);
        tvMessage = findViewById(R.id.tvMessage);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.color_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.color_red) {
            mainLayout.setBackgroundColor(Color.RED);
            tvMessage.setText("Background: Red");
            return true;
        } else if (id == R.id.color_green) {
            mainLayout.setBackgroundColor(Color.GREEN);
            tvMessage.setText("Background: Green");
            return true;
        } else if (id == R.id.color_blue) {
            mainLayout.setBackgroundColor(Color.BLUE);
            tvMessage.setText("Background: Blue");
            return true;
        } else if (id == R.id.color_yellow) {
            mainLayout.setBackgroundColor(Color.YELLOW);
            tvMessage.setText("Background: Yellow");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
