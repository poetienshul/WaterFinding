package com.example.ethantien.m4.Model;

/**
 * Created by ethantien on 2/21/17.
 *
 * A Generic Person account type.
 */

public abstract class Person {
    private String name;
    private String ID;
    private String password;
    private String email;
    private String address;

    public Person() {
        //dummy default constructors
        name = "..";
        ID = "..";
        password = "..";
        email = "..";
        address = "..";
    }

    /**
     * Constructor for a generic Person
     * @param nameStr name
     * @param IDStr id
     * @param passwordStr password
     */
    public Person(String nameStr, String IDStr, String passwordStr) {
        name = nameStr;
        ID = IDStr;
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
}
