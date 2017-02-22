package com.example.ethantien.m4.Model;

import android.app.Application;
import java.util.HashMap;

/**
 * Created by ethantien on 2/20/17.
 *
 * A class that holds information that needs to be accessed from any page in the application
 * Contains: HashMap for <Username, Person> and Person that holds the current logged in user.
 */

public class vars {
    private static final vars _instance = new vars();
    public static vars getInstance() { return _instance; }

    private HashMap<String, Person> userPass;
    private Person currPerson;

    private vars() {
        userPass  = new HashMap<>();
    }

    public void addPerson(String str, Person p) {
        userPass.put(str, p);
    }

    public HashMap getMap() {
        return userPass;
    }

    public void setCurrPerson(Person p) {
        currPerson = p;
    }

    public Person getCurrPerson() {
        return currPerson;
    }

}
