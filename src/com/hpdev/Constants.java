package com.hpdev;

/**
 * Created by harry on 07/10/2016.
 */
public class Constants {
    public final static String Error_LoginKey="Inserire la LoginKey.";
    public final static String Error_LoginKeyShort="La LoginKey è errata.\nRiprovare.";
    public static final String USER_AGENT = "Mozilla/5.0";
    public static final String GET_IP_URL="http://checkip.amazonaws.com";
    public static final String LOGIN_URL="http://harrydev.altervista.org/Tesi/loginPi.php";
    public static final String KEY_PI_ID="pi_id";
    public static final String KEY_PI_IP="pi_ip";
    public static final String BACKUP_FILE_NAME="backup.xml";
    public static final String LOGIN_MESSAGE="Inizializzazione servizi\n\nLogin Key: ";

    public static final String SEND_WAIT_MESSAGE="ok_send_me";
	public static final String DONE_MESSAGE="done";

    public final static int USER_TYPE_RELAY=0;
    public final static int USER_TYPE_SENSOR_DH11=1;

    public final static int ANALOG_INPUT=0;
    public final static int ANALOG_OUTPUT=1;
    public final static int DIGITAL_INPUT=2;
    public final static int DIGITAL_OUTPUT=3;

    public final static int TYPE_ROOM=0;
    public final static int TYPE_LIVING_ROOM=1;
    public final static int TYPE_BED_ROOM=2;
    public final static int TYPE_KITCHEN_ROOM=3;
    public final static int TYPE_SWIMMING_POOL_ROOM=4;
    public final static int TYPE_GARDEN_ROOM=5;

}
