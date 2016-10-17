package com.hpdev;

/**
 * Created by harry on 16/10/2016.
 */

public class Command {
    private String PiName;
    private String RoomName;
    private String UserName;
    private int Command;


    public Command( String roomName, String userName, int command) {
        RoomName = roomName;
        UserName = userName;
        Command = command;
    }


    public String getPiName() {
        return PiName;
    }

    public String getRoomName() {
        return RoomName;
    }

    public String getUserName() {
        return UserName;
    }

    public int getCommand() {
        return Command;
    }

}
