package com.example.ethantien.m4.Controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.ethantien.m4.Model.vars;
import com.example.ethantien.m4.R;

/**
 * Allows the user to explore their own account, with 2 buttons to View his/her own profile, or
 * to log out of the current account
 */
public class startApplication extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_app);

        Button logout = (Button) findViewById(R.id.logout);
        Button viewProfile = (Button) findViewById(R.id.Profile);

        /**
         * Button handler for the logout button
         * @param view the button
         */
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vars.getInstance().setCurrPerson(null);
                startActivity(new Intent(startApplication.this, MainActivity.class));
            }
        });



        /**
         * Button handler for the view Profile button
         * @param view the button
         */
        viewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(startApplication.this, ViewingProfile.class));
            }
        });
    }
}
