package com.hpdev;

import java.io.*;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

public class Main {


    private String IP = null;
    private TCPServer Server = null;
    private String LoginKey;
    private XMLWrapper fileWrapper = null;
    private ArrayList<GPIORoom> GPIORoomList;


    public static void main(String[] args) {

        Main myExecution = new Main();
        myExecution.GPIORoomList = new ArrayList<GPIORoom>();

        if (args.length == 0) {
            if (!myExecution.loadBackupFile()) {
                System.out.println(Constants.Error_LoginKey);
                System.exit(-1);
            }

        } else if (args[0].length() != 8) {
            System.out.println(Constants.Error_LoginKeyShort);
            System.exit(-1);
        } else {
            myExecution.loadBackupFile();
            if (!myExecution.LoginKey.equalsIgnoreCase(args[0])) {
                myExecution.setLoginKey(args[0]);
                myExecution.GPIORoomList = new ArrayList<GPIORoom>();
                myExecution.storeBackupFile();
            }

        }

        System.out.println(Constants.LOGIN_MESSAGE + myExecution.LoginKey);


        if (!myExecution.isFileWrapperEmpty()) {
            GPIOController.initInstance();
            myExecution.initRoomList();
        } else {
            GPIOController.initInstance();
        }

        myExecution.StartIPUpdater();
        myExecution.StartServer();

        while (true) ;


    }

    private void addRoom(String name, int roomType) {
        GPIORoomList.add(new GPIORoom(name, roomType));
        storeBackupFile();
    }

    private XMLWrapper getFileWrapper() {
        return fileWrapper;
    }


    public void initRoomList() {

        ArrayList<XMLRoom> roomList = fileWrapper.getXMLRoomList();

        for (int i = 0; i < roomList.size(); i++) {
            XMLRoom room = roomList.get(i);
            ArrayList<XMLUser> userList = room.getUserList();
            ArrayList<GPIOUser> GPIOUserList = new ArrayList<GPIOUser>();
            for (int j = 0; j < userList.size(); j++) {
                XMLUser user = userList.get(j);
                ArrayList<XMLPin> xmlPin = user.getPinList();
                ArrayList<GPIOPin> gpioPins = new ArrayList<GPIOPin>();
                for (int k = 0; k < xmlPin.size(); k++) {
                    XMLPin pin = xmlPin.get(k);
                    gpioPins.add(new GPIOPin(pin));
                }


                switch (user.getType()) {
                    case Constants.USER_TYPE_RELAY:
                        GPIOUserList.add(new Relay(user, gpioPins));
                        break;
                    case Constants.USER_TYPE_SENSOR_DH11:
                        GPIOUserList.add(new DHT11(user, gpioPins));
                        break;
                }


            }
            GPIORoomList.add(new GPIORoom(room.getName(), GPIOUserList, room.getRoomType()));


        }


    }

    public void setLoginKey(String loginKey) {
        this.LoginKey = loginKey;
    }

    public boolean isFileWrapperEmpty() {

        if (fileWrapper != null) {
            return false;
        }

        return true;
    }


    private void storeBackupFile() {
        XStream xstream = new XStream(new DomDriver());
        XMLWrapper xmlWrapper = new XMLWrapper(LoginKey);
        ArrayList<XMLRoom> roomList = new ArrayList<XMLRoom>();

        for (GPIORoom room : GPIORoomList) {
            ArrayList<GPIOUser> userList = room.getUserList();
            ArrayList<XMLUser> xmlUser = new ArrayList<XMLUser>();
            for (GPIOUser user : userList) {
                ArrayList<GPIOPin> pinList = user.getPinList();
                ArrayList<XMLPin> xmlPin = new ArrayList<XMLPin>();
                for (GPIOPin pin : pinList) {
                    xmlPin.add(new XMLPin(pin.getPinNumber(), pin.getPinIdentifier(), pin.getPinType()));
                }
                xmlUser.add(new XMLUser(user.getGPIOUserName(), user.getGPIOUserType(), xmlPin));
            }
            roomList.add(new XMLRoom(room.getName(), xmlUser, room.getRoomType()));
        }
        xmlWrapper.setXMLRoomList(roomList);
        String xml = xstream.toXML(xmlWrapper);
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(Constants.BACKUP_FILE_NAME, "UTF-8");
        } catch (FileNotFoundException ignored) {

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        writer.println(xml);
        writer.close();
    }

