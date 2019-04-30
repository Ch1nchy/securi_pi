/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package securi_pi;
import com.hopding.jrpicam.RPiCamera;
import com.hopding.jrpicam.exceptions.FailedToRunRaspistillException;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import jssc.SerialPort;
import static jssc.SerialPort.PURGE_RXCLEAR;
import static jssc.SerialPort.PURGE_TXCLEAR;
import jssc.SerialPortException;
import static securi_pi.ShootStill.shootStill;

/**
 *
 * @author pi
 */
public class MainProgramThread implements Runnable{
    
    boolean temp;
    
    public MainProgramThread () {
        temp = Securi_pi.status;
    }
    
    
    public void run() {
       
        //Encapsulate this in a loop to test whether the Arduino is connected via ACM0 or ACM1
        SerialPort serialPort = null;
        String serialPortPath = "/dev/ttyACM0";
        Boolean activeSerial = false;

        while (activeSerial == false) {
            try {

                serialPort = new SerialPort(serialPortPath);
                System.out.println("Port opened: " + serialPort.openPort());
                System.out.println("Params setted: " + serialPort.setParams(9600, 8, 1, 0));
                serialPort.closePort();
                activeSerial = true;

            } catch (SerialPortException ex) {
                try {
                    serialPortPath = "/dev/ttyACM1";
                    serialPort = new SerialPort(serialPortPath);
                    System.out.println("Port opened: " + serialPort.openPort());
                    System.out.println("Params setted: " + serialPort.setParams(9600, 8, 1, 0));
                    serialPort.closePort();
                    activeSerial = true;
                } catch (SerialPortException ex1) {
                    Logger.getLogger(MainProgramThread.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }
        }
        
        while(true){
     
            if (Securi_pi.status == true){
            
                //Check if USB serial port is opened, and if not, open it
                if(serialPort.isOpened() == false) {
                    try {
                        serialPort.openPort();
                    } catch (SerialPortException ex) {
                        Logger.getLogger(MainProgramThread.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
                //Intentional delay to allow users to leave premesis before activation
                try {
                    Thread.sleep(10000);
                    serialPort.writeString("P");
                } catch (SerialPortException ex) {
                    Logger.getLogger(MainProgramThread.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    Logger.getLogger(MainProgramThread.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                
                System.out.println("System has armed");

                // Attempt to create an instance of RPiCamera, will fail if raspistill is not properly installed
                /*RPiCamera piCamera = null;
                String saveDir = "/home/pi/Pictures";
                try {
                    piCamera = new RPiCamera(saveDir);
                } catch (FailedToRunRaspistillException ex) {
                    Logger.getLogger(MainProgramThread.class.getName()).log(Level.SEVERE, null, ex);
                }*/
                
                
                try{
               
                    while(Securi_pi.status == true)
                    {     
                        
                        String readSerial = serialPort.readString();
                        serialPort.purgePort(PURGE_RXCLEAR);
                        serialPort.purgePort(PURGE_TXCLEAR);
                        if (readSerial != null)
                        {
                            
                            RPiCamera piCamera = null;
                            String saveDir = "/home/pi/Pictures";
                            try {
                                piCamera = new RPiCamera(saveDir);
                            } catch (FailedToRunRaspistillException ex) {
                                Logger.getLogger(MainProgramThread.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            
                            if (piCamera != null){
                                
                                shootStill(piCamera);
                                Thread sendEmailThread = new Thread() {
                                    public void run() {
                                        try {
                                            SendAttachmentInEmail sendEmail = new SendAttachmentInEmail();
                                        } catch (IOException | NamingException ex) {
                                            Logger.getLogger(MainProgramThread.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                    }
                                };
                                sendEmailThread.start();
                                
                                //serialPort.writeString("R");
                                //serialPort.closePort();
                                break;
                            }
                        }
                    }
                } catch (SerialPortException ex) {
                    System.out.println(ex);
                } 
            } 
            
            if (Securi_pi.status == false) {
                try {
                    
                    if(serialPort.isOpened() == false) {
                        try {
                            serialPort.openPort();
                        } catch (SerialPortException ex) {
                            Logger.getLogger(MainProgramThread.class.getName()).log(Level.SEVERE, null, ex);       
                        }
                    }
                    serialPort.writeString("R");
                } catch (SerialPortException ex) {
                    Logger.getLogger(MainProgramThread.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        } 
    }
}
