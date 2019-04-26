/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package securi_pi;

/**
 *
 * @author pi
 */
public class ServerThread implements Runnable {
    
    public void run () {
        
        while (true){
            if ("Disarmed".equals(Securi_pi.status)){
            System.out.println("You are in the server thread, status is " + Securi_pi.status);
            Securi_pi.status = "Armed";
            }
        }
    }
}
