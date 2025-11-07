package com.example.generatedynamiclist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    EditText etNumber;
    Button btnGenerate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNumber = findViewById(R.id.etNumber);
        btnGenerate = findViewById(R.id.btnGenerate);

        btnGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String numStr = etNumber.getText().toString().trim();
                if (!numStr.isEmpty()) {
                    int num = Integer.parseInt(numStr);

                    Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                    intent.putExtra("count", num);
                    startActivity(intent);
                }
            }
        });
    }
}