    private boolean loadBackupFile() {
        boolean ret = false;
        File fileDir = new File(Constants.BACKUP_FILE_NAME);
        BufferedReader in = null;
        String Result = "";
        try {
            in = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(fileDir), "UTF8"));
            String str;
            while ((str = in.readLine()) != null) {
                Result += str;
                Result += "\n";
            }
            in.close();
            ret = true;
            Result = Result.substring(0, Result.length() - 1);
            XStream xstream = new XStream(new DomDriver());
            fileWrapper = (XMLWrapper) xstream.fromXML(Result);
            LoginKey = fileWrapper.getLoginkey();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            ret = false;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return ret;
        }
    }



    private void StartIPUpdater() {
        Timer timer = new Timer();
        timer.schedule(new IPUpdater(), 0, 600000);
    }


    private class IPUpdater extends TimerTask {

        private void SendIPToServer() {


            GetIP();
            if (IP != null) {


                HttpClient httpClient = HttpClientBuilder.create().build();
                HttpPost post = new HttpPost(Constants.LOGIN_URL);

                // add header
                post.setHeader("User-Agent", Constants.USER_AGENT);

                ArrayList<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
                urlParameters.add(new BasicNameValuePair(Constants.KEY_PI_IP, IP));
                urlParameters.add(new BasicNameValuePair(Constants.KEY_PI_ID, LoginKey));


                try {
                    post.setEntity(new UrlEncodedFormEntity(urlParameters));
                    HttpResponse response = httpClient.execute(post);

                } catch (UnsupportedEncodingException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        }


        @Override
        public void run() {
            SendIPToServer();
        }
    }

    private void GetIP() {
        URL whatismyip = null;
        String ip = null;
        try {
            whatismyip = new URL(Constants.GET_IP_URL);
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    whatismyip.openStream()));

            ip = in.readLine();
        } catch (MalformedURLException ex) {
            IP = null;
        } catch (IOException ex) {
            IP = null;
        }
        IP = ip;
    }


    private void StartServer() {
        Server = new TCPServer();
        Server.setDaemon(true);
        Server.start();
    }

    private class TCPServer extends Thread {
        ServerSocket SERVER = null;

        @Override
        public void run() {
            super.run();
            try {
                SERVER = new ServerSocket(Constants.PORT_NUMBER);
                while (true) {
                    Socket connectionSocket = SERVER.accept();
                    Runnable r = new CommandExecutor(connectionSocket);
                    new Thread(r).start();
                }
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        @Override
        public void interrupt() {
            super.interrupt(); //To change body of generated methods, choose Tools | Templates.
            try {
                SERVER.close();
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    private class CommandExecutor implements Runnable {

        private static final String TYPE_ADD_ROOM = "room";
        private static final String TYPE_ADD_USER = "user";
        private static final String TYPE_EXEC_COMMAND = "command";
        private static final String TYPE_UPDATE_REQUEST = "update";


        private Socket connectionSocket;

        public CommandExecutor(Socket connecSocket) {
            this.connectionSocket = connecSocket;
        }

        @Override
        public void run() {
            String clientSentence = "";
            String clientSentence2 = "";
            String clientSentence3 = "";
            String serverSentence = "";


            try {
                BufferedReader inFromClient =
                        new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
                DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());

                clientSentence = inFromClient.readLine();

                if (clientSentence != null) {
                    if (clientSentence.length() > 0) {

                        if (clientSentence.equalsIgnoreCase(TYPE_ADD_ROOM)) {
                            serverSentence = Constants.SEND_WAIT_MESSAGE + "\n";
                            outToClient.writeBytes(serverSentence);
                            clientSentence = inFromClient.readLine();
                            clientSentence2 = inFromClient.readLine();
                            serverSentence = Constants.DONE_MESSAGE + "\n";
                            outToClient.writeBytes(serverSentence);
                            connectionSocket.close();
                            addRoom(clientSentence, Integer.parseInt(clientSentence2));

                        }

                        if (clientSentence.equalsIgnoreCase(TYPE_ADD_USER)) {
                            serverSentence = Constants.SEND_WAIT_MESSAGE + "\n";
                            outToClient.writeBytes(serverSentence);
                            clientSentence3 = inFromClient.readLine();
                            int totFree = GPIOController.totalFreePin();
                            int totRequired = 0;
                            switch (Integer.parseInt(clientSentence3)) {
                                case Constants.USER_TYPE_RELAY:
                                    totRequired = Relay.PIN_NUMBER;
                                    break;
                                case Constants.USER_TYPE_SENSOR_DH11:

                                    break;

                            }
                            if (totFree >= totRequired) {
                                serverSentence = Constants.SEND_WAIT_MESSAGE + "\n";
                                outToClient.writeBytes(serverSentence);
                                clientSentence = inFromClient.readLine();
                                clientSentence2 = inFromClient.readLine();

                                ArrayList<GPIOPin> list = addUserToRoom(clientSentence, clientSentence2, Integer.parseInt(clientSentence3));

                                for (int i = 0; i < list.size(); i++) {
                                    serverSentence = list.get(i).getPinNumber() + "\n";
                                    outToClient.writeBytes(serverSentence);
                                }
                                serverSentence = Constants.DONE_MESSAGE + "\n";
                                outToClient.writeBytes(serverSentence);
                                connectionSocket.close();

                            } else {
                                connectionSocket.close();
                            }

                        }

                        if (clientSentence.equalsIgnoreCase(TYPE_EXEC_COMMAND)) {

                            serverSentence = Constants.SEND_WAIT_MESSAGE + "\n";
                            outToClient.writeBytes(serverSentence);
                            clientSentence = inFromClient.readLine();
                            clientSentence2 = inFromClient.readLine();
                            clientSentence3 = inFromClient.readLine();
                            serverSentence = execClientCommand(new Command(clientSentence, clientSentence2, Integer.parseInt(clientSentence3))) + "\n";
                            outToClient.writeBytes(serverSentence);
                            serverSentence = Constants.DONE_MESSAGE + "\n";
                            outToClient.writeBytes(serverSentence);
                            connectionSocket.close();
                        }


                        if (clientSentence.equalsIgnoreCase(TYPE_UPDATE_REQUEST)) {
                            serverSentence = Constants.SEND_WAIT_MESSAGE + "\n";
                            outToClient.writeBytes(serverSentence);

                            String lines[] = getXMLUpdate().split("\\r?\\n");

                            for (int i = 0; i < lines.length; i++) {
                                outToClient.writeBytes(lines[i] + "\n");
                            }

                            serverSentence = Constants.DONE_MESSAGE + "\n";
                            outToClient.writeBytes(serverSentence);

                            connectionSocket.close();


                        }
                    }
                }


            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private Object execClientCommand(Command command) {

        for (GPIORoom aGPIORoomList : GPIORoomList) {
            if (command.getRoomName().equalsIgnoreCase(aGPIORoomList.getName())) {
                ArrayList<GPIOUser> users = aGPIORoomList.getUserList();

                for (GPIOUser user : users) {
                    if (command.getUserName().equalsIgnoreCase(user.getGPIOUserName())) {
                        return user.executeCommand(command.getCommand());
                    }
                }
            }
        }
        return null;
    }

    private ArrayList<GPIOPin> addUserToRoom(String UserName, String RoomName, int UserType) {

        for (int i = 0; i < GPIORoomList.size(); i++) {
            GPIORoom room = GPIORoomList.get(i);
            if (room.getName().equalsIgnoreCase(RoomName)) {
                switch (UserType) {
                    case Constants.USER_TYPE_RELAY:
                        Relay rel = new Relay(UserName);
                        room.addUser(rel);
                        GPIORoomList.remove(i);
                        GPIORoomList.add(i, room);
                        storeBackupFile();
                        return rel.getPinList();
                    case Constants.USER_TYPE_SENSOR_DH11:
                        DHT11 dh11 = new DHT11(UserName);
                        room.addUser(dh11);
                        GPIORoomList.remove(i);
                        GPIORoomList.add(i, room);
                        storeBackupFile();
                        return dh11.getPinList();
                }

                break;
            }
        }


        return null;
    }

    private String getXMLUpdate() {
        XStream xstream = new XStream(new DomDriver());


        XMLWrapper xmlWrapper = new XMLWrapper(LoginKey);

        ArrayList<XMLRoom> roomList = new ArrayList<XMLRoom>();

        for (int i = 0; i < GPIORoomList.size(); i++) {
            GPIORoom room = GPIORoomList.get(i);
            ArrayList<GPIOUser> userList = room.getUserList();
            ArrayList<XMLUser> xmlUser = new ArrayList<XMLUser>();
            for (int j = 0; j < userList.size(); j++) {
                GPIOUser user = userList.get(j);
                ArrayList<GPIOPin> pinList = user.getPinList();
                ArrayList<XMLPin> xmlPin = new ArrayList<XMLPin>();
                for (int k = 0; k < pinList.size(); k++) {
                    GPIOPin pin = pinList.get(k);
                    xmlPin.add(new XMLPin(pin.getPinNumber(), pin.getPinIdentifier(), pin.getPinType()));
                }
                xmlUser.add(new XMLUser(user.getGPIOUserName(), user.getGPIOUserType(), xmlPin));
            }

            roomList.add(new XMLRoom(room.getName(), xmlUser, room.getRoomType()));

        }

        xmlWrapper.setXMLRoomList(roomList);


        return xstream.toXML(xmlWrapper);
    }


}
