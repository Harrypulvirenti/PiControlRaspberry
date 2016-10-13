package com.hpdev;

import java.util.ArrayList;

/**
 * Created by harry on 09/10/2016.
 */
public class GPIORoom {

    private String Name;
    private ArrayList<GPIOUser> userList;

    private int roomType=0;

    public GPIORoom(String name,int roomType) {
        Name = name;
        this.roomType=roomType;
        userList=new ArrayList<GPIOUser>();
    }

    public GPIORoom(String name, ArrayList<GPIOUser> users, int roomType ) {
        Name = name;
        userList=users;
        this.roomType=roomType;
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

    public int getRoomType(){
        return roomType;
    }
}
