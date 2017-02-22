package com.example.ethantien.m4.Model;

/**
 * Created by ethantien on 2/21/17.
 *
 * represents an User account type
 */
public class User extends Person {

    /**
     * Constructer chainer to create a new subclass of the person Class
     * @param nameStr name
     * @param IDStr id
     * @param passwordStr password
     */
    public User(String nameStr, String IDStr, String passwordStr) {
        super(nameStr, IDStr, passwordStr);
    }
}
