package com.example.ethantien.m4.Model;

/**
 * Created by ethantien on 2/21/17.
 *
 * represents an Worker account type
 */
public class Worker extends User {

    public Worker() {

    }

    /**
     * Constructer chainer to create a new subclass of the person Class
     * @param nameStr name
     * @param IDStr id
     * @param passwordStr password
     */
    public Worker(String nameStr, String IDStr, String passwordStr) {
        super(nameStr, IDStr, passwordStr);
    }
}
