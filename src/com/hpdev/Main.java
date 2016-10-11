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


    private String IP=null;
    private TCPServer Server=null;
    private GPIOController controllerGPIO;
    private String LoginKey;
    private XMLWrapper fileWrapper=null;
    private ArrayList<GPIORoom> GPIORoomList;


    public static void main(String[] args) {

        Main myExecution=new Main();
        myExecution.GPIORoomList =new ArrayList<GPIORoom>();

        if(args.length == 0){
            if(!myExecution.loadBackupFile()){
                System.out.println(Constants.Error_LoginKey);
                System.exit(-1);}

        }else  if(args[0].length()!=8){
            System.out.println(Constants.Error_LoginKeyShort);
            System.exit(-1);
        } else{
            myExecution.setLoginKey(args[0]);
            myExecution.storeBackupFile();
        }

        System.out.println(Constants.LOGIN_MESSAGE+myExecution.LoginKey);

        //GPIO=new GPIOController();
        //pin=GPIO.getDigitalOutputPin("led");
        
        if(!myExecution.isFileWrapperEmpty()){
            myExecution.initRoomList();
        }

        myExecution.StartIPUpdater();
        myExecution.StartServer();

        while(true);


    }

    private void addRoom(String name) {
        GPIORoomList.add(new GPIORoom(name));
        storeBackupFile();
    }

    private void initGPIOPin() {


    }


    public void initRoomList(){

        ArrayList<XMLRoom> roomList=fileWrapper.getXMLRoomList();

        for(int i=0;i<roomList.size();i++){
            XMLRoom room= roomList.get(i);
            ArrayList<XMLUser> userList=room.getUserList();
            ArrayList<GPIOUser> GPIOUserList=new ArrayList<GPIOUser>();
            for(int j=0;j<userList.size();j++){
                XMLUser user=userList.get(j);
                ArrayList<XMLPin> xmlPin=user.getPinList();
                ArrayList<GPIOPin> gpioPins=new ArrayList<GPIOPin>();
                for(int k=0;k<xmlPin.size();k++){
                    XMLPin pin=xmlPin.get(k);
                    gpioPins.add(new GPIOPin(pin));
                }
                GPIOUserList.add(new GPIOUser(user,gpioPins));
            }
            GPIORoomList.add(new GPIORoom(room.getName(),GPIOUserList));


        }


    }

    public void setLoginKey(String loginKey) {
        this.LoginKey = loginKey;
    }

    public boolean isFileWrapperEmpty(){

        if(fileWrapper!=null){
           return false;
        }

        return true;
    }




    private void storeBackupFile() {
        XStream xstream = new XStream(new DomDriver());


        XMLWrapper xmlWrapper=new XMLWrapper(LoginKey);

        ArrayList<XMLRoom> roomList=new ArrayList<XMLRoom>();

        for(int i=0;i<GPIORoomList.size();i++){
            GPIORoom room= GPIORoomList.get(i);
            ArrayList<GPIOUser> userList=room.getUserList();
            ArrayList<XMLUser> xmlUser=new ArrayList<XMLUser>();
            for(int j=0;j<userList.size();j++){
                GPIOUser user=userList.get(j);
                ArrayList<GPIOPin> pinList=user.getPinList();
                ArrayList<XMLPin> xmlPin=new ArrayList<XMLPin>();
                for(int k=0;k<pinList.size();k++){
                    GPIOPin pin=pinList.get(k);
                    xmlPin.add(new XMLPin(pin.getPinNumber(),pin.getPinIdentifier(),pin.getPinType()));
                }
                xmlUser.add(new XMLUser(user.getGPIOUserName(),user.getGPIOUserType(),xmlPin));
            }

            roomList.add(new XMLRoom(room.getName(),xmlUser));

        }

        xmlWrapper.setXMLRoomList(roomList);


        String xml = xstream.toXML(xmlWrapper);

        PrintWriter writer = null;
        try {
            writer = new PrintWriter(Constants.BACKUP_FILE_NAME, "UTF-8");
        } catch (FileNotFoundException e) {

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        writer.println(xml);
        writer.close();


    }

    private boolean loadBackupFile() {
        boolean ret=false;

        File fileDir = new File(Constants.BACKUP_FILE_NAME);

        BufferedReader in = null;
        String Result="";

        try {
            in = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(fileDir), "UTF8"));

            String str;

            while ((str = in.readLine()) != null) {
                Result+=str;
                Result+="\n";
            }

            in.close();

            ret=true;

            Result = Result.substring(0, Result.length()-1);
            XStream xstream = new XStream(new DomDriver());

            fileWrapper=(XMLWrapper)xstream.fromXML(Result);

            LoginKey=fileWrapper.getLoginkey();
            
            

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            ret=false;
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            return ret;
        }
    }

    private void StartIPUpdater(){
        Timer timer = new Timer();
        timer.schedule(new IPUpdater(), 0, 600000);
    }


    private static int executeCommand(int command){
        return 0;
    }



    private class IPUpdater extends TimerTask{

        private void SendIPToServer() {



        GetIP();
        if(IP!=null){


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
        URL whatismyip=null;
        String ip=null;
        try {
            whatismyip = new URL(Constants.GET_IP_URL);
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    whatismyip.openStream()));

            ip = in.readLine();
        } catch (MalformedURLException ex) {
            IP=null;
        } catch (IOException ex) {
            IP=null;
        }
        IP=ip;
    }


    private void StartServer(){
        Server=new TCPServer();
        Server.setDaemon(true);
        Server.start();
    }

    private class TCPServer extends Thread {

        private final int PORT_NUMBER=8888;
        ServerSocket SERVER=null;

        private final int COMMAND_1=1;

        @Override
        public void run() {
            super.run(); //To change body of generated methods, choose Tools | Templates.

            String clientSentence;
            String capitalizedSentence;

            try {
                SERVER = new ServerSocket(PORT_NUMBER);

                while(true)
                {
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

    private class CommandExecutor implements Runnable{

        private static final String TYPE_ADD_ROOM="room";
        private static final String TYPE_ADD_USER="user";
        private static final String TYPE_EXEC_COMMAND="command";
        private static final String TYPE_UPDATE_REQUEST="update";


        private Socket connectionSocket;

        public CommandExecutor( Socket connecSocket){
            this.connectionSocket=connecSocket;
        }

        @Override
        public void run() {
            String clientSentence;
            String serverSentence;


            try {
                BufferedReader inFromClient =
                        new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
                DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());

                clientSentence = inFromClient.readLine();
                if(clientSentence.equalsIgnoreCase(TYPE_ADD_ROOM)){
                    serverSentence = Constants.SEND_WAIT_MESSAGE+"\n";
                    outToClient.writeBytes(serverSentence);
                    clientSentence = inFromClient.readLine();
                    serverSentence = Constants.DONE_MESSAGE+"\n";
                    outToClient.writeBytes(serverSentence);
                    connectionSocket.close();
                    addRoom(clientSentence);

                }

                if(clientSentence.equalsIgnoreCase(TYPE_ADD_USER)){

                }

                if(clientSentence.equalsIgnoreCase(TYPE_EXEC_COMMAND)){

                }
                if(clientSentence.equalsIgnoreCase(TYPE_UPDATE_REQUEST)){

                }


                //int ret=executeCommand(Integer.parseInt(clientSentence));

                //serverSentence = String.valueOf(ret)+ '\n';
                //outToClient.writeBytes(serverSentence);


            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }



}
