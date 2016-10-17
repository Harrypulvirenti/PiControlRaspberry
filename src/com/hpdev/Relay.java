package com.hpdev;

import java.util.ArrayList;

/**
 * Created by harry on 13/10/2016.
 */
public class Relay extends GPIOUser {

    public final static int PIN_NUMBER=1;

    public static final int COMMAND_COUNT=4;

    public static final int COMMAND_TURN_ON_NO=0;
    public static final int COMMAND_TURN_OFF_NO=1;
    public static final int COMMAND_TURN_ON_NC=2;
    public static final int COMMAND_TURN_OFF_NC=3;


    public Relay(String GPIOUserName) {
        super(GPIOUserName, Constants.USER_TYPE_RELAY, PIN_NUMBER);
        GPIOPin pin= GPIOController.getDigitalOutputPin(GPIOUserName);
        addUserPin(pin);
        setCommandCount(COMMAND_COUNT);
    }

    public Relay(XMLUser user, ArrayList<GPIOPin> list) {
        super(user.getUserName(), Constants.USER_TYPE_RELAY, PIN_NUMBER);
        GPIOPin pin= GPIOController.getDigitalOutputPin(user.getUserName(),list.get(0).getPinNumber());
        addUserPin(pin);
        setCommandCount(COMMAND_COUNT);
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

    @Override
    public Object executeCommand(int command) {

        switch (command){
            case COMMAND_TURN_ON_NO:
                turnON_NO();
                break;
            case COMMAND_TURN_OFF_NO:
                turnOFF_NO();
                break;
            case COMMAND_TURN_ON_NC:
                turnON_NC();
                break;
            case COMMAND_TURN_OFF_NC:
                turnOFF_NC();
                break;
        }
        return null;
    }
}
