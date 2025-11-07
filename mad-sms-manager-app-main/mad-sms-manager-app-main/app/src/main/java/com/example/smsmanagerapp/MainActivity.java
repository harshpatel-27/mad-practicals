package com.example.smsmanagerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listViewMessages;
    EditText editTextNumber, editTextMessage;
    Button btnSend;

    private static final int REQUEST_SMS_PERMISSION = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewMessages = findViewById(R.id.listViewMessages);
        editTextNumber = findViewById(R.id.editTextNumber);
        editTextMessage = findViewById(R.id.editTextMessage);
        btnSend = findViewById(R.id.btnSend);

        // Request SMS permissions
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_SMS, Manifest.permission.SEND_SMS},
                    REQUEST_SMS_PERMISSION);
        } else {
            loadInboxMessages();
        }

        // Send SMS button click
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendSmsMessage();
            }
        });
    }

    // Load Inbox Messages
    private void loadInboxMessages() {
        ArrayList<String> smsList = new ArrayList<>();
        Uri uriSms = Uri.parse("content://sms/inbox");
        ContentResolver cr = getContentResolver();

        Cursor cursor = cr.query(uriSms, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String address = cursor.getString(cursor.getColumnIndexOrThrow("address"));
                String body = cursor.getString(cursor.getColumnIndexOrThrow("body"));
                smsList.add("From: " + address + "\nMessage: " + body);
            } while (cursor.moveToNext());
            cursor.close();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, smsList);
        listViewMessages.setAdapter(adapter);
    }

    // Send SMS Message
    private void sendSmsMessage() {
        String phone = editTextNumber.getText().toString();
        String message = editTextMessage.getText().toString();

        if (phone.isEmpty() || message.isEmpty()) {
            Toast.makeText(this, "Please enter number and message", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phone, null, message, null, null);
            Toast.makeText(this, "SMS Sent!", Toast.LENGTH_SHORT).show();
            editTextMessage.setText("");
        } catch (Exception e) {
            Toast.makeText(this, "Failed to send SMS: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    // Handle permission result
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_SMS_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                loadInboxMessages();
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}