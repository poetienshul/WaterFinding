package com.example.ethantien.m4.Controller;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
    private Button Register;
    private Button Cancel;
    private Spinner choseUserType;

    private String temp = "";
    private Person tempPerson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        /**
         * Labels all of the elements on the screen
         */
        name = (EditText) findViewById(R.id.editText);
        userID = (EditText) findViewById(R.id.userID);
        pass = (EditText) findViewById(R.id.passField);
        Register = (Button) findViewById(R.id.regButton);
        Cancel = (Button) findViewById(R.id.cancelButton);
        choseUserType = (Spinner) findViewById(R.id.accType);

        /**
         * add types to the spinner
         */
        ArrayList<String> types = new ArrayList<>();
        types.add("User");
        types.add("Worker");
        types.add("Manager");
        types.add("Admin");
        ArrayAdapter<String> adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, types);
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

                if (username.equals("") || password.equals("") || fullName.equals("")) {
                    Toast.makeText(Register.this, "Please fill out all blanks.", Toast.LENGTH_LONG).show();
                } else {
                    /**
                     * Creates a new Subclass of 'Person' based on what they selected.
                     */
                    switch (choseUserType.getSelectedItem().toString()) {
                        case "User":
                            //vars.getInstance().addPerson(username, new User(fullName, username, password));
                            tempPerson = new User(fullName, username, password);
                            temp = "Users";
                            break;
                        case "Worker":
                            //vars.getInstance().addPerson(username, new Worker(fullName, username, password));
                            tempPerson = new Worker(fullName, username, password);
                            temp = "Workers";
                            break;
                        case "Manager":
                            //vars.getInstance().addPerson(username, new Manager(fullName, username, password));
                            tempPerson = new Manager(fullName, username, password);
                            temp = "Mangers";
                            break;
                        case "Admin":
                            //vars.getInstance().addPerson(username, new Admin(fullName, username, password));
                            tempPerson = new Admin(fullName, username, password);
                            temp = "Admins";
                            break;
                    }
                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference(temp);
                    //System.out.println(tempPerson instanceof Admin);
                    mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.child(tempPerson.getID()).getValue() != null) {
                                Toast.makeText(Register.this, "That user already exists!", Toast.LENGTH_LONG).show();
                            } else {
                                Map<String, Object> childUpdates = new HashMap<>();

                                childUpdates.put("" + tempPerson.getID(), tempPerson);
                                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference(temp);
                                mDatabase.updateChildren(childUpdates);

                                Toast.makeText(Register.this, "New user created.", Toast.LENGTH_LONG).show();
                                vars.getInstance().setCurrPerson(tempPerson);
                                System.out.println(tempPerson instanceof Admin);
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
}
