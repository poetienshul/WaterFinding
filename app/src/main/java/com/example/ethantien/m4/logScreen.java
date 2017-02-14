package com.example.ethantien.m4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class logScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_screen);

        final String login = "admin";
        final String pass = "1234";

        final EditText username = (EditText) findViewById(R.id.user);
        final EditText password = (EditText) findViewById(R.id.pass);

        Button enterKeys = (Button) findViewById(R.id.loginButton);
        enterKeys.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = username.getText().toString();
                String passw = password.getText().toString();
                if (userName.equals(login) && passw.equals(pass)) {
                    startActivity(new Intent(logScreen.this, logout.class));
                } else {
                    Toast.makeText(logScreen.this, "Invalid Username or Password", Toast.LENGTH_LONG).show();
                }

            }
        });

        Button cancel = (Button) findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // if ()
                startActivity(new Intent(logScreen.this, MainActivity.class));
            }
        });
    }
}
