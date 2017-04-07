package com.example.ethantien.m4.model;

/**
 * Created by ethantien on 2/21/17.
 *
 * represents a Manager account type
 */
public class Manager extends Worker {

    public Manager() {

    }

    /**
     * Constructor chaining to create a new subclass of the person Class
     * @param nameStr name
     * @param IDStr id
     * @param passwordStr password
     */
    public Manager(String nameStr, String IDStr, String passwordStr) {
        super(nameStr, IDStr, passwordStr);
    }
}
