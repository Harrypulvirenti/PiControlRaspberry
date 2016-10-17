/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hpdev;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import java.util.ArrayList;

/**
 *
 * @author harry
 */
public class GPIOController {
    private static final int GPIO_TOTAL=30;
    private static ArrayList<GPIOPin> allPin;
    private static GpioController GPIO;
    public static final int LOW=0;
    public static final int HIGH=1;


    public static void initIstance(ArrayList<XMLRoom> list){
        GPIO = GpioFactory.getInstance();
        allPin=new ArrayList<>();
        initPin(list);
    }

    private static void initPin(ArrayList<XMLRoom> list){
        for(int i=0;i<GPIO_TOTAL;i++){
            allPin.add(i, new GPIOPin(false, i));
        }
    }


    public static void initIstance(){
        GPIO = GpioFactory.getInstance();
        allPin=new ArrayList<>();
        initPin();
    }
    
   private static void initPin(){
        for(int i=0;i<GPIO_TOTAL;i++){
           allPin.add(i, new GPIOPin(false, i));
        }
    }
   
  public static GPIOPin getDigitalOutputPin(String identifier){
      int pinNumber=getFreePin();
      GpioPinDigitalOutput GPIOPin=null;
      switch(pinNumber){
              case 0:              
              GPIOPin=GPIO.provisionDigitalOutputPin(RaspiPin.GPIO_00, identifier, PinState.LOW);
              break;
              case 1:              
              GPIOPin=GPIO.provisionDigitalOutputPin(RaspiPin.GPIO_01, identifier, PinState.LOW);
              break;
              case 2:              
              GPIOPin=GPIO.provisionDigitalOutputPin(RaspiPin.GPIO_02, identifier, PinState.LOW);
              break;
              case 3:              
              GPIOPin=GPIO.provisionDigitalOutputPin(RaspiPin.GPIO_03, identifier, PinState.LOW);
              break;
              case 4:              
              GPIOPin=GPIO.provisionDigitalOutputPin(RaspiPin.GPIO_04, identifier, PinState.LOW);
              break;
              case 5:              
              GPIOPin=GPIO.provisionDigitalOutputPin(RaspiPin.GPIO_05, identifier, PinState.LOW);
              break;
              case 6:              
              GPIOPin=GPIO.provisionDigitalOutputPin(RaspiPin.GPIO_06, identifier, PinState.LOW);
              break;
              case 7:              
              GPIOPin=GPIO.provisionDigitalOutputPin(RaspiPin.GPIO_07, identifier, PinState.LOW);
              break;
              case 8:              
              GPIOPin=GPIO.provisionDigitalOutputPin(RaspiPin.GPIO_08, identifier, PinState.LOW);
              break;
              case 9:              
              GPIOPin=GPIO.provisionDigitalOutputPin(RaspiPin.GPIO_09, identifier, PinState.LOW);
              break;
              case 10:              
              GPIOPin=GPIO.provisionDigitalOutputPin(RaspiPin.GPIO_10, identifier, PinState.LOW);
              break;
              case 11:              
              GPIOPin=GPIO.provisionDigitalOutputPin(RaspiPin.GPIO_11, identifier, PinState.LOW);
              break;
              case 12:              
              GPIOPin=GPIO.provisionDigitalOutputPin(RaspiPin.GPIO_12, identifier, PinState.LOW);
              break;
              case 13:              
              GPIOPin=GPIO.provisionDigitalOutputPin(RaspiPin.GPIO_13, identifier, PinState.LOW);
              break;
              case 14:              
              GPIOPin=GPIO.provisionDigitalOutputPin(RaspiPin.GPIO_14, identifier, PinState.LOW);
              break;
              case 15:              
              GPIOPin=GPIO.provisionDigitalOutputPin(RaspiPin.GPIO_15, identifier, PinState.LOW);
              break;
              case 16:              
              GPIOPin=GPIO.provisionDigitalOutputPin(RaspiPin.GPIO_16, identifier, PinState.LOW);
              break;
              case 17:              
              GPIOPin=GPIO.provisionDigitalOutputPin(RaspiPin.GPIO_17, identifier, PinState.LOW);
              break;
              case 18:              
              GPIOPin=GPIO.provisionDigitalOutputPin(RaspiPin.GPIO_18, identifier, PinState.LOW);
              break;
              case 19:              
              GPIOPin=GPIO.provisionDigitalOutputPin(RaspiPin.GPIO_19, identifier, PinState.LOW);
              break;
              case 20:              
              GPIOPin=GPIO.provisionDigitalOutputPin(RaspiPin.GPIO_20, identifier, PinState.LOW);
              break;
              case 21:              
              GPIOPin=GPIO.provisionDigitalOutputPin(RaspiPin.GPIO_21, identifier, PinState.LOW);
              break;
              case 22:              
              GPIOPin=GPIO.provisionDigitalOutputPin(RaspiPin.GPIO_22, identifier, PinState.LOW);
              break;
              case 23:              
              GPIOPin=GPIO.provisionDigitalOutputPin(RaspiPin.GPIO_23, identifier, PinState.LOW);
              break;
              case 24:              
              GPIOPin=GPIO.provisionDigitalOutputPin(RaspiPin.GPIO_24, identifier, PinState.LOW);
              break;
              case 25:              
              GPIOPin=GPIO.provisionDigitalOutputPin(RaspiPin.GPIO_25, identifier, PinState.LOW);
              break;
              case 26:              
              GPIOPin=GPIO.provisionDigitalOutputPin(RaspiPin.GPIO_26, identifier, PinState.LOW);
              break;
              case 27:              
              GPIOPin=GPIO.provisionDigitalOutputPin(RaspiPin.GPIO_27, identifier, PinState.LOW);
              break;
              case 28:              
              GPIOPin=GPIO.provisionDigitalOutputPin(RaspiPin.GPIO_28, identifier, PinState.LOW);
              break;
              case 29:              
              GPIOPin=GPIO.provisionDigitalOutputPin(RaspiPin.GPIO_29, identifier, PinState.LOW);
              break;
      }
     GPIOPin.setShutdownOptions(true, PinState.LOW);
     GPIOPin newPin= new GPIOPin(pinNumber, GPIOPin);
      newPin.setPinIdentifier(identifier);

     allPin.remove(pinNumber);
     allPin.add(pinNumber, newPin);
      return newPin;
  }
  
