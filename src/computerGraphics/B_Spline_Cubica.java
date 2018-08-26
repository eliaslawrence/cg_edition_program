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
public class B_Spline_Cubica extends Curve {//GraphicComponent {

    public B_Spline_Cubica() {
        super();
        super.type = "B_Spline_C";
        this.color = Color.BLACK;
    }

    @Override
    public void draw(userInterface.Canvas canvas) {
        if (selected) {
            drawSelected(canvas);
            return;
        }
        canvas.blockRepainting();
        Line line = new Line(canvas);
        line.setDelta(0.03);

        for (int i = 0; i <= numPoints; i++) {
            if (numPoints >= i + 3) {
                //p0
                double x0 = points.get(i).getX();
                double y0 = points.get(i).getY();
                double z0 = points.get(i).getZ();

                //p1
                double x1 = points.get(i + 1).getX();
                double y1 = points.get(i + 1).getY();
                double z1 = points.get(i + 1).getZ();

                //p2
                double x2 = points.get(i + 2).getX();
                double y2 = points.get(i + 2).getY();
                double z2 = points.get(i + 2).getZ();
                
                //p3
                double x3 = points.get(i + 3).getX();
                double y3 = points.get(i + 3).getY();
                double z3 = points.get(i + 3).getZ();

                double x, y, z, t;
                int max = (int) ((int) 1.0 / canvas.parameterDelta);
                for (int count = 0; count <= max; count++) {
                    Point pxl = new Point(canvas);
                    t = (double) count / (double) max;                   
                    x = (((1 - t) * (1 - t) * (1 - t)) * x0
                            + (3 * t * t * t - 6 * t * t + 4) * x1
                            + (-3 * t * t * t + 3 * t * t + 3 * t + 1) * x2
                            + (t * t * t) * x3)/6;
                    y = ((1 - t) * (1 - t) * (1 - t) * y0
                            + (3 * t * t * t - 6 * t * t + 4) * y1
                            + (-3 * t * t * t + 3 * t * t + 3 * t + 1) * y2
                            + (t * t * t) * y3)/6;
                    z = ((1 - t) * (1 - t) * (1 - t) * z0
                            + (3 * t * t * t - 6 * t * t + 4) * z1
                            + (-3 * t * t * t + 3 * t * t + 3 * t + 1) * z2
                            + (t * t * t) * z3)/6;
                    
                    pxl.setX(x);
                    pxl.setY(y);
                    pxl.setZ(z);
                    canvas.drawPixel(pxl, zcp, 0);
                }
            }
        }

        canvas.unblockRepainting();
        canvas.performRepainting();
    }

    private void drawSelected(Canvas canvas) {
        canvas.blockRepainting();
        Line line = new Line(canvas);
        line.setDelta(0.03);

        for (int i = 0; i <= numPoints; i++) {
            if (numPoints >= i + 3) {
                //p0
                double x0 = points.get(i).getX();
                double y0 = points.get(i).getY();
                double z0 = points.get(i).getZ();

                points.get(i).draw(canvas, zcp);

                line.setP0(points.get(i));

                //p1
                double x1 = points.get(i + 1).getX();
                double y1 = points.get(i + 1).getY();
                double z1 = points.get(i + 1).getZ();

                points.get(i + 1).draw(canvas, zcp);

                line.setP1(points.get(i + 1));

                line.draw(canvas, zcp, 0);

                line.setP0(points.get(i + 1));

                //p2
                double x2 = points.get(i + 2).getX();
                double y2 = points.get(i + 2).getY();
                double z2 = points.get(i + 2).getZ();

                points.get(i + 2).draw(canvas, zcp);

                line.setP1(points.get(i + 2));

                line.draw(canvas, zcp, 0);

                line.setP0(points.get(i + 2));

                //p3
                double x3 = points.get(i + 3).getX();
                double y3 = points.get(i + 3).getY();
                double z3 = points.get(i + 3).getZ();

                points.get(i + 3).draw(canvas, zcp);

                line.setP1(points.get(i + 3));

                line.draw(canvas, zcp, 0);

                double x, y, z, t;
                int max = (int) ((int) 1.0 / canvas.parameterDelta);
                for (int count = 0; count <= max; count++) {
                    Point pxl = new Point(canvas);
                    t = (double) count / (double) max;
                    x = (((1 - t) * (1 - t) * (1 - t)) * x0
                            + (3 * t * t * t - 6 * t * t + 4) * x1
                            + (-3 * t * t * t + 3 * t * t + 3 * t + 1) * x2
                            + (t * t * t) * x3)/6;
                    y = ((1 - t) * (1 - t) * (1 - t) * y0
                            + (3 * t * t * t - 6 * t * t + 4) * y1
                            + (-3 * t * t * t + 3 * t * t + 3 * t + 1) * y2
                            + (t * t * t) * y3)/6;
                    z = ((1 - t) * (1 - t) * (1 - t) * z0
                            + (3 * t * t * t - 6 * t * t + 4) * z1
                            + (-3 * t * t * t + 3 * t * t + 3 * t + 1) * z2
                            + (t * t * t) * z3)/6;
                    pxl.setX(x);
                    pxl.setY(y);
                    pxl.setZ(z);
                    canvas.drawPixel(pxl, zcp, 0);
                }
            }
        }

        canvas.unblockRepainting();
        canvas.performRepainting();
    }
    
    @Override
    public void interpolatePoints() {     
        interpolatedPoints.clear();
        
        for (int i = 0; i <= numPoints; i++) {
            if (numPoints >= i + 3) {
                //p0
                double x0 = points.get(i).getX();
                double y0 = points.get(i).getY();
                double z0 = points.get(i).getZ();

                //p1
                double x1 = points.get(i + 1).getX();
                double y1 = points.get(i + 1).getY();
                double z1 = points.get(i + 1).getZ();

                //p2
                double x2 = points.get(i + 2).getX();
                double y2 = points.get(i + 2).getY();
                double z2 = points.get(i + 2).getZ();

                //p3
                double x3 = points.get(i + 3).getX();
                double y3 = points.get(i + 3).getY();
                double z3 = points.get(i + 3).getZ();

                double x, y, z, t;
                int max = (int) ((int) 1.0 / resolution);
                for (int count = 0; count <= max; count++) {
                    Point pxl = new Point(canvas);
                    t = (double) count / (double) max;
                    x = (((1 - t) * (1 - t) * (1 - t)) * x0
                            + (3 * t * t * t - 6 * t * t + 4) * x1
                            + (-3 * t * t * t + 3 * t * t + 3 * t + 1) * x2
                            + (t * t * t) * x3)/6;
                    y = ((1 - t) * (1 - t) * (1 - t) * y0
                            + (3 * t * t * t - 6 * t * t + 4) * y1
                            + (-3 * t * t * t + 3 * t * t + 3 * t + 1) * y2
                            + (t * t * t) * y3)/6;
                    z = ((1 - t) * (1 - t) * (1 - t) * z0
                            + (3 * t * t * t - 6 * t * t + 4) * z1
                            + (-3 * t * t * t + 3 * t * t + 3 * t + 1) * z2
                            + (t * t * t) * z3)/6;
                    
                    pxl.setX(x);
                    pxl.setY(y);
                    pxl.setZ(z);

                    interpolatedPoints.add(pxl);
                }
            }
        }
    }
}
