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
    private GpioController GPIO;
    public final int LOW=0;
    public final int HIGH=1;



    public GPIOPin(XMLPin pin){
        GPIO = GpioFactory.getInstance();

        this.pinType=pin.getPinType();
        this.isPinUsed=true;
        this.pinIdentifier=pin.getPinIdentifier();
        this.pinNumber=pin.getPinNumber();

        switch (pinType){
            case Constants.ANALOG_INPUT:
                initAnalogInput();
                break;
            case Constants.ANALOG_OUTPUT:
                initAnalogOutput();
                break;
            case Constants.DIGITAL_INPUT:
                initDigitalInput();
                break;
            case Constants.DIGITAL_OUTPUT:
                initDigitalOutput();
                break;
        }


    }

    private void initDigitalInput() {
        switch(pinNumber){
            case 0:
                digitalInput=GPIO.provisionDigitalInputPin(RaspiPin.GPIO_00, pinIdentifier);
                break;
            case 1:
                digitalInput=GPIO.provisionDigitalInputPin(RaspiPin.GPIO_01, pinIdentifier);
                break;
            case 2:
                digitalInput=GPIO.provisionDigitalInputPin(RaspiPin.GPIO_02, pinIdentifier);
                break;
            case 3:
                digitalInput=GPIO.provisionDigitalInputPin(RaspiPin.GPIO_03, pinIdentifier);
                break;
            case 4:
                digitalInput=GPIO.provisionDigitalInputPin(RaspiPin.GPIO_04, pinIdentifier);
                break;
            case 5:
                digitalInput=GPIO.provisionDigitalInputPin(RaspiPin.GPIO_05, pinIdentifier);
                break;
            case 6:
                digitalInput=GPIO.provisionDigitalInputPin(RaspiPin.GPIO_06, pinIdentifier);
                break;
            case 7:
                digitalInput=GPIO.provisionDigitalInputPin(RaspiPin.GPIO_07, pinIdentifier);
                break;
            case 8:
                digitalInput=GPIO.provisionDigitalInputPin(RaspiPin.GPIO_08, pinIdentifier);
                break;
            case 9:
                digitalInput=GPIO.provisionDigitalInputPin(RaspiPin.GPIO_09, pinIdentifier);
                break;
            case 10:
                digitalInput=GPIO.provisionDigitalInputPin(RaspiPin.GPIO_10, pinIdentifier);
                break;
            case 11:
                digitalInput=GPIO.provisionDigitalInputPin(RaspiPin.GPIO_11, pinIdentifier);
                break;
            case 12:
                digitalInput=GPIO.provisionDigitalInputPin(RaspiPin.GPIO_12, pinIdentifier);
                break;
            case 13:
                digitalInput=GPIO.provisionDigitalInputPin(RaspiPin.GPIO_13, pinIdentifier);
                break;
            case 14:
                digitalInput=GPIO.provisionDigitalInputPin(RaspiPin.GPIO_14, pinIdentifier);
                break;
            case 15:
                digitalInput=GPIO.provisionDigitalInputPin(RaspiPin.GPIO_15, pinIdentifier);
                break;
            case 16:
                digitalInput=GPIO.provisionDigitalInputPin(RaspiPin.GPIO_16, pinIdentifier);
                break;
            case 17:
                digitalInput=GPIO.provisionDigitalInputPin(RaspiPin.GPIO_17, pinIdentifier);
                break;
            case 18:
                digitalInput=GPIO.provisionDigitalInputPin(RaspiPin.GPIO_18, pinIdentifier);
                break;
            case 19:
                digitalInput=GPIO.provisionDigitalInputPin(RaspiPin.GPIO_19, pinIdentifier);
                break;
            case 20:
                digitalInput=GPIO.provisionDigitalInputPin(RaspiPin.GPIO_20, pinIdentifier);
                break;
            case 21:
                digitalInput=GPIO.provisionDigitalInputPin(RaspiPin.GPIO_21, pinIdentifier);
                break;
            case 22:
                digitalInput=GPIO.provisionDigitalInputPin(RaspiPin.GPIO_22, pinIdentifier);
                break;
            case 23:
                digitalInput=GPIO.provisionDigitalInputPin(RaspiPin.GPIO_23, pinIdentifier);
                break;
            case 24:
                digitalInput=GPIO.provisionDigitalInputPin(RaspiPin.GPIO_24, pinIdentifier);
                break;
            case 25:
                digitalInput=GPIO.provisionDigitalInputPin(RaspiPin.GPIO_25, pinIdentifier);
                break;
            case 26:
                digitalInput=GPIO.provisionDigitalInputPin(RaspiPin.GPIO_26, pinIdentifier);
                break;
            case 27:
                digitalInput=GPIO.provisionDigitalInputPin(RaspiPin.GPIO_27, pinIdentifier);
                break;
            case 28:
                digitalInput=GPIO.provisionDigitalInputPin(RaspiPin.GPIO_28, pinIdentifier);
                break;
            case 29:
                digitalInput=GPIO.provisionDigitalInputPin(RaspiPin.GPIO_29, pinIdentifier);
                break;
        }
    }

    private void initAnalogOutput() {

    }

    private void initAnalogInput() {

    }

    private void initDigitalOutput() {
        switch(pinNumber){
            case 0:
                digitalOutput=GPIO.provisionDigitalOutputPin(RaspiPin.GPIO_00, pinIdentifier, PinState.LOW);
                break;
            case 1:
                digitalOutput=GPIO.provisionDigitalOutputPin(RaspiPin.GPIO_01, pinIdentifier, PinState.LOW);
                break;
            case 2:
                digitalOutput=GPIO.provisionDigitalOutputPin(RaspiPin.GPIO_02, pinIdentifier, PinState.LOW);
                break;
            case 3:
                digitalOutput=GPIO.provisionDigitalOutputPin(RaspiPin.GPIO_03, pinIdentifier, PinState.LOW);
                break;
            case 4:
                digitalOutput=GPIO.provisionDigitalOutputPin(RaspiPin.GPIO_04, pinIdentifier, PinState.LOW);
                break;
            case 5:
                digitalOutput=GPIO.provisionDigitalOutputPin(RaspiPin.GPIO_05, pinIdentifier, PinState.LOW);
                break;
            case 6:
                digitalOutput=GPIO.provisionDigitalOutputPin(RaspiPin.GPIO_06, pinIdentifier, PinState.LOW);
                break;
            case 7:
                digitalOutput=GPIO.provisionDigitalOutputPin(RaspiPin.GPIO_07, pinIdentifier, PinState.LOW);
                break;
            case 8:
                digitalOutput=GPIO.provisionDigitalOutputPin(RaspiPin.GPIO_08, pinIdentifier, PinState.LOW);
                break;
            case 9:
                digitalOutput=GPIO.provisionDigitalOutputPin(RaspiPin.GPIO_09, pinIdentifier, PinState.LOW);
                break;
            case 10:
                digitalOutput=GPIO.provisionDigitalOutputPin(RaspiPin.GPIO_10, pinIdentifier, PinState.LOW);
                break;
            case 11:
                digitalOutput=GPIO.provisionDigitalOutputPin(RaspiPin.GPIO_11, pinIdentifier, PinState.LOW);
                break;
            case 12:
                digitalOutput=GPIO.provisionDigitalOutputPin(RaspiPin.GPIO_12, pinIdentifier, PinState.LOW);
                break;
            case 13:
                digitalOutput=GPIO.provisionDigitalOutputPin(RaspiPin.GPIO_13, pinIdentifier, PinState.LOW);
                break;
            case 14:
                digitalOutput=GPIO.provisionDigitalOutputPin(RaspiPin.GPIO_14, pinIdentifier, PinState.LOW);
                break;
            case 15:
                digitalOutput=GPIO.provisionDigitalOutputPin(RaspiPin.GPIO_15, pinIdentifier, PinState.LOW);
                break;
            case 16:
                digitalOutput=GPIO.provisionDigitalOutputPin(RaspiPin.GPIO_16, pinIdentifier, PinState.LOW);
                break;
            case 17:
                digitalOutput=GPIO.provisionDigitalOutputPin(RaspiPin.GPIO_17, pinIdentifier, PinState.LOW);
                break;
            case 18:
                digitalOutput=GPIO.provisionDigitalOutputPin(RaspiPin.GPIO_18, pinIdentifier, PinState.LOW);
                break;
            case 19:
                digitalOutput=GPIO.provisionDigitalOutputPin(RaspiPin.GPIO_19, pinIdentifier, PinState.LOW);
                break;
            case 20:
                digitalOutput=GPIO.provisionDigitalOutputPin(RaspiPin.GPIO_20, pinIdentifier, PinState.LOW);
                break;
            case 21:
                digitalOutput=GPIO.provisionDigitalOutputPin(RaspiPin.GPIO_21, pinIdentifier, PinState.LOW);
                break;
            case 22:
                digitalOutput=GPIO.provisionDigitalOutputPin(RaspiPin.GPIO_22, pinIdentifier, PinState.LOW);
                break;
            case 23:
                digitalOutput=GPIO.provisionDigitalOutputPin(RaspiPin.GPIO_23, pinIdentifier, PinState.LOW);
                break;
            case 24:
                digitalOutput=GPIO.provisionDigitalOutputPin(RaspiPin.GPIO_24, pinIdentifier, PinState.LOW);
                break;
            case 25:
                digitalOutput=GPIO.provisionDigitalOutputPin(RaspiPin.GPIO_25, pinIdentifier, PinState.LOW);
                break;
            case 26:
                digitalOutput=GPIO.provisionDigitalOutputPin(RaspiPin.GPIO_26, pinIdentifier, PinState.LOW);
                break;
            case 27:
                digitalOutput=GPIO.provisionDigitalOutputPin(RaspiPin.GPIO_27, pinIdentifier, PinState.LOW);
                break;
            case 28:
                digitalOutput=GPIO.provisionDigitalOutputPin(RaspiPin.GPIO_28, pinIdentifier, PinState.LOW);
                break;
            case 29:
                digitalOutput=GPIO.provisionDigitalOutputPin(RaspiPin.GPIO_29, pinIdentifier, PinState.LOW);
                break;
        }

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
