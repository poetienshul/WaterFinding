package com.example.ethantien.m4.Model;



/**
 * Created by ethantien on 2/21/17.
 *
 * represents an Administrator account type
 */
public class Admin extends Person {

    /**
     * Constructer chainer to create a new subclass of the person Class
     * @param nameStr name
     * @param IDStr id
     * @param passwordStr password
     */
    public Admin(String nameStr, String IDStr, String passwordStr) {
        super(nameStr, IDStr, passwordStr);
    }
}
