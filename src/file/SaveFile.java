/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package file;

import computerGraphics.GraphicComponent;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import userInterface.Canvas;

/**
 *
 * @author Elias
 */
public class SaveFile {

    @SuppressWarnings("ConvertToTryWithResources")
    public static void save(Canvas canvas, String fileName) {
        try {
            FileOutputStream fo = new FileOutputStream(fileName);
            ObjectOutputStream oo = new ObjectOutputStream(fo);
            oo.writeObject(canvas.getGraphicComponents()); // serializo objeto cat            
            oo.close();

            PrintWriter writer = new PrintWriter("window.config", "UTF-8");                        
            writer.println(canvas.getxMin());
            writer.println(canvas.getyMin());
            writer.println(canvas.getxMax());
            writer.println(canvas.getyMax());
            writer.println(canvas.getWidth());
            writer.println(canvas.getHeight());
            writer.close();                       

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
