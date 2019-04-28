/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package securi_pi;

import com.hopding.jrpicam.exceptions.FailedToRunRaspistillException;
import java.io.IOException;
import javax.naming.NamingException;

/**
 *
 * @author pi
 */
public class Securi_pi {
    
    static boolean status = false;
    
    public static void main(String[] args) throws FailedToRunRaspistillException, IOException, NamingException {
    
        //This thread starts the main program that listens for the usb serial and
        //carries out all the associated tasks
        MainProgramThread teamProjects = new MainProgramThread();
        Thread tpThread = new Thread(teamProjects);      
        tpThread.start();      
        
        
        AndroidServer serverThread = new AndroidServer(20101);
        Thread serThread = new Thread(serverThread);
        serThread.start();
        
    }
}
