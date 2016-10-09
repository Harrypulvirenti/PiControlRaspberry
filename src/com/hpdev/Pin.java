package com.hpdev;

/**
 * Created by harry on 09/10/2016.
 */
public class Pin {

    public final static int ANALOG_INPUT=0;
    public final static int ANALOG_OUTPUT=1;
    public final static int DIGITAL_INPUT=2;
    public final static int DIGITAL_OUTPUT=3;

    private int pinNumber;
    private String pinIdentifier;
    private int pinType;

    public Pin(int pinNumber, String pinIdentifier, int pinType) {
        this.pinNumber = pinNumber;
        this.pinIdentifier = pinIdentifier;
        this.pinType = pinType;
    }

    public int getPinNumber() {
        return pinNumber;
    }

    public void setPinNumber(int pinNumber) {
        this.pinNumber = pinNumber;
    }

    public String getPinIdentifier() {
        return pinIdentifier;
    }

    public void setPinIdentifier(String pinIdentifier) {
        this.pinIdentifier = pinIdentifier;
    }

    public int getPinType() {
        return pinType;
    }

    public void setPinType(int pinType) {
        this.pinType = pinType;
    }
}
