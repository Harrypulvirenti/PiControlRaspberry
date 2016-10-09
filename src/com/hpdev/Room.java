package com.hpdev;

import java.util.ArrayList;

/**
 * Created by harry on 09/10/2016.
 */
public class Room {

    private String Name;
    private ArrayList<GPIOUser> userList;

    public Room(String name) {
        Name = name;
        userList=new ArrayList<GPIOUser>();
    }

    public void addUser(GPIOUser user){
        userList.add(user);
    }

    public String getName() {
        return Name;
    }

    public ArrayList<GPIOUser> getUserList() {
        return userList;
    }
}
