package com.example.ethantien.m4.Controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ethantien.m4.Model.Person;
import com.example.ethantien.m4.Model.vars;
import com.example.ethantien.m4.R;


/**
 * Login Screen that the user can enter their credentials in to access their own account.
 * Has 2 textfields, username and password, and 2 buttons, Login and Cancel.
 */
public class logScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_screen);


        final EditText username = (EditText) findViewById(R.id.user);
        final EditText password = (EditText) findViewById(R.id.pass);

        Button enterKeys = (Button) findViewById(R.id.loginButton);

        /**
         * Button handler for the login button
         * @param view the button
         */
        enterKeys.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = username.getText().toString();
                String passw = password.getText().toString();

                if (vars.getInstance().getMap().containsKey(userName)) {
                    vars.getInstance().setCurrPerson((Person)vars.getInstance().getMap().get(userName));
                    if (passw.equals(vars.getInstance().getCurrPerson().getPassword())) {
                        startActivity(new Intent(logScreen.this, startApplication.class));
                    } else {
                        Toast.makeText(logScreen.this, "Invalid Username or Password", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(logScreen.this, "Invalid Username or Password", Toast.LENGTH_LONG).show();
                }

            }
        });

        Button cancel = (Button) findViewById(R.id.cancel);

        /**
         * Button handler for the cancel button
         * @param view the button
         */
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(logScreen.this, MainActivity.class));
            }
        });
    }
}
