package com.example.ethantien.m4.Controller;

import android.content.Intent;
import android.content.SyncStatusObserver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.ethantien.m4.Model.Admin;
import com.example.ethantien.m4.Model.User;
import com.example.ethantien.m4.Model.WaterPurityReport;
import com.example.ethantien.m4.Model.WaterReport;
import com.example.ethantien.m4.Model.Worker;
import com.google.firebase.database.DatabaseReference;

import com.example.ethantien.m4.R;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * First screen user sees, gives option to create new account or login as existing user
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Admins");
//
//        //public Person(String nameStr, String IDStr, String passwordStr) {
//        //WaterPurityReport meow = new WaterPurityReport("3/23/2017", "8:13PM", 0, "Etan", 33.0, -88.4, "Bottled", .3, 4.0);
//        Admin meow = new Admin("Ethan Tien", "ethan", "1234");
//        meow.setAddress("456 Meow Street");
//        meow.setEmail("meow4@gmail.com");
//
//        Map<String, Object> childUpdates = new HashMap<>();
//        childUpdates.put("" + meow.getID(), meow);
//        mDatabase.updateChildren(childUpdates);


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