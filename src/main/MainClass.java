/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import userInterface.MainWindow;

/**
 *
 * @author Elias
 */
public class MainClass {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        MainWindow mw = new MainWindow(0, 3, 0, 2, 900, 600);
        mw.setVisible(true);     
    }
    
}