  public static void setDigitalPinState(int pinNumber,int state){
      if(state==LOW){
          allPin.get(pinNumber).getDigitalOutput().low();
      }else{
          allPin.get(pinNumber).getDigitalOutput().high();
      }
  
  }

  public static GPIOPin getGPIOFreePin(String identifier){
      int pinNumber=getFreePin();

      GPIOPin newPin= new GPIOPin(true,pinNumber);
      newPin.setPinIdentifier(identifier);

      allPin.remove(pinNumber);
      allPin.add(pinNumber, newPin);
      return newPin;
  }
  
  private static int getFreePin(){
      int i;
      for(i=0;i<GPIO_TOTAL;i++){
          if(!allPin.get(i).isPinUsed())
              break;
      }
  return i;
  }

  public static int totalFreePin(){
      int tot=0;
      for(int i=0;i<GPIO_TOTAL;i++){
          if(!allPin.get(i).isPinUsed())
              tot++;
      }
      return tot;
  }

    public static GPIOPin getDigitalOutputPin(String identifier, int pinNumber) {
        GpioPinDigitalOutput GPIOPin=null;
        switch(pinNumber){
            case 0:
                GPIOPin=GPIO.provisionDigitalOutputPin(RaspiPin.GPIO_00, identifier, PinState.LOW);
                break;
            case 1:
                GPIOPin=GPIO.provisionDigitalOutputPin(RaspiPin.GPIO_01, identifier, PinState.LOW);
                break;
            case 2:
                GPIOPin=GPIO.provisionDigitalOutputPin(RaspiPin.GPIO_02, identifier, PinState.LOW);
                break;
            case 3:
                GPIOPin=GPIO.provisionDigitalOutputPin(RaspiPin.GPIO_03, identifier, PinState.LOW);
                break;
            case 4:
                GPIOPin=GPIO.provisionDigitalOutputPin(RaspiPin.GPIO_04, identifier, PinState.LOW);
                break;
            case 5:
                GPIOPin=GPIO.provisionDigitalOutputPin(RaspiPin.GPIO_05, identifier, PinState.LOW);
                break;
            case 6:
                GPIOPin=GPIO.provisionDigitalOutputPin(RaspiPin.GPIO_06, identifier, PinState.LOW);
                break;
            case 7:
                GPIOPin=GPIO.provisionDigitalOutputPin(RaspiPin.GPIO_07, identifier, PinState.LOW);
                break;
            case 8:
                GPIOPin=GPIO.provisionDigitalOutputPin(RaspiPin.GPIO_08, identifier, PinState.LOW);
                break;
            case 9:
                GPIOPin=GPIO.provisionDigitalOutputPin(RaspiPin.GPIO_09, identifier, PinState.LOW);
                break;
            case 10:
                GPIOPin=GPIO.provisionDigitalOutputPin(RaspiPin.GPIO_10, identifier, PinState.LOW);
                break;
            case 11:
                GPIOPin=GPIO.provisionDigitalOutputPin(RaspiPin.GPIO_11, identifier, PinState.LOW);
                break;
            case 12:
                GPIOPin=GPIO.provisionDigitalOutputPin(RaspiPin.GPIO_12, identifier, PinState.LOW);
                break;
            case 13:
                GPIOPin=GPIO.provisionDigitalOutputPin(RaspiPin.GPIO_13, identifier, PinState.LOW);
                break;
            case 14:
                GPIOPin=GPIO.provisionDigitalOutputPin(RaspiPin.GPIO_14, identifier, PinState.LOW);
                break;
            case 15:
                GPIOPin=GPIO.provisionDigitalOutputPin(RaspiPin.GPIO_15, identifier, PinState.LOW);
                break;
            case 16:
                GPIOPin=GPIO.provisionDigitalOutputPin(RaspiPin.GPIO_16, identifier, PinState.LOW);
                break;
            case 17:
                GPIOPin=GPIO.provisionDigitalOutputPin(RaspiPin.GPIO_17, identifier, PinState.LOW);
                break;
            case 18:
                GPIOPin=GPIO.provisionDigitalOutputPin(RaspiPin.GPIO_18, identifier, PinState.LOW);
                break;
            case 19:
                GPIOPin=GPIO.provisionDigitalOutputPin(RaspiPin.GPIO_19, identifier, PinState.LOW);
                break;
            case 20:
                GPIOPin=GPIO.provisionDigitalOutputPin(RaspiPin.GPIO_20, identifier, PinState.LOW);
                break;
            case 21:
                GPIOPin=GPIO.provisionDigitalOutputPin(RaspiPin.GPIO_21, identifier, PinState.LOW);
                break;
            case 22:
                GPIOPin=GPIO.provisionDigitalOutputPin(RaspiPin.GPIO_22, identifier, PinState.LOW);
                break;
            case 23:
                GPIOPin=GPIO.provisionDigitalOutputPin(RaspiPin.GPIO_23, identifier, PinState.LOW);
                break;
            case 24:
                GPIOPin=GPIO.provisionDigitalOutputPin(RaspiPin.GPIO_24, identifier, PinState.LOW);
                break;
            case 25:
                GPIOPin=GPIO.provisionDigitalOutputPin(RaspiPin.GPIO_25, identifier, PinState.LOW);
                break;
            case 26:
                GPIOPin=GPIO.provisionDigitalOutputPin(RaspiPin.GPIO_26, identifier, PinState.LOW);
                break;
            case 27:
                GPIOPin=GPIO.provisionDigitalOutputPin(RaspiPin.GPIO_27, identifier, PinState.LOW);
                break;
            case 28:
                GPIOPin=GPIO.provisionDigitalOutputPin(RaspiPin.GPIO_28, identifier, PinState.LOW);
                break;
            case 29:
                GPIOPin=GPIO.provisionDigitalOutputPin(RaspiPin.GPIO_29, identifier, PinState.LOW);
                break;
        }
        GPIOPin.setShutdownOptions(true, PinState.LOW);
        GPIOPin newPin= new GPIOPin(pinNumber, GPIOPin);
        newPin.setPinIdentifier(identifier);

        allPin.remove(pinNumber);
        allPin.add(pinNumber, newPin);
        return newPin;
    }

    public static GPIOPin getGPIOFreePin(String identifier, int pinNumber) {

        GPIOPin newPin= new GPIOPin(true,pinNumber);
        newPin.setPinIdentifier(identifier);

        allPin.remove(pinNumber);
        allPin.add(pinNumber, newPin);
        return newPin;
    }
}
