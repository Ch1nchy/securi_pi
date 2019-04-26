/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package securi_pi;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.regex.Pattern;

public class MessageBuilder {
    public MessageBuilder() {

    }

    private static String structureBuilder(String name, boolean status, String req, String meta, String resp) {
        return "<name:" + name +
                ";status:" + Boolean.toString(status) +
                ";req:" + req +
                ";meta:" + meta +
                ";resp:" + resp + ">";
    }

    private static HashMap<String, String> separateElements(String r) {
        HashMap<String, String> details = new HashMap<>();

        String s = r.substring(1, r.length() - 1);
        String[] elements = s.split(Pattern.quote(";"));

        int size = elements.length;
        for (int i = 0; i < size; i++) {
            String[] vals = elements[i].split(Pattern.quote(":"));
            details.put(vals[0], vals[1]);
        }

        return details;
    }

    public static String handleResponse(String response, String serverName, boolean serverRunning) {
        HashMap<String, String> responseData = separateElements(response);

        String resp = responseData.get("resp");
        String req = responseData.get("req");
        String reply = "";

        switch (req) {
            case " ":
                switch (resp) {
                    case "identify":
                        // Add device to some form of list...
                        String devName = "Unknown";
                        try {
                            devName = InetAddress.getLocalHost().getHostName();
                        } catch (UnknownHostException e) {
                            System.out.println("Error: " + e.toString());
                        }

                        reply = structureBuilder(serverName, serverRunning,
                                " ", devName, "identify");
                        break;
                    default:
                        // Empty message...
                        reply = structureBuilder(serverName, serverRunning,
                                " ", " "," ");
                        break;
                }
                break;
            case "update":
                reply = structureBuilder(serverName, serverRunning,
                        " ", " ", "update");
                break;
            default:
                break;
        }

        return reply;
    }

    public static String initialMessage(String serverName, boolean serverRunning) {
        return structureBuilder(serverName, serverRunning, " ", " ", " ");
    }
}

