package com.example.ethantien.m4.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ethantien.m4.model.Admin;
import com.example.ethantien.m4.model.Manager;
import com.example.ethantien.m4.model.Person;
import com.example.ethantien.m4.model.User;
import com.example.ethantien.m4.model.Worker;
import com.example.ethantien.m4.model.vars;
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
        Button cancel = (Button) findViewById(R.id.cancel);
        Button enterKeys = (Button) findViewById(R.id.loginButton);

        username = (EditText) findViewById(R.id.user);
        password = (EditText) findViewById(R.id.pass);

        /**
         * Button handler for the login button
         * @param view the button
         */
        enterKeys.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login(username.getText().toString(), password.getText().toString());
            }
        });


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
     * @param user username entered
     * @param pass password entered
     */
    private void login(final String user, final String pass) {

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange (DataSnapshot dataSnapshot) {
                if (user.equals("") || pass.equals("")) {
                    Toast.makeText(logScreen.this, "Please enter values.", Toast.LENGTH_LONG).show();
                } else {
                    String str = "";
                    Person temp = new User();
                    if (dataSnapshot.child("Users").child(user).getValue() != null) {
                        str = "Users";
                        temp = dataSnapshot.child(str).child(user).getValue(User.class);
                    } else if (dataSnapshot.child("Workers").child(user).getValue() != null) {
                        str = "Workers";
                        temp = dataSnapshot.child(str).child(user).getValue(Worker.class);
                    } else if (dataSnapshot.child("Managers").child(user).getValue() != null) {
                        str = "Managers";
                        temp = dataSnapshot.child(str).child(user).getValue(Manager.class);
                    } else if (dataSnapshot.child("Admins").child(user).getValue() != null) {
                        str = "Admins";
                        temp = dataSnapshot.child(str).child(user).getValue(Admin.class);
                    }
                    if (str.equals("")) {
                        Toast.makeText(logScreen.this, "Invalid username or password.", Toast.LENGTH_LONG).show();
                    } else {
                        if (temp.getPassword().equals(pass)) {
                            switch (str) {
                                case "Users":
                                    vars.getInstance().setCurrPerson(dataSnapshot.child("Users").child(user).getValue(User.class));
                                    break;
                                case "Workers":
                                    vars.getInstance().setCurrPerson(dataSnapshot.child("Workers").child(user).getValue(Worker.class));
                                    break;
                                case "Managers":
                                    vars.getInstance().setCurrPerson(dataSnapshot.child("Managers").child(user).getValue(Manager.class));
                                    break;
                                case "Admins":
                                    vars.getInstance().setCurrPerson(dataSnapshot.child("Admins").child(user).getValue(Admin.class));
                                    break;
                            }

                            Toast.makeText(logScreen.this, "Login Successful", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(logScreen.this, startApplication.class));
                            finish();
                        } else {
                            Toast.makeText(logScreen.this, "Invalid username or password", Toast.LENGTH_LONG).show();
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
}
