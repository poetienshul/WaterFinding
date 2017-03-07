package com.example.ethantien.m4.Controller;

import android.content.Intent;
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

import java.util.ArrayList;

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
                            vars.getInstance().addPerson(username, new User(fullName, username, password));
                            break;
                        case "Worker":
                            vars.getInstance().addPerson(username, new Worker(fullName, username, password));
                            break;
                        case "Manager":
                            vars.getInstance().addPerson(username, new Manager(fullName, username, password));
                            break;
                        case "Admin":
                            vars.getInstance().addPerson(username, new Admin(fullName, username, password));
                            break;
                    }
                    Toast.makeText(Register.this, "New user created.", Toast.LENGTH_LONG).show();
                    vars.getInstance().setCurrPerson((Person)vars.getInstance().getMap().get(username));
                    startActivity(new Intent(Register.this, startApplication.class));
                    finish();

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
