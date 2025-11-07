package com.example.authapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.*;

public class MainActivity extends AppCompatActivity {

    EditText etUserId, etPassword;
    Button btnLogin, btnRegister;
    TextView tvResult;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUserId = findViewById(R.id.etUserId);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        tvResult = findViewById(R.id.tvResult);

        dbHelper = new DBHelper(this);

        // LOGIN
        btnLogin.setOnClickListener(v -> {
            String userid = etUserId.getText().toString();
            String password = etPassword.getText().toString();

            if (dbHelper.checkUser(userid, password)) {
                tvResult.setText("✅ Login Successful! Welcome " + userid);
            } else {
                tvResult.setText("❌ Invalid Credentials");
            }
        });

        // REGISTER
        btnRegister.setOnClickListener(v -> {
            String userid = etUserId.getText().toString();
            String password = etPassword.getText().toString();

            boolean inserted = dbHelper.insertUser(userid, password);
            if (inserted) {
                tvResult.setText("✅ User Registered Successfully!");
            } else {
                tvResult.setText("❌ Registration Failed (UserID already exists)");
            }
        });
    }
}