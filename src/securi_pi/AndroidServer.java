/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package securi_pi;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class AndroidServer implements Runnable {

    private boolean serverRunning = false;
    private String serverName = "SecuriPi Demo Server";

    int portNum = 20101;
    ServerSocket serverSoc = null;

    public AndroidServer(int port) {
        if (port > 2048) {
            portNum = port;
        } else {
            System.err.println("Port number too low, defaulting to " + portNum);
        }
    }
    
    public void run () {
            try {
        // Setup socket for communication
        serverSoc = new ServerSocket(portNum);

        while (true) {
            // Accept incoming connections
            System.out.println("Waiting for connection...");
            Socket soc = serverSoc.accept();

            // Create a new thread for the connection to start
            ServerConnectionHandler sch = new ServerConnectionHandler(soc, serverName);
            Thread schThread = new Thread(sch);
            schThread.start();
        }

        } catch (Exception e) {
            System.err.println("Error: " + e.toString());
        }
    }
}

class ServerConnectionHandler implements Runnable {

    Socket soc = null;
    String serverName;

    public ServerConnectionHandler(Socket clientSoc, String serverName) {
        soc = clientSoc;
        this.serverName = serverName;
    }

    private void close() {
        soc = null;
    }

    public void run() {
        try {
            System.out.println("Client connected");

            BufferedReader reader = new BufferedReader(new InputStreamReader(soc.getInputStream()));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(soc.getOutputStream()));

            // Receive message
            System.out.println("Waiting for input...");
            String message = reader.readLine();
            System.out.println("Received: " + message);
            
            //Parse out status from message
            HashMap<String, String> map = MessageBuilder.separateElements(message);
            boolean temp = Boolean.parseBoolean(map.get("meta"));
            System.out.println("The retrieved boolean is " + temp);
            
            if (map.get("req") == "setStatus"){
                Securi_pi.status = Boolean.parseBoolean(map.get("meta"));
                System.out.println("The system status is " + Securi_pi.status);
            }

            // Handle message
            String resp = MessageBuilder.handleResponse(message, serverName,true); // SERVERRUNNING NEEDS TO BE CHANGED HERE...
            writer.write(resp + "\n");
            writer.flush();
            System.out.println("Responded with: " + resp);

            reader.close();
            writer.close();

            // Close socket
            close();
            System.out.println("Socket closed.");

        } catch (Exception e) {
            close();
        }
    }
}

