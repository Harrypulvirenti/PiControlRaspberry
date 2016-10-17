package com.hpdev;

import java.util.ArrayList;

/**
 * Created by harry on 09/10/2016.
 */
public class GPIOUser {

    private ArrayList<GPIOPin> PinList;
    private String GPIOUserName;
    private int GPIOUserType;
    private int pinNumber;
    private int CommandCount;

    public GPIOUser(String GPIOUserName, int GPIOType, int pinNumber) {
        this.GPIOUserName = GPIOUserName;
        this.GPIOUserType = GPIOType;
        this.pinNumber = pinNumber;
        PinList=new ArrayList<GPIOPin>();
    }

    public GPIOUser (XMLUser user,ArrayList<GPIOPin> list){
        this.GPIOUserName=user.getUserName();
        this.GPIOUserType=user.getType();
        this.pinNumber=user.getPinNumber();
        PinList=list;
    }


    public void addUserPin(GPIOPin pin){
        PinList.add(pin);
    }

    public ArrayList<GPIOPin> getPinList() {
        return PinList;
    }

    public String getGPIOUserName() {
        return GPIOUserName;
    }

    public int getGPIOUserType() {
        return GPIOUserType;
    }

    public int getPinNumber() {
        return pinNumber;
    }

    public int getCommandCount() {
        return CommandCount;
    }

    public void setCommandCount(int commandCount) {
        CommandCount = commandCount;
    }

    public Object executeCommand(int command){return null;};
}
