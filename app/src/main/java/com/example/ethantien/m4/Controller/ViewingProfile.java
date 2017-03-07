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
 * Allows the user to view and edit their own information, with 3 textfields, and 2 buttons.
 */
public class ViewingProfile extends AppCompatActivity {

    private EditText nameTEXT;
    private EditText emailTEXT;
    private EditText addressTEXT;
    Person cur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewing_profile);
        cur = vars.getInstance().getCurrPerson();

        /**
         * Label all the things on the current page layout
         */
        nameTEXT = (EditText) findViewById(R.id.fullName);
        emailTEXT = (EditText) findViewById(R.id.email);
        addressTEXT = (EditText) findViewById(R.id.address);


        nameTEXT.setText(cur.getName());
        emailTEXT.setText(cur.getEmail());
        addressTEXT.setText(cur.getAddress());

        Button cancel = (Button) findViewById(R.id.cancelButton);
        Button saving = (Button) findViewById(R.id.saveButton);

        /**
         * Button handler for the view cancel button
         * @param view the button
         */
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ViewingProfile.this, startApplication.class));
                finish();
            }
        });


        /**
         * Button handler for the view saving button
         * @param view the button
         */
        saving.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameTEXT.getText().toString();
                String email = emailTEXT.getText().toString();
                String address = addressTEXT.getText().toString();
                if (name.equals("") || email.equals("") || address.equals("")) {
                    Toast.makeText(ViewingProfile.this, "Please fill out all blanks.", Toast.LENGTH_LONG).show();
                } else {
                    cur.setName(name);
                    cur.setEmail(email);
                    cur.setAddress(address);
                    Toast.makeText(ViewingProfile.this, "Information saved successfully.", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(ViewingProfile.this, startApplication.class));
                    finish();
                }

            }
        });


    }
}
