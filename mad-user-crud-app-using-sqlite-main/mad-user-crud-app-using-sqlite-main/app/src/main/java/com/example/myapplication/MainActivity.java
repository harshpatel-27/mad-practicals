package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.*;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText etId, etName, etAddress, etContact;
    Button btnAdd, btnUpdate, btnDelete, btnList;
    ListView listViewUsers;
    DBHelper dbHelper;
    ArrayAdapter<String> adapter;
    ArrayList<String> userList;
    ArrayList<Integer> userIdList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etId = findViewById(R.id.etId);
        etName = findViewById(R.id.etName);
        etAddress = findViewById(R.id.etAddress);
        etContact = findViewById(R.id.etContact);
        btnAdd = findViewById(R.id.btnAdd);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        btnList = findViewById(R.id.btnList);
        listViewUsers = findViewById(R.id.listViewUsers);

        dbHelper = new DBHelper(this);
        userList = new ArrayList<>();
        userIdList = new ArrayList<>();

        // ADD
        btnAdd.setOnClickListener(v -> {
            boolean inserted = dbHelper.insertUser(
                    etName.getText().toString(),
                    etAddress.getText().toString(),
                    etContact.getText().toString()
            );
            if (inserted) {
                Toast.makeText(this, "User Added", Toast.LENGTH_SHORT).show();
                clearFields();
                loadUsers();
            } else {
                Toast.makeText(this, "Error Adding User", Toast.LENGTH_SHORT).show();
            }
        });

        // UPDATE
        btnUpdate.setOnClickListener(v -> {
            boolean updated = dbHelper.updateUser(
                    etId.getText().toString(),
                    etName.getText().toString(),
                    etAddress.getText().toString(),
                    etContact.getText().toString()
            );
            if (updated) {
                Toast.makeText(this, "User Updated", Toast.LENGTH_SHORT).show();
                clearFields();
                loadUsers();
            } else {
                Toast.makeText(this, "Error Updating User", Toast.LENGTH_SHORT).show();
            }
        });

        // DELETE
        btnDelete.setOnClickListener(v -> {
            boolean deleted = dbHelper.deleteUser(etId.getText().toString());
            if (deleted) {
                Toast.makeText(this, "User Deleted", Toast.LENGTH_SHORT).show();
                clearFields();
                loadUsers();
            } else {
                Toast.makeText(this, "Error Deleting User", Toast.LENGTH_SHORT).show();
            }
        });

        // LIST USERS
        btnList.setOnClickListener(v -> loadUsers());

        // CLICK ITEM → SHOW IN EDITTEXT
        listViewUsers.setOnItemClickListener((parent, view, position, id) -> {
            int userId = userIdList.get(position);

            Cursor cursor = dbHelper.getAllUsers();
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    if (cursor.getInt(0) == userId) {
                        etId.setText(String.valueOf(cursor.getInt(0)));
                        etName.setText(cursor.getString(1));
                        etAddress.setText(cursor.getString(2));
                        etContact.setText(cursor.getString(3));
                        break;
                    }
                }
                cursor.close();
            }
        });
    }

    private void loadUsers() {
        userList.clear();
        userIdList.clear();
        Cursor cursor = dbHelper.getAllUsers();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String address = cursor.getString(2);
                String contact = cursor.getString(3);

                userList.add(id + " - " + name + " - " + contact);
                userIdList.add(id);
            }
            cursor.close();
        }
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, userList);
        listViewUsers.setAdapter(adapter);
    }

    private void clearFields() {
        etId.setText("");
        etName.setText("");
        etAddress.setText("");
        etContact.setText("");
    }
}