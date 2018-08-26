/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package computerGraphics;

import java.util.ArrayList;
import computerGraphics.Point;
import java.awt.Color;

/**
 *
 * @author Elias
 */
public abstract class Curve extends GraphicComponent {

    protected ArrayList<Point> points = new ArrayList<>();
    protected ArrayList<Point> interpolatedPoints = new ArrayList<>();

    protected int numPoints = -1;
    protected double resolution = 0.1;
    protected double zcp = 0;

    private final int FACTOR_CP = 40;

    public Curve() {

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

    public void setZcp(double zcp) {
        this.zcp = zcp;
    }

    public int getNumPoints() {
        return numPoints;
    }

    public ArrayList<Point> getPoints() {
        return points;
    }

    public void addPoint(Point p) {
        points.add(p);
        numPoints++;
        setZcp(greaterDistance() * FACTOR_CP);
    }

    public void removePoint(int i) {
        points.remove(i);
        numPoints--;
    }

    public void clearPoints() {
        points.clear();
        numPoints = -1;
    }

    public double greaterDistance() {
        double greaterDistance = 0;
        double actualDistance = 0;
        for (int i = 0; i < points.size(); i++) {
            for (int j = i + 1; j < points.size(); j++) {
                actualDistance = Point.distanceBetweenPoints(points.get(i), points.get(j));

                if (actualDistance > greaterDistance) {
                    greaterDistance = actualDistance;
                }
            }
        }

        return greaterDistance;
    }

    public Point lefterPoint() {
        Point lefterPoint = null;
        Point actualPoint = null;
        if (interpolatedPoints.size() > 0) {
            lefterPoint = interpolatedPoints.get(0);
            for (int i = 0; i < interpolatedPoints.size(); i++) {
                actualPoint = interpolatedPoints.get(i);

                if (actualPoint.getX() < lefterPoint.getX()) {
                    lefterPoint = actualPoint;
                }
            }
        }

        return lefterPoint;
    }

    @Override
    public boolean clicked(int x, int y) {
        if (selected) {
            points.stream().forEach((p) -> {
                p.clicked(x, y);
            });
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void dragged(int x, int y) {
        points.stream().forEach((point) -> {
            point.dragged(x, y);
        });
    }

    public double getCenterX() {
        double centerX = 0;
        for (int i = 0; i < interpolatedPoints.size(); i++) {
            centerX += interpolatedPoints.get(i).getX();
        }
        return centerX / interpolatedPoints.size();
    }

    public double getCenterY() {
        double centerY = 0;
        for (int i = 0; i < interpolatedPoints.size(); i++) {
            centerY += interpolatedPoints.get(i).getX();
        }
        return centerY / interpolatedPoints.size();
    }

    public double getCenterZ() {
        double centerZ = 0;
        for (int i = 0; i < interpolatedPoints.size(); i++) {
            centerZ += interpolatedPoints.get(i).getZ();
        }
        return centerZ / interpolatedPoints.size();
    }

    public void generateSurface(Enum type, double value) {
        Surface surface = new Surface(this, canvas, type, value);
        canvas.addComponent(surface);       
    }

    public abstract void interpolatePoints();

    @Override
    public void translate(double dx, double dy) {        
        for (Point vertex : points) {
            vertex.setX(vertex.getX() - dx);
            vertex.setY(vertex.getY() - dy);
        }
    }

    @Override
    public void rotate(double dx, double dy) {      
        interpolatePoints();
        points = Transformations.rotateCurveX(points, getCenterX(), getCenterY(), getCenterZ(), -dy);
        points = Transformations.rotateCurveY(points, getCenterX(), getCenterY(), getCenterZ(), -dx);
    }
}
