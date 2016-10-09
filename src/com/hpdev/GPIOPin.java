package com.hpdev;

import com.pi4j.io.gpio.*;
/**
 *
 * @author harry
 */
public class GPIOPin {

    public final static int ANALOG_INPUT=0;
    public final static int ANALOG_OUTPUT=1;
    public final static int DIGITAL_INPUT=2;
    public final static int DIGITAL_OUTPUT=3;

    private int pinType;

    private Boolean isPinUsed;
    private int pinNumber;
    private String pinIdentifier;
    private GpioPinAnalogInput analogInput;
    private GpioPinAnalogOutput analogOutput;
    private GpioPinDigitalInput digitalInput;
    private GpioPinDigitalOutput digitalOutput;

    public GPIOPin(Boolean isPinUsed, int pinNumber) {
        this.isPinUsed = isPinUsed;
        this.pinNumber = pinNumber;
    }

    public GPIOPin(int pinNumber, GpioPinDigitalOutput digitalOutput) {
        this.pinNumber = pinNumber;
        this.digitalOutput = digitalOutput;
        this.isPinUsed=true;
        pinType=DIGITAL_OUTPUT;
    }


    public int getPinType() {
        return pinType;
    }

    public GpioPinAnalogInput getAnalogInput() {
        return analogInput;
    }

    public void setAnalogInput(GpioPinAnalogInput analogInput) {
        this.analogInput = analogInput;
        pinType=ANALOG_INPUT;
    }

    public GpioPinAnalogOutput getAnalogOutput() {
        return analogOutput;
    }

    public void setAnalogOutput(GpioPinAnalogOutput analogOutput) {
        this.analogOutput = analogOutput;
        pinType=ANALOG_OUTPUT;
    }

    public GpioPinDigitalInput getDigitalInput() {
        return digitalInput;
    }

    public void setDigitalInput(GpioPinDigitalInput digitalInput) {
        this.digitalInput = digitalInput;
        pinType=DIGITAL_INPUT;
    }

    public GpioPinDigitalOutput getDigitalOutput() {
        return digitalOutput;
    }

    public void setDigitalOutput(GpioPinDigitalOutput digitalOutput) {
        this.digitalOutput = digitalOutput;
        pinType=DIGITAL_OUTPUT;
    }

    

    public Boolean isPinUsed() {
        return isPinUsed;
    }

    public void setPinUsed(Boolean isPinUsed) {
        this.isPinUsed = isPinUsed;
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
    
    
    
}
