package com.hpdev;

import java.util.ArrayList;

/**
 * Created by harry on 13/10/2016.
 */
public class Relay extends GPIOUser {

    public final static int PIN_NUMBER=1;


    public Relay(String GPIOUserName) {
        super(GPIOUserName, Constants.USER_TYPE_RELAY, PIN_NUMBER);
        GPIOPin pin= GPIOController.getDigitalOutputPin(GPIOUserName);
        addUserPin(pin);
    }

    public Relay(XMLUser user, ArrayList<GPIOPin> list) {
        super(user, list);
    }

    public void turnON_NO(){
        GPIOController.setDigitalPinState(getPinList().get(0).getPinNumber(),GPIOController.HIGH);
    }
    public void turnOFF_NO(){
        GPIOController.setDigitalPinState(getPinList().get(0).getPinNumber(),GPIOController.LOW);
    }
    public void turnON_NC(){
        GPIOController.setDigitalPinState(getPinList().get(0).getPinNumber(),GPIOController.LOW);
    }
    public void turnOFF_NC(){
        GPIOController.setDigitalPinState(getPinList().get(0).getPinNumber(),GPIOController.HIGH);
    }

}
