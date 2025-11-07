package com.example.userregistrationapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import java.io.*;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements UserAdapter.UserActionListener {

    EditText editTextName, editTextEmail, editTextPassword;
    Button btnRegister;
    RecyclerView recyclerViewUsers;

    String fileName = "user_data.txt";
    ArrayList<User> usersList;
    UserAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        btnRegister = findViewById(R.id.btnRegister);
        recyclerViewUsers = findViewById(R.id.recyclerViewUsers);

        usersList = new ArrayList<>();
        adapter = new UserAdapter(this, usersList, this);
        recyclerViewUsers.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewUsers.setAdapter(adapter);

        loadUsersFromFile();

        btnRegister.setOnClickListener(v -> saveUserData());
    }

    private void saveUserData() {
        String name = editTextName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        User newUser = new User(name, email, password);
        usersList.add(newUser);
        adapter.notifyDataSetChanged();
        saveAllUsersToFile();

        editTextName.setText("");
        editTextEmail.setText("");
        editTextPassword.setText("");

        Toast.makeText(this, "User registered successfully!", Toast.LENGTH_SHORT).show();
    }

    private void saveAllUsersToFile() {
        try {
            FileOutputStream fos = openFileOutput(fileName, MODE_PRIVATE);
            OutputStreamWriter osw = new OutputStreamWriter(fos);

            for (User u : usersList) {
                osw.write(u.toString() + "\n");
            }
            osw.close();
        } catch (Exception e) {
            Toast.makeText(this, "Error saving file", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadUsersFromFile() {
        try {
            FileInputStream fis = openFileInput(fileName);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            String line;
            while ((line = br.readLine()) != null) {
                User user = User.fromString(line);
                if (user != null) usersList.add(user);
            }
            br.close();
            adapter.notifyDataSetChanged();
        } catch (FileNotFoundException e) {
            // First time, ignore
        } catch (IOException e) {
            Toast.makeText(this, "Error reading file", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDelete(User user) {
        usersList.remove(user);
        adapter.notifyDataSetChanged();
        saveAllUsersToFile();
        Toast.makeText(this, "User deleted", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEdit(User user, int position) {
        editTextName.setText(user.name);
        editTextEmail.setText(user.email);
        editTextPassword.setText(user.password);

        // Remove old entry (it will be added again after update)
        usersList.remove(position);
        adapter.notifyDataSetChanged();
        saveAllUsersToFile();
    }
}