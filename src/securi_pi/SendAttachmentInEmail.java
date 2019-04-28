/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package securi_pi;
import java.util.*;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.*;
import javax.naming.NamingException;
/**
 *
 * @author Ian and Carl
 */
public class SendAttachmentInEmail {
    
    public SendAttachmentInEmail() throws FileNotFoundException, IOException, NamingException { 
        
        final String username = "teamproject5cc518@gmail.com";
        final String password = "teamprojectsinitial";

        //Outlook account info
        /*
        Address: teamproject5cc518@outlook.com
        Password: T3amprojectinitial
        */
        String[] array = new String[1];
        //String[] returned;
        array[0] = "gmail.com";
        
        //MXRecords mxrecords;
        //mxrecords = new MXRecords(array);
     
        //returned = mxrecords.lookupMailHosts("gmail.com");      
        //String mxRecord = returned[0];
        //System.out.println(mxRecord);
        
        Properties props = new java.util.Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");  //"smtp.gmail.com"
        props.put("mail.smtp.port", "587");
        
        // Get the Session object.
        Session session;
        session = Session.getInstance(props,
        new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(username, password);
            }
        });
        
        Message msg = new MimeMessage(session);
        
        //List<String> addresses=new ArrayList<>(); 
        String[] addresses = new String[100];
        BufferedReader bufferedReader = new BufferedReader(new FileReader("/home/pi/Documents/myfile.txt"));
        String line = bufferedReader.readLine();
        
        int count = 0;
        while (line != null) {
            addresses[count] = line;
            line = bufferedReader.readLine();
            count++;
        }
        
        
        //The try/Catch block below needs to be encapsulated in a foreach loop to iterate through each email address
        for (String address : addresses)
        {
            if (address != null) {
                try {
                    System.out.println("You are here");
                    msg.setFrom(new InternetAddress("teamproject5cc518@gmail.com"));
                    
                    //The email address in the 'Recipients' can be replaced by the variable
                    //set out in the for each loop
                    
                    msg.setRecipient(Message.RecipientType.TO, new InternetAddress(address));
                    msg.setSubject("Motion Alert");
                    
                    Multipart multipart = new MimeMultipart();
                    MimeBodyPart textBodyPart = new MimeBodyPart();
                    textBodyPart.setText("Motion has taken place");
                    MimeBodyPart attachmentBodyPart= new MimeBodyPart();
                    
                    DataSource source = new FileDataSource("/home/pi/Pictures/motionalert.jpg");
                    attachmentBodyPart.setDataHandler(new DataHandler(source));
                    attachmentBodyPart.setFileName("A Cool Picture.jpg"); // ex : "test.pdf"
                    
                    multipart.addBodyPart(textBodyPart);  // add the text part
                    multipart.addBodyPart(attachmentBodyPart); // add the attachement part
                    
                    msg.setContent(multipart);
                    Transport.send(msg);
                    System.out.println("Sent message successfully....");
                    
                } catch (MessagingException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
/* https://jar-download.com/artifacts/com.sun.mail/javax.mail/1.6.2 */
