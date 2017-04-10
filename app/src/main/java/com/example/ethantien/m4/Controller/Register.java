package com.example.ethantien.m4.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Allows the User to create a new account
 * Has textfields to enter new credentials, a spinner to determine the User type, and 2 buttons,
 * register and cancel
 */
public class Register extends AppCompatActivity {

    private EditText name;
    private EditText userID;
    private EditText pass;

    private Spinner choseUserType;

    private String temp = "";
    private Person tempPerson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Button Cancel = (Button) findViewById(R.id.cancelButton);
        Button Register = (Button) findViewById(R.id.regButton);


        /**
         * Labels all of the elements on the screen
         */
        name = (EditText) findViewById(R.id.editText);
        userID = (EditText) findViewById(R.id.userID);
        pass = (EditText) findViewById(R.id.passField);
        choseUserType = (Spinner) findViewById(R.id.accType);
        /**
         * add types to the spinner
         */
        ArrayList<String> types = new ArrayList<>();
        types.add("User");
        types.add("Worker");
        types.add("Manager");
        types.add("Admin");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, types);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        choseUserType.setAdapter(adapter);

        /**
         * Button handler for the register button
         * @param view the button
         */
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = userID.getText().toString();
                String password = pass.getText().toString();
                String fullName = name.getText().toString();

                try {
                    if (Person.validInput(username, password, fullName)) {
                        /**
                         * Creates a new Subclass of 'Person' based on what they selected.
                         */
                        String choice = choseUserType.getSelectedItem().toString();

                        addNewUser(username, password, fullName, choice);
                    }
                } catch (IllegalArgumentException e) {
                    Toast.makeText(Register.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }



            }
        });




        /**
         * Button handler for the cancel button
         * @param view the button
         */
        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Register.this, MainActivity.class));
                finish();
            }
        });


    }

    /**
     * creates a new user based on the choice and pushes to database
     *
     * @param username the user's userID
     * @param pass the user's password
     * @param name the user's full name
     * @param choice the user's account type
     */
    private void addNewUser(String username, String pass, String name, String choice) {
        switch (choice) {
            case "User":
                tempPerson = new User(name, username, pass);
                temp = "Users";
                break;
            case "Worker":
                tempPerson = new Worker(name, username, pass);
                temp = "Workers";
                break;
            case "Manager":
                tempPerson = new Manager(name, username, pass);
                temp = "Mangers";
                break;
            case "Admin":
                tempPerson = new Admin(name, username, pass);
                temp = "Admins";
                break;
        }
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("Users").child(tempPerson.getID()).getValue() != null) {
                    Toast.makeText(Register.this, "That user already exists!", Toast.LENGTH_LONG).show();
                } else if (dataSnapshot.child("Workers").child(tempPerson.getID()).getValue() != null){
                    Toast.makeText(Register.this, "That user already exists!", Toast.LENGTH_LONG).show();
                } else if (dataSnapshot.child("Managers").child(tempPerson.getID()).getValue() != null){
                    Toast.makeText(Register.this, "That user already exists!", Toast.LENGTH_LONG).show();
                } else if (dataSnapshot.child("Admins").child(tempPerson.getID()).getValue() != null){
                    Toast.makeText(Register.this, "That user already exists!", Toast.LENGTH_LONG).show();
                } else {
                    Map<String, Object> childUpdates = new HashMap<>();

                    childUpdates.put(tempPerson.getID(), tempPerson);
                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference(temp);
                    mDatabase.updateChildren(childUpdates);

                    Toast.makeText(Register.this, "New user created.", Toast.LENGTH_LONG).show();
                    vars.getInstance().setCurrPerson(tempPerson);
                    startActivity(new Intent(Register.this, startApplication.class));
                    finish();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(Register.this, "Database error", Toast.LENGTH_LONG).show();
            }
        });


    }
}