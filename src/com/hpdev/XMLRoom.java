package com.hpdev;

import java.util.ArrayList;

/**
 * Created by harry on 11/10/2016.
 */
public class XMLRoom {


    private String Name;
    private ArrayList<XMLUser> userList;

    private int RoomType=0;


    public XMLRoom(String name, ArrayList<XMLUser> userList, int roomType) {
        Name = name;
        this.userList = userList;
        RoomType = roomType;
    }

    public String getName() {
        return Name;
    }

    public ArrayList<XMLUser> getUserList() {
        return userList;
    }

    public int getRoomType() {
        return RoomType;
    }
}
