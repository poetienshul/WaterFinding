package com.example.ethantien.m4.model;

/**
 * Created by ethantien on 2/21/17.
 *
 * A Generic Person account type.
 */

public abstract class Person {
    private String name;
    private String id;
    private String password;
    private String email;
    private String address;

    Person() {
        //dummy default constructors
//        name = "..";
//        ID = "..";
//        password = "..";
//        email = "..";
//        address = "..";
    }

    /**
     * Constructor for a generic Person
     * @param nameStr name
     * @param IDStr ID
     * @param passwordStr password
     */
    Person(String nameStr, String IDStr, String passwordStr) {
        name = nameStr;
        id = IDStr;
        password = passwordStr;
    }

    /**
     * getters and setters for all the attributes (besides those that should not be changed)
     */

    public void setName(String str) {
        name = str;
    }

    public String getName() {
        return name;
    }

    public void setAddress(String str) {
        address = str;
    }

    public String getAddress() {
        return address;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String str) {
        email = str;
    }

    public String getEmail() {
        return email;
    }

    public String getID() {
        return id;
    }
}
