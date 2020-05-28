package com.example.pastpaper2019a;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pastpaper2019a.database.DBHandler;

public class MainActivity extends AppCompatActivity {

    EditText editTextUsername, editTextPassword;
    Button buttonLogin, buttonRegister;
    DBHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // hooks
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonRegister = findViewById(R.id.buttonRegister);
        db = new DBHandler(this);
        SQLiteDatabase movieDb = db.getReadableDatabase();

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();

                if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {

                    boolean status = db.register(username, password);
                    editTextUsername.setText("");
                    editTextPassword.setText("");
                    if (status)
                        Toast.makeText(MainActivity.this, "user registered successfully", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(MainActivity.this, "error occurred", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(MainActivity.this, "all the fields are required", Toast.LENGTH_SHORT).show();
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();

                if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {

                    boolean status = db.loginUser(username, password);
                    editTextUsername.setText("");
                    editTextPassword.setText("");
                    if (status) {
                        if (username.equals("admin")) {
                            Intent intent = new Intent(getApplicationContext(), AddMovie.class);
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(getApplicationContext(), MovieList.class);
                            startActivity(intent);
                        }
                        Toast.makeText(MainActivity.this, "user logged in successfully", Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(MainActivity.this, "error occurred", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(MainActivity.this, "all the fields are required", Toast.LENGTH_SHORT).show();
            }

        });

    }
}
