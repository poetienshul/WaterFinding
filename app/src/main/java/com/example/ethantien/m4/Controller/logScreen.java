package com.example.ethantien.m4.Controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ethantien.m4.Model.Admin;
import com.example.ethantien.m4.Model.Manager;
import com.example.ethantien.m4.Model.Person;
import com.example.ethantien.m4.Model.User;
import com.example.ethantien.m4.Model.Worker;
import com.example.ethantien.m4.Model.vars;
import com.example.ethantien.m4.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * Login Screen that the user can enter their credentials in to access their own account.
 * Has 2 textfields, username and password, and 2 buttons, Login and Cancel.
 */
public class logScreen extends AppCompatActivity {
    private EditText username;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_screen);
        login();
        Button cancel = (Button) findViewById(R.id.cancel);

        /**
         * Button handler for the cancel button
         * @param view the button
         */
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(logScreen.this, MainActivity.class));
                finish();
            }
        });
    }

    /**
     * takes the values from the textboxes and checks to see if a user exists with the given
     * username and password, and then enters the application with said credentials
     */

    private void login() {
        username = (EditText) findViewById(R.id.user);
        password = (EditText) findViewById(R.id.pass);

        Button enterKeys = (Button) findViewById(R.id.loginButton);

        /**
         * Button handler for the login button
         * @param view the button
         */
        enterKeys.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String userName = username.getText().toString();
                        String passw = password.getText().toString();
                        if (userName.equals("") || passw.equals("")) {
                            Toast.makeText(logScreen.this, "Please fill in entire form", Toast.LENGTH_LONG).show();
                        } else {
                            String str = "";
                            if (dataSnapshot.child("Users").child(userName).getValue() != null) {
                                str = "Users";
                            } else if (dataSnapshot.child("Workers").child(userName).getValue() != null) {
                                str = "Workers";
                            } else if (dataSnapshot.child("Managers").child(userName).getValue() != null) {
                                str = "Managers";
                            } else if (dataSnapshot.child("Admins").child(userName).getValue() != null) {
                                str = "Admins";
                            }
                            if (str.equals("")) {
                                Toast.makeText(logScreen.this, "Invalid Username or Password", Toast.LENGTH_LONG).show();
                            } else {
                                if (dataSnapshot.child(str).child(userName).child("password").getValue().toString().equals(passw)) {
                                    Toast.makeText(logScreen.this, "Login Successful", Toast.LENGTH_LONG).show();
                                    switch (str) {
                                        case "Users":
                                            vars.getInstance().setCurrPerson(dataSnapshot.child("Users").child(userName).getValue(User.class));
                                            break;
                                        case "Workers":
                                            vars.getInstance().setCurrPerson(dataSnapshot.child("Workers").child(userName).getValue(Worker.class));
                                            break;
                                        case "Managers":
                                            vars.getInstance().setCurrPerson(dataSnapshot.child("Managers").child(userName).getValue(Manager.class));
                                            break;
                                        case "Admins":
                                            vars.getInstance().setCurrPerson(dataSnapshot.child("Admins").child(userName).getValue(Admin.class));
                                            break;
                                    }
                                    startActivity(new Intent(logScreen.this, startApplication.class));
                                } else {
                                    Toast.makeText(logScreen.this, "Invalid Username or Password", Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(logScreen.this, "Database Error", Toast.LENGTH_LONG).show();
                    }
                });


            }
        });
    }
}
