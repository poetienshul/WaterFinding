package com.example.ethantien.m4.Model;

/**
 * Created by ethantien on 2/21/17.
 *
 * represents an Manager account type
 */
public class Manager extends Worker {

    public Manager() {

    }

    /**
     * Constructer chainer to create a new subclass of the person Class
     * @param nameStr name
     * @param IDStr id
     * @param passwordStr password
     */
    public Manager(String nameStr, String IDStr, String passwordStr) {
        super(nameStr, IDStr, passwordStr);
    }
}
