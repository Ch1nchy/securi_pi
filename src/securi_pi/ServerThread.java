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
        
        System.out.println("You are in the server thread");
        Securi_pi.status = "disarmed";
        
        System.out.println(Securi_pi.status);
    }
}
