package com.hpdev;

import com.pi4j.io.gpio.*;
/**
 *
 * @author harry
 */
public class GPIOPin {

    private int pinType;

    private Boolean isPinUsed;
    private int pinNumber;
    private String pinIdentifier;
    private GpioPinAnalogInput analogInput;
    private GpioPinAnalogOutput analogOutput;
    private GpioPinDigitalInput digitalInput;
    private GpioPinDigitalOutput digitalOutput;
    public final int LOW=0;
    public final int HIGH=1;



    public GPIOPin(XMLPin pin){

        this.pinType=pin.getPinType();
        this.isPinUsed=true;
        this.pinIdentifier=pin.getPinIdentifier();
        this.pinNumber=pin.getPinNumber();


    }

    public GPIOPin(Boolean isPinUsed, int pinNumber) {
        this.isPinUsed = isPinUsed;
        this.pinNumber = pinNumber;
    }

    public GPIOPin(int pinNumber, GpioPinDigitalOutput digitalOutput) {
        this.pinNumber = pinNumber;
        this.digitalOutput = digitalOutput;
        this.isPinUsed=true;
        pinType=Constants.DIGITAL_OUTPUT;
    }


    public int getPinType() {
        return pinType;
    }

    public GpioPinAnalogInput getAnalogInput() {
        return analogInput;
    }

    public void setAnalogInput(GpioPinAnalogInput analogInput) {
        this.analogInput = analogInput;
        pinType=Constants.ANALOG_INPUT;
    }

    public GpioPinAnalogOutput getAnalogOutput() {
        return analogOutput;
    }

    public void setAnalogOutput(GpioPinAnalogOutput analogOutput) {
        this.analogOutput = analogOutput;
        pinType=Constants.ANALOG_OUTPUT;
    }

    public GpioPinDigitalInput getDigitalInput() {
        return digitalInput;
    }

    public void setDigitalInput(GpioPinDigitalInput digitalInput) {
        this.digitalInput = digitalInput;
        pinType=Constants.DIGITAL_INPUT;
    }

    public GpioPinDigitalOutput getDigitalOutput() {
        return digitalOutput;
    }

    public void setDigitalOutput(GpioPinDigitalOutput digitalOutput) {
        this.digitalOutput = digitalOutput;
        pinType=Constants.DIGITAL_OUTPUT;
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
