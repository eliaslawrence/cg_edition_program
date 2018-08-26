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
public class Line extends GraphicComponent {

    private Point p0;
    private Point p1;
    private double delta = 0.001;
    private boolean ready = false;

    public Line(Canvas canvas) {
        super();
        p0 = new Point(0, 0);
        p1 = new Point(0, 0);

        this.canvas = canvas;

        super.type = "Line";
        this.color = Color.BLACK;
    }

    public Line(Point p0, Point p1) {
        super();
        super.type = "Line";
        this.p0 = p0;
        this.p1 = p1;
        this.color = Color.BLACK;
    }

    public Point getP0() {
        return p0;
    }

    public Point getP1() {
        return p1;
    }

    public double getDelta() {
        return delta;
    }

    public void setP0(Point p0) {
        this.p0 = p0;
        p0.setCanvas(canvas);
    }

    public void setP1(Point p1) {
        this.p1 = p1;
        ready = true;
        p1.setCanvas(canvas);
    }

    public void setDelta(double delta) {
        this.delta = delta;
    }

    @Override
    public void setSelected(boolean selected) {
        if (!selected) {
            this.selected = p0.selected = p1.selected = selected;
        } else {
            this.selected = true;
        }
    }

    public void draw(userInterface.Canvas canvas, double zcp, double distance) {
        canvas.blockRepainting();
        
        if (selected) {
            p0.setColor(color);
            p0.draw(canvas, zcp);

            p1.setColor(color);
            p1.draw(canvas, zcp);
        }
        if (ready) {
            double x, y, z;
            Point pxl = new Point(canvas);
            pxl.setColor(color);

            for (double i = 0; i <= 1; i += delta) {
                x = (p0.getX() + (p1.getX() - p0.getX()) * i);
                y = (p0.getY() + (p1.getY() - p0.getY()) * i);
                z = (p0.getZ() + (p1.getZ() - p0.getZ()) * i);

                pxl.setX(x);
                pxl.setY(y);
                pxl.setZ(z);

                canvas.drawPixel(pxl, zcp, distance);
            }
        } else {
            p0.draw(canvas, zcp);
        }

        canvas.unblockRepainting();
        canvas.performRepainting();
    }

    @Override
    public void draw(userInterface.Canvas canvas) {
        canvas.blockRepainting();
        if (selected) {
            p0.setColor(color);
            p0.draw(canvas);

            p1.setColor(color);
            p1.draw(canvas);
        }
        if (ready) {
            double x, y;
            Point pxl = new Point(canvas);
            pxl.setColor(color);

            for (double i = 0; i <= 1; i += delta) {
                x = (p0.getX() + (p1.getX() - p0.getX()) * i);
                y = (p0.getY() + (p1.getY() - p0.getY()) * i);
                pxl.setX(x);
                pxl.setY(y);

                canvas.drawPixel(pxl);
            }
        } else {
            p0.draw(canvas);
        }

        canvas.unblockRepainting();
        canvas.performRepainting();
    }

    @Override
    public boolean clicked(int x, int y) {
        if (selected) {
            boolean clickedP0 = p0.clicked(x, y);
            boolean clickedP1 = p1.clicked(x, y);
            return clickedP0 || clickedP1;
        } else {
            return false;
        }
    }

    @Override
    public void dragged(int x, int y) {
        p0.dragged(x, y);
        p1.dragged(x, y);
    }
}
