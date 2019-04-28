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
       

        
        if (Securi_pi.status != temp){
            System.out.println("Status has changed");
            temp = Securi_pi.status;
        }
      
        //if (temp == true){
            
            //System.out.println("System has triggered");
            
            // Attempt to create an instance of RPiCamera, will fail if raspistill is not properly installed
            /*RPiCamera piCamera = null;
            String saveDir = "/home/pi/Pictures";
            try {
                piCamera = new RPiCamera(saveDir);
            } catch (FailedToRunRaspistillException ex) {
                Logger.getLogger(MainProgramThread.class.getName()).log(Level.SEVERE, null, ex);
            }


            //Encapsulate this in a loop to test whether the Arduino is connected via ACM0 or ACM1
            SerialPort serialPort = null;
            String serialPortPath = "/dev/ttyACM0";
            Boolean activeSerial = false;

            while (activeSerial == false) {
                try {

                    serialPort = new SerialPort(serialPortPath);
                    System.out.println("Port opened: " + serialPort.openPort());
                    System.out.println("Params setted: " + serialPort.setParams(9600, 8, 1, 0));
                    activeSerial = true;

                } catch (SerialPortException ex) {
                    serialPortPath = "/dev/ttyACM1";
                    Logger.getLogger(MainProgramThread.class.getName()).log(Level.SEVERE, null, ex);
                }
            }


            try{

                while(true)
                {           
                    byte[] readSerial = serialPort.readBytes();
                    if (readSerial != null)
                    {

                        if (piCamera != null){
                        shootStill(piCamera);

                            try {
                                SendAttachmentInEmail sendEmail = new SendAttachmentInEmail();
                            } catch (IOException ex) {
                                Logger.getLogger(MainProgramThread.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (NamingException ex) {
                                Logger.getLogger(MainProgramThread.class.getName()).log(Level.SEVERE, null, ex);
                            }


                        serialPort.writeString("L");
                        }
                    }
                }
            } catch (SerialPortException ex) {
                System.out.println(ex);
            }

            //The arduino needs the code below to activate Light/sound
            try
            {
                serialPort.closePort();

            } catch(SerialPortException ex){

                System.out.println(ex);
            }
            */
        //}
        
    }
}
