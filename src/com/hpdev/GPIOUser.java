package com.hpdev;

import java.util.ArrayList;

/**
 * Created by harry on 09/10/2016.
 */
public class GPIOUser {
    public final static int TYPE_ACTUATOR=0;
    public final static int TYPE_SENSOR_DH11=1;

    private ArrayList<Pin> myPin;
    private String GPIOUserName;
    private int GPIOType;
    private int pinNumber;

    public GPIOUser(String GPIOUserName, int GPIOType, int pinNumber) {
        this.GPIOUserName = GPIOUserName;
        this.GPIOType = GPIOType;
        this.pinNumber = pinNumber;
    }

    public void addUserPin(Pin pin){
        myPin.add(pin);
    }

    public ArrayList<Pin> getMyPin() {
        return myPin;
    }

    public String getGPIOUserName() {
        return GPIOUserName;
    }

    public int getGPIOType() {
        return GPIOType;
    }

    public int getPinNumber() {
        return pinNumber;
    }
}
