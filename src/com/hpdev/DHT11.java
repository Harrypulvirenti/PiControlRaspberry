package com.hpdev;

import com.pi4j.wiringpi.Gpio;
import com.pi4j.wiringpi.GpioUtil;

import java.util.ArrayList;

/**
 * Created by harry on 16/10/2016.
 */
public class DHT11 extends GPIOUser {



    public final static int PIN_NUMBER=1;

    public static final int COMMAND_COUNT=1;

    public static final int COMMAND_GET_DATA=0;

    private static final int MAXTIMINGS = 85;
    private int[] dht11_dat = {0, 0, 0, 0, 0};


    public DHT11(String GPIOUserName) {
        super(GPIOUserName, Constants.USER_TYPE_SENSOR_DH11, PIN_NUMBER);

        GPIOPin pin= GPIOController.getGPIOFreePin(GPIOUserName);
        addUserPin(pin);
        setCommandCount(COMMAND_COUNT);

        GpioUtil.export(pin.getPinNumber(), GpioUtil.DIRECTION_OUT);

    }

    public DHT11(XMLUser user, ArrayList<GPIOPin> list) {
        super(user, list);
    }

    public String getData(){
        String ret=null;

        while (ret==null){
        int laststate = Gpio.HIGH;
        int j = 0;
        dht11_dat[0] = dht11_dat[1] = dht11_dat[2] = dht11_dat[3] = dht11_dat[4] = 0;
        int pin=getPinList().get(0).getPinNumber();


        Gpio.pinMode(pin, Gpio.OUTPUT);
        Gpio.digitalWrite(pin, Gpio.LOW);
        Gpio.delay(18);

        Gpio.digitalWrite(pin, Gpio.HIGH);
        Gpio.pinMode(pin, Gpio.INPUT);

        for (int i = 0; i < MAXTIMINGS; i++) {
            int counter = 0;
            while (Gpio.digitalRead(pin) == laststate) {
                counter++;
                Gpio.delayMicroseconds(1);
                if (counter == 255) {
                    break;
                }
            }

            laststate = Gpio.digitalRead(pin);

            if (counter == 255) {
                break;
            }

      /* ignore first 3 transitions */
            if ((i >= 4) && (i % 2 == 0)) {
         /* shove each bit into the storage bytes */
                dht11_dat[j / 8] <<= 1;
                if (counter > 16) {
                    dht11_dat[j / 8] |= 1;
                }
                j++;
            }
        }
        // check we read 40 bits (8bit x 5 ) + verify checksum in the last
        // byte
        if ((j >= 40) && checkParity()) {
            float h = (float) ((dht11_dat[0] << 8) + dht11_dat[1]) / 10;
            if (h > 100) {
                h = dht11_dat[0];   // for DHT11
            }
            float c = (float) (((dht11_dat[2] & 0x7F) << 8) + dht11_dat[3]) / 10;
            if (c > 125) {
                c = dht11_dat[2];   // for DHT11
            }
            if ((dht11_dat[2] & 0x80) != 0) {
                c = -c;
            }
            float f = c * 1.8f + 32;
            ret= c+"-"+h;
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            ret= null;
        }}
        return null;
    }

    @Override
    public Object executeCommand(int command) {
        return getData();
    }

    private boolean checkParity() {
        return (dht11_dat[4] == ((dht11_dat[0] + dht11_dat[1] + dht11_dat[2] + dht11_dat[3]) & 0xFF));
    }
}
