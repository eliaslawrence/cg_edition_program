/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import computerGraphics.*;

/**
 *
 * @author Elias
 */
public class Util {
    
  public static double module(double i, double j, double k){
      return Math.sqrt(i*i + j*j + k*k);
  }
  
  public static Vector VectorialProduct(Point p0, Point p1, Point p2){
      Vector resultant = new Vector();
      
      double a1 = p1.getX()- p0.getX();
      double b1 = p2.getX()- p0.getX();
      
      double a2 = p1.getY()- p0.getY();
      double b2 = p2.getY()- p0.getY();
      
      double a3 = p1.getZ()- p0.getZ();
      double b3 = p2.getZ()- p0.getZ();
      
      resultant.setDx(a2*b3 - a3*b2);
      resultant.setDy(a3*b1 - a1*b3);
      resultant.setDz(a1*b2 - a2*b1);
      
      return resultant;      
  }
  
}
