package com.hpdev;

import java.util.ArrayList;

/**
 * Created by harry on 09/10/2016.
 */
public class XMLWrapper {

    private String Loginkey;
    private ArrayList<Pin> pinList;


    public XMLWrapper(String loginkey) {
        Loginkey = loginkey;
        pinList=new ArrayList<Pin>();
    }

    public String getLoginkey() {
        return Loginkey;
    }

    public void addPin(Pin pin){
        pinList.add(pin);
    }

    public ArrayList<Pin> getPinList() {
        return pinList;
    }
}
