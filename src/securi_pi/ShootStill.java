/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package securi_pi;

import com.hopding.jrpicam.RPiCamera;
import com.hopding.jrpicam.enums.AWB;
import com.hopding.jrpicam.enums.DRC;
import com.hopding.jrpicam.enums.Encoding;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author pi
 */
public class ShootStill {
    
    public static void shootStill(RPiCamera piCamera) {
        
        piCamera.setAWB(AWB.AUTO) 	    // Change Automatic White Balance setting to automatic
                .setDRC(DRC.OFF)			// Turn off Dynamic Range Compression
                .setContrast(25) 			// Set maximum contrast
                .setSharpness(100)		    // Set maximum sharpness
                .setQuality(100) 		    // Set maximum quality
                .setTimeout(1000)		    // Wait 1 second to take the image
                .turnOnPreview()            // Turn on image preview
                .setEncoding(Encoding.JPG); // Change encoding of images to PNG
        // Take a 650x650 still image and save it as "/home/pi/Pictures/A Cool Picture.jpg"
        try {
                File image = piCamera.takeStill("A Cool Picture.jpg",1920, 1080);
                System.out.println("New JPG image saved to:\n\t" + image.getAbsolutePath());
        } catch (IOException | InterruptedException e) {
        }
    }	
}
