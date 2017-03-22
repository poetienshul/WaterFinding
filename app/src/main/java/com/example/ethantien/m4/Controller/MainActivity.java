package com.example.ethantien.m4.Controller;

import android.content.Intent;
import android.content.SyncStatusObserver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.ethantien.m4.Model.User;
import com.google.firebase.database.DatabaseReference;

import com.example.ethantien.m4.R;
import com.google.firebase.database.FirebaseDatabase;

/**
 * First screen user sees, gives option to create new account or login as existing user
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Button enterValues = (Button) findViewById(R.id.logButton);
        Button register = (Button) findViewById(R.id.regButton);

        /**
         * Button handler for the login button
         * @param view the button
         */
        enterValues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, logScreen.class));
                finish();
            }
        });

        /**
         * Button handler for the register button
         * @param view the button
         */
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Register.class));
                finish();
            }
        });
    }
}
