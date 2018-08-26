/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package computerGraphics;

import java.awt.Color;
import userInterface.Canvas;

/**
 *
 * @author Elias
 */
public class Circle extends GraphicComponent {

    private double x;
    private double y;
    private double radius = .05;
    private boolean painted;
    private boolean filled = false;

    public static final Color SELECTED_COLOR = Color.red;

    public Circle() {
        super();
        super.type = "Circle";
        this.x = 0;
        this.y = 0;
        this.color = Color.BLACK;
        this.painted = false;
    }

    public Circle(double x, double y) {
        super();
        super.type = "Circle";
        this.x = x;
        this.y = y;
        this.color = Color.BLACK;
        this.painted = false;
    }

    public Circle(int x, int y, Color color) {
        super();
        this.x = x;
        this.y = y;
        this.color = color;
        this.painted = false;
    }

    @Override
    public String toString() {
        return "(" + this.x + "," + this.y + ") " + this.color + "circle";
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getRadius() {
        return radius;
    }

    public boolean isPainted() {
        return painted;
    }

    public boolean isFilled() {
        return filled;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public void setPainted(boolean painted) {
        this.painted = painted;
    }

    public void setFilled(boolean filled) {
        this.filled = filled;
    }

    @Override
    public void draw(Canvas canvas) {
        Color drawColor = color;
        if (selected) {
            drawColor = SELECTED_COLOR;                       
        }
        canvas.blockRepainting();

        //int screenX = realXtoViewPort(x0);
        //int screenY = realYtoViewPort(y0);
        double x, y;
        Point pixel = new Point(canvas);
        pixel.setColor(drawColor);

        for (double i = 0; i < 90; i += 0.5) {
            //
            x = (Math.cos(i * Math.PI / 180) * radius + this.x);
            y = (Math.sin(i * Math.PI / 180) * radius + this.y);
            pixel.setY(y);
            pixel.setX(x);
            canvas.drawPixel(pixel);
            //
            double xTemp = x;
            x = 2 * this.x - x;
            pixel.setX(x);
            canvas.drawPixel(pixel);
            //
            double yTemp = y;
            y = 2 * this.y - y;
            pixel.setY(y);
            canvas.drawPixel(pixel);
            //
            x = xTemp;
            pixel.setX(x);
            canvas.drawPixel(pixel);
            //
        }
        canvas.unblockRepainting();
        canvas.performRepainting();
    }

    @Override
    public boolean clicked(int x, int y) {
        selected = Math.abs(canvas.viewPortXtoReal(x) - this.x) <= radius
                && Math.abs(canvas.viewPortYtoReal(y) - this.y) <= radius;
        return selected;
    }

    @Override
    public void dragged(int x, int y) {
        if (selected) {
            this.x = canvas.viewPortXtoReal(x);
            this.y = canvas.viewPortYtoReal(y);
        }
    }

}
