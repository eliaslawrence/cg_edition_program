/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package computerGraphics;

import java.awt.Color;
import userInterface.Canvas;
import static Utils.Util.*;
import lighting.LightController;

/**
 *
 * @author Elias
 */
public class Triangle extends GraphicComponent {

    private Point p0;
    private Point p1;
    private Point p2;

    public Vector normal;

    private double centerX;
    private double centerY;
    private double centerZ;

    public Color reverseColor = Color.GREEN;

    private final double RESOLUTION = 0.01;
    private double delta = 0.01;    

    public Triangle(Canvas canvas) {
        super();
        p0 = new Point(0, 0);
        p1 = new Point(0, 0);
        p2 = new Point(0, 0);

        this.canvas = canvas;

        super.type = "Triangle";
        this.color = Color.BLUE;
    }

    public void setP0(Point p0) {
        this.p0 = p0;
        p0.setCanvas(canvas);
    }

    public void setP1(Point p1) {
        this.p1 = p1;
        p1.setCanvas(canvas);
    }

    public void setP2(Point p2) {
        this.p2 = p2;
        p2.setCanvas(canvas);
    }

    public void setNormal() {
        this.normal = VectorialProduct(p0, p1, p2);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.blockRepainting();

        Line line = new Line(canvas);
        line.setDelta(RESOLUTION);
        line.setColor(color);

        line.setP0(p0);
        line.setP1(p1);
        line.draw(canvas);

        line.setP0(p1);
        line.setP1(p2);
        line.draw(canvas);

        line.setP0(p2);
        line.setP1(p0);
        line.draw(canvas);

        canvas.unblockRepainting();
        canvas.performRepainting();
    }

    public void drawFilled(Canvas canvas, double zcp) {
        canvas.blockRepainting();
        double[] rgbFactors = new double[3];
        
        rgbFactors[0] = LightController.intensity * LightController.color.getRed() / 255.0;
        rgbFactors[1] = LightController.intensity * LightController.color.getGreen() / 255.0;
        rgbFactors[2] = LightController.intensity * LightController.color.getBlue() / 255.0;
        
        double cos = normal.cosBetween(1, -1, 0);
        double dirFactor = (cos + 1.0) / 2;        

        rgbFactors[0] += LightController.intensity * dirFactor * LightController.color.getRed() / 255.0;
        rgbFactors[1] += LightController.intensity * dirFactor * LightController.color.getGreen() / 255.0;
        rgbFactors[2] += LightController.intensity * dirFactor * LightController.color.getBlue() / 255.0;

        rgbFactors[0] = rgbFactors[0] > 1.0 ? 1.0 : rgbFactors[0];
        rgbFactors[1] = rgbFactors[1] > 1.0 ? 1.0 : rgbFactors[1];
        rgbFactors[2] = rgbFactors[2] > 1.0 ? 1.0 : rgbFactors[2];

        color = new Color(
                (int) (color.getRed() * rgbFactors[0]),
                (int) (color.getGreen() * rgbFactors[1]),
                (int) (color.getBlue() * rgbFactors[2]));

        double x1, y1, z1, x2, y2, z2;
        Point pxl1 = new Point(canvas);
        Point pxl2 = new Point(canvas);

        for (double i = 0;
                i <= 1; i += delta) {
            x1 = (p0.getX() + (p1.getX() - p0.getX()) * i);
            y1 = (p0.getY() + (p1.getY() - p0.getY()) * i);
            z1 = (p0.getZ() + (p1.getZ() - p0.getZ()) * i);

            pxl1.setX(x1);
            pxl1.setY(y1);
            pxl1.setZ(z1);

            x2 = (p0.getX() + (p2.getX() - p0.getX()) * i);
            y2 = (p0.getY() + (p2.getY() - p0.getY()) * i);
            z2 = (p0.getZ() + (p2.getZ() - p0.getZ()) * i);

            pxl2.setX(x2);
            pxl2.setY(y2);
            pxl2.setZ(z2);

            //Line line = new Line(canvas);
            //line.setDelta(RESOLUTION);
            //line.setColor(color);
            //line.setP0(pxl1);
            //line.setP1(pxl2);
            //line.draw(canvas, zcp, Math.abs(getCenterZ() - zcp));
            canvas.drawLine(pxl1, pxl2, zcp, Math.abs(getCenterZ() - zcp), color, RESOLUTION);
        }

        canvas.unblockRepainting();
        canvas.performRepainting();
    }
    
    public void draw(Canvas canvas, double zcp){
        canvas.blockRepainting();
        
        Line line = new Line(canvas);
        line.setDelta(RESOLUTION);
        line.setColor(color);

        line.setP0(p0);
        line.setP1(p1);
        line.draw(canvas, zcp, Math.abs(getCenterZ() - zcp));

        line.setP0(p1);
        line.setP1(p2);
        line.draw(canvas, zcp, Math.abs(getCenterZ() - zcp));
        
        line.setP0(p2);
        line.setP1(p0);
        line.draw(canvas, zcp, Math.abs(getCenterZ() - zcp));
        
        canvas.unblockRepainting();
        canvas.performRepainting();
    }

    private double getDistance() {
        return centerZ;
    }

    public double getCenterX() {
        return centerX;
    }

    public double getCenterY() {
        return centerY;
    }

    public double getCenterZ() {
        return centerZ;
    }

    @Override
    public boolean clicked(int x, int y) {
        return false;
    }

    @Override
    public void dragged(int x, int y) {

    }

    @Override
    public void setCenter() {
        this.centerX = (p0.getX() + p1.getX() + p2.getX()) / 3;
        this.centerY = (p0.getY() + p1.getY() + p2.getY()) / 3;
        this.centerZ = (p0.getZ() + p1.getZ() + p2.getZ()) / 3;
    }
}
