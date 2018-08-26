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
public class B_Spline_Quadratica extends Curve {//GraphicComponent {

    public B_Spline_Quadratica() {
        super();
        super.type = "B_Spline_Q";
        this.color = Color.BLACK;
    }

    @Override
    public void draw(userInterface.Canvas canvas) {
        interpolatedPoints.clear();
        if (selected) {
            drawSelected(canvas);
            //interpolatePoints();
            return;
        }
        canvas.blockRepainting();
        Line line = new Line(canvas);
        line.setDelta(0.03);
        //Double distance = greaterDistance() / 10;
        //double zcp = 40 * distance;

        for (int i = 0; i <= numPoints; i++) {
            if (numPoints >= i + 2) {
                //p0
                double x0 = points.get(i).getX();
                double y0 = points.get(i).getY();
                double z0 = points.get(i).getZ();

                line.setP0(points.get(i));

                //p1
                double x1 = points.get(i + 1).getX();
                double y1 = points.get(i + 1).getY();
                double z1 = points.get(i + 1).getZ();

                line.setP1(points.get(i + 1));

                line.setP0(points.get(i + 1));

                //p2
                double x2 = points.get(i + 2).getX();
                double y2 = points.get(i + 2).getY();
                double z2 = points.get(i + 2).getZ();

                line.setP1(points.get(i + 2));

                line.setP0(points.get(i + 2));

                double x, y, z, t, countInt = resolution;
                int max = (int) ((int) 1.0 / canvas.parameterDelta);
                for (int count = 0; count <= max; count++) {
                    Point pxl = new Point(canvas);
                    t = (double) count / (double) max;
                    x = ((t * t - 2 * t + 1) * x0
                            + (-2 * t * t + 2 * t + 1) * x1
                            + (t * t) * x2) / 2;
                    y = ((t * t - 2 * t + 1) * y0
                            + (-2 * t * t + 2 * t + 1) * y1
                            + (t * t) * y2) / 2;
                    z = ((t * t - 2 * t + 1) * z0
                            + (-2 * t * t + 2 * t + 1) * z1
                            + (t * t) * z2) / 2;
                    pxl.setX(x);
                    pxl.setY(y);
                    pxl.setZ(z);
                    canvas.drawPixel(pxl, zcp, 0);
                    if (countInt >= resolution) {                        
                        interpolatedPoints.add(pxl);
                        countInt = 0;
                    }
                    countInt += canvas.parameterDelta;
                }
            }
        }

        //interpolatePoints();
        canvas.unblockRepainting();
        canvas.performRepainting();
    }

    @Override
    public void interpolatePoints() {        
        //Double distance = greaterDistance() / 10;
        //double zcp = 40 * distance;
        interpolatedPoints.clear();
        for (int i = 0; i <= numPoints; i++) {
            if (numPoints >= i + 2) {
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

                double x, y, z, t;
                int max = (int) ((int) 1.0 / resolution);
                for (int count = 0; count <= max; count++) {
                    Point pxl = new Point(canvas);
                    t = (double) count / (double) max;
                    x = ((t * t - 2 * t + 1) * x0
                            + (-2 * t * t + 2 * t + 1) * x1
                            + (t * t) * x2) / 2;
                    y = ((t * t - 2 * t + 1) * y0
                            + (-2 * t * t + 2 * t + 1) * y1
                            + (t * t) * y2) / 2;
                    z = ((t * t - 2 * t + 1) * z0
                            + (-2 * t * t + 2 * t + 1) * z1
                            + (t * t) * z2) / 2;
                    pxl.setX(x);
                    pxl.setY(y);
                    pxl.setZ(z);

                    interpolatedPoints.add(pxl);

                }
            }
        }
    }

    private void drawSelected(Canvas canvas) {
        canvas.blockRepainting();
        Line line = new Line(canvas);
        line.setDelta(0.03);
        //Double distance = greaterDistance() / 10;
        //double zcp = 40 * distance;

        for (int i = 0; i <= numPoints; i++) {
            if (numPoints >= i + 2) {
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

                double x, y, z, t, countInt = resolution;
                int max = (int) ((int) 1.0 / canvas.parameterDelta);
                for (int count = 0; count <= max; count++) {
                    Point pxl = new Point(canvas);
                    t = (double) count / (double) max;
                    x = ((t * t - 2 * t + 1) * x0
                            + (-2 * t * t + 2 * t + 1) * x1
                            + (t * t) * x2) / 2;
                    y = ((t * t - 2 * t + 1) * y0
                            + (-2 * t * t + 2 * t + 1) * y1
                            + (t * t) * y2) / 2;
                    z = ((t * t - 2 * t + 1) * z0
                            + (-2 * t * t + 2 * t + 1) * z1
                            + (t * t) * z2) / 2;
                    pxl.setX(x);
                    pxl.setY(y);
                    pxl.setZ(z);
                    canvas.drawPixel(pxl, zcp, 0);
                    if (countInt >= resolution) {                        
                        interpolatedPoints.add(pxl);
                        countInt = 0;
                    }
                    countInt += canvas.parameterDelta;
                }
            }
        }

        canvas.unblockRepainting();
        canvas.performRepainting();
    }    
}
