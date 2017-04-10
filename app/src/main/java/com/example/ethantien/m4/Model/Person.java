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
     * returns true whether or not the input is valid
     * valid = no empty parameters, length of id and password is longer than 5,
     *          password contains at least one uppercase, lowercase, and digit
     * @param id the id of the username
     * @param password the password of the user
     * @param name the user's full name
     * @return true whether or not the credentials are valid
     */
    public static boolean validInput(String id, String password, String name) {
        if (id.trim().equals("") || password.trim().equals("") || name.trim().equals("")) {
            throw new IllegalArgumentException("Please enter all fields");
        }
        if (id.length() < 6) {
            throw new IllegalArgumentException("ID cannot be less than six characters");
        }
        if (password.length() < 6) {
            throw new IllegalArgumentException("Password cannot be less than six characters");
        }
        boolean hasUpper = false;
        boolean hasLower = false;
        boolean hasDigit = false;
        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            if (Character.isDigit(c)) {
                hasDigit = true;
            } else if (Character.isLowerCase(c)) {
                hasLower = true;
            } else if (Character.isUpperCase(c)) {
                hasUpper = true;
            }
        }

        if (!hasUpper) {
            throw new IllegalArgumentException("Password must contain an upper case letter");
        }
        if (!hasLower) {
            throw new IllegalArgumentException("Password must contain a lower case letter");
        }
        if (!hasDigit) {
            throw new IllegalArgumentException("Password must contain a digit");
        }
        return true;
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
