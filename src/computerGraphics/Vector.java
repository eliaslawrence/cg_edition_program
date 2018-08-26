/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package computerGraphics;

import static Utils.Util.*;

/**
 *
 * @author Elias
 */
public class Vector {
    private double dx;
    private double dy;
    private double dz;
    
    public Vector(){
    }
    
    public Vector(double dx, double dy, double dz){
        this.dx = dx;
        this.dy = dy;
        this.dz = dz;
    }
    
    public double cosBetween(double i, double j, double k){
        double prodInt = dx * i + dy * j + dz * k;
        
        return prodInt / ((getModule())*(module(i, j, k)));
    }

    public void setDx(double dx) {
        this.dx = dx;
    }

    public void setDy(double dy) {
        this.dy = dy;
    }

    public void setDz(double dz) {
        this.dz = dz;
    }
    
    public double getModule(){        
        return module(dx, dy, dz);
    }
}
