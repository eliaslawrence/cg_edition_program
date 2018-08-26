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
public class Hermite extends Curve {

    private boolean painted;

    public Hermite() {
        super();
        super.type = "Hermite";
        this.color = Color.BLACK;
        this.painted = false;
    }

    public boolean isPainted() {
        return painted;
    }

    public void setPainted(boolean painted) {
        this.painted = painted;
    }

    @Override
    public void setSelected(boolean selected) {
        this.selected = selected;
        if (!selected) {
            points.stream().forEach((point) -> {
                point.setSelected(selected);
            });
        }
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

        for (int i = 0; i <= numPoints; i += 2) {
            if (numPoints >= i + 3) {
                //p1
                double x1 = points.get(i).getX();
                double y1 = points.get(i).getY();
                double z1 = points.get(i).getZ();

                //p0
                double x0 = points.get(i + 1).getX();
                double y0 = points.get(i + 1).getY();
                double z0 = points.get(i + 1).getZ();

                //p3
                double x3 = points.get(i + 2).getX();
                double y3 = points.get(i + 2).getY();
                double z3 = points.get(i + 2).getZ();

                //p2
                double x2 = points.get(i + 3).getX();
                double y2 = points.get(i + 3).getY();
                double z2 = points.get(i + 3).getZ();

                double x, y, z, t;
                int max = (int) ((int) 1.0 / canvas.parameterDelta);
                for (int count = 0; count <= max; count++) {
                    Point pxl = new Point(canvas);
                    t = (double) count / (double) max;
                    x = ((2 * t * t * t - 3 * t * t + 1) * x0
                            + (t * t * t - 2 * t * t + t) * (x1 - x0)
                            + (-2 * t * t * t + 3 * t * t) * x2
                            + (t * t * t - t * t) * (x3 - x2));
                    y = ((2 * t * t * t - 3 * t * t + 1) * y0
                            + (t * t * t - 2 * t * t + t) * (y1 - y0)
                            + (-2 * t * t * t + 3 * t * t) * y2
                            + (t * t * t - t * t) * (y3 - y2));
                    z = ((2 * t * t * t - 3 * t * t + 1) * z0
                            + (t * t * t - 2 * t * t + t) * (z1 - z0)
                            + (-2 * t * t * t + 3 * t * t) * z2
                            + (t * t * t - t * t) * (z3 - z2));
                    
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

        for (int i = 0; i <= numPoints; i += 2) {
            if (numPoints >= i + 3) {
                //p1
                double x1 = points.get(i).getX();
                double y1 = points.get(i).getY();
                double z1 = points.get(i).getZ();

                points.get(i).draw(canvas, zcp);

                line.setP0(points.get(i));

                //p0
                double x0 = points.get(i + 1).getX();
                double y0 = points.get(i + 1).getY();
                double z0 = points.get(i + 1).getZ();

                points.get(i + 1).draw(canvas, zcp);

                line.setP1(points.get(i + 1));

                line.draw(canvas, zcp, 0);

                //p3
                double x3 = points.get(i + 2).getX();
                double y3 = points.get(i + 2).getY();
                double z3 = points.get(i + 2).getZ();

                points.get(i + 2).draw(canvas, zcp);

                line.setP0(points.get(i + 2));

                //p2
                double x2 = points.get(i + 3).getX();
                double y2 = points.get(i + 3).getY();
                double z2 = points.get(i + 3).getZ();

                points.get(i + 3).draw(canvas, zcp);

                line.setP1(points.get(i + 3));

                line.draw(canvas, zcp, 0);

                double x, y, z, t;
                int max = (int) ((int) 1.0 / canvas.parameterDelta);
                for (int count = 0; count <= max; count++) {
                    Point pxl = new Point(canvas);
                    t = (double) count / (double) max;
                    x = ((2 * t * t * t - 3 * t * t + 1) * x0
                            + (t * t * t - 2 * t * t + t) * (x1 - x0)
                            + (-2 * t * t * t + 3 * t * t) * x2
                            + (t * t * t - t * t) * (x3 - x2));
                    y = ((2 * t * t * t - 3 * t * t + 1) * y0
                            + (t * t * t - 2 * t * t + t) * (y1 - y0)
                            + (-2 * t * t * t + 3 * t * t) * y2
                            + (t * t * t - t * t) * (y3 - y2));
                    z = ((2 * t * t * t - 3 * t * t + 1) * z0
                            + (t * t * t - 2 * t * t + t) * (z1 - z0)
                            + (-2 * t * t * t + 3 * t * t) * z2
                            + (t * t * t - t * t) * (z3 - z2));
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
        //Double distance = greaterDistance() / 10;
        //double zcp = 40 * distance;
        interpolatedPoints.clear();
        for (int i = 0; i <= numPoints; i += 2) {
            if (numPoints >= i + 3) {
                //p1
                double x1 = points.get(i).getX();
                double y1 = points.get(i).getY();
                double z1 = points.get(i).getZ();

                //p0
                double x0 = points.get(i + 1).getX();
                double y0 = points.get(i + 1).getY();
                double z0 = points.get(i + 1).getZ();

                //p3
                double x3 = points.get(i + 2).getX();
                double y3 = points.get(i + 2).getY();
                double z3 = points.get(i + 2).getZ();

                //p2
                double x2 = points.get(i + 3).getX();
                double y2 = points.get(i + 3).getY();
                double z2 = points.get(i + 3).getZ();

                double x, y, z, t;
                int max = (int) ((int) 1.0 / resolution);
                for (int count = 0; count <= max; count++) {
                    Point pxl = new Point(canvas);
                    t = (double) count / (double) max;
                    x = ((2 * t * t * t - 3 * t * t + 1) * x0
                            + (t * t * t - 2 * t * t + t) * (x1 - x0)
                            + (-2 * t * t * t + 3 * t * t) * x2
                            + (t * t * t - t * t) * (x3 - x2));
                    y = ((2 * t * t * t - 3 * t * t + 1) * y0
                            + (t * t * t - 2 * t * t + t) * (y1 - y0)
                            + (-2 * t * t * t + 3 * t * t) * y2
                            + (t * t * t - t * t) * (y3 - y2));
                    z = ((2 * t * t * t - 3 * t * t + 1) * z0
                            + (t * t * t - 2 * t * t + t) * (z1 - z0)
                            + (-2 * t * t * t + 3 * t * t) * z2
                            + (t * t * t - t * t) * (z3 - z2));
                    
                    pxl.setX(x);
                    pxl.setY(y);
                    pxl.setZ(z);

                    interpolatedPoints.add(pxl);
                }
            }
        }
    }
}
