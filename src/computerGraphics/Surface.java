/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package computerGraphics;

import Enums.SurfaceGenerationEnum;
import java.awt.Color;
import java.util.ArrayList;
import javafx.scene.transform.Transform;
import javax.swing.JOptionPane;
import userInterface.Canvas;

/**
 *
 * @author Elias
 */
public class Surface extends GraphicComponent {

    private final ArrayList<ArrayList<Point>> VERTICES = new ArrayList<>();
    private final ArrayList<Triangle> PLANS = new ArrayList<>();

    private final int NUM_POINTS;

    private int numImpressions = 0;
    private int numVertices = 0;
    private int numLines = 0;
    private int numLayers = 0;
    private int numPointsPerLayer = 0;

    private final double ZCP;
    private final double DISTANCE;

    private final double ANGLE;
    private final double DELTA_ROTATION = 5;

    private double centerX;
    private double centerY;
    private double centerZ;

    public static final Color SELECTED_COLOR = Color.red;

    public Surface(Curve curve, Canvas canvas, Enum type, double value) {
        curve.interpolatePoints();
        this.VERTICES.add(curve.interpolatedPoints);
        this.NUM_POINTS = curve.interpolatedPoints.size();

        //ZCP        
        this.ZCP = curve.zcp;

        //DISTANCE
        this.DISTANCE = curve.greaterDistance() * value;

        //ROTATION
        this.ANGLE = value;

        //generated curve  
        if (type == SurfaceGenerationEnum.Rotation) {
            rotation(curve);
        } else {
            translation(curve);
        }

        super.type = "TS_BSQ";
    }

    @Override
    public void setSelected(boolean selected) {
        if (selected) {
            this.setColor(Color.orange);
        } else {
            this.setColor(Color.black);
        }
        this.selected = selected;
    }

    private void translation(Curve curve) {
        ArrayList<Point> generatedPoints = new ArrayList<>();

        Point pxl;
        for (Point p : curve.interpolatedPoints) {
            double[][] m = {{p.getX()}, {p.getY()}, {p.getZ()}, {1}};
            double[][] newM = Transformations.translate(0, 0, DISTANCE, m);

            pxl = new Point(newM[0][0], newM[1][0], newM[2][0]);
            generatedPoints.add(pxl);
            numVertices++;
        }
        numVertices *= 2;
        numLines = (numVertices / 2 - 1) * 3 + numVertices / 2;
        VERTICES.add(generatedPoints);
    }

    private void rotation(Curve curve) {
        Point center = curve.lefterPoint();

        for (double i = 0; i <= ANGLE && i < 360; i += DELTA_ROTATION) {
            ArrayList<Point> generatedPoints = Transformations.rotateCurveY(curve.interpolatedPoints, center.getX(), center.getY(), center.getZ(), Math.toRadians(i));            
            numVertices = numVertices + generatedPoints.size() - 1;
            VERTICES.add(generatedPoints);
        }
        numLayers = VERTICES.size();
        numPointsPerLayer = VERTICES.get(0).size();
        numVertices += 1;
        numLines = (numLayers - 1) * ((numPointsPerLayer - 2) * 3 + 2) + numPointsPerLayer - 1;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.blockRepainting();

        //generateCurve(selected);
//        this.generatedCurve.interpolatePoints();
//        this.curve.interpolatePoints();        
        PLANS.clear();

        for (int j = 0; j < VERTICES.size() - 1; j++) {
            for (int i = 0; i < NUM_POINTS - 1; i++) {
                Triangle triangle1 = new Triangle(canvas);
                triangle1.setP0(VERTICES.get(j).get(i));
                triangle1.setP1(VERTICES.get(j + 1).get(i));
                triangle1.setP2(VERTICES.get(j + 1).get(i + 1));
                triangle1.setNormal();
                triangle1.setCenter();
                PLANS.add(triangle1);

                Triangle triangle2 = new Triangle(canvas);
                triangle2.setP0(VERTICES.get(j).get(i));
                triangle2.setP1(VERTICES.get(j + 1).get(i + 1));
                triangle2.setP2(VERTICES.get(j).get(i + 1));
                triangle2.setNormal();
                triangle2.setCenter();
                PLANS.add(triangle2);
            }
        }
        if (ANGLE >= 360) {
            for (int i = 0; i < NUM_POINTS - 1; i++) {
                Triangle triangle1 = new Triangle(canvas);
                triangle1.setP0(VERTICES.get(VERTICES.size() - 1).get(i));
                triangle1.setP1(VERTICES.get(0).get(i));
                triangle1.setP2(VERTICES.get(0).get(i + 1));
                triangle1.setNormal();
                triangle1.setCenter();
                PLANS.add(triangle1);

                Triangle triangle2 = new Triangle(canvas);
                triangle2.setP0(VERTICES.get(VERTICES.size() - 1).get(i));
                triangle2.setP1(VERTICES.get(0).get(i + 1));
                triangle2.setP2(VERTICES.get(VERTICES.size() - 1).get(i + 1));
                triangle2.setNormal();
                triangle2.setCenter();
                PLANS.add(triangle2);
            }
        }

//        for (int i = 0; i < curve.interpolatedPoints.size(); i++) {
//            if ((i + 1) < curve.interpolatedPoints.size()) {
//                Triangle triangle1 = new Triangle(canvas);
//                triangle1.setP0(generatedCurve.interpolatedPoints.get(i));
//                triangle1.setP1(curve.interpolatedPoints.get(i));
//                triangle1.setP2(curve.interpolatedPoints.get(i+1));
//                triangle1.setCenter();
//                triangles.add(triangle1);
//                
//                Triangle triangle2 = new Triangle(canvas);
//                triangle2.setP0(generatedCurve.interpolatedPoints.get(i));
//                triangle2.setP2(curve.interpolatedPoints.get(i+1));
//                triangle2.setP1(generatedCurve.interpolatedPoints.get(i+1));
//                triangle2.setCenter();
//                triangles.add(triangle2);
//            }
//        }
        setCenter();

        Point pxl = new Point(canvas);
        pxl.setColor(Color.GREEN);
        pxl.setX(centerX);
        pxl.setY(centerY);
        pxl.setZ(centerZ);
        pxl.draw(canvas, ZCP);

        PLANS.stream().forEach((tr) -> {            
            if (tr.normal.cosBetween(0, 0, 1) < 0) {                
                tr.setColor(Color.GREEN);
            }
            tr.drawFilled(canvas, ZCP);
        });

        canvas.unblockRepainting();
        canvas.performRepainting();
        if (numImpressions == 0) {
            JOptionPane.showMessageDialog(canvas, "Esse objeto possui " + numVertices + " Vértices, " + numLines + " Arestas, " + PLANS.size() + " Faces");
            JOptionPane.showMessageDialog(canvas, "Foi utilizada a técnica de z-buffer");
        }
        numImpressions++;
    }

    @Override
    public boolean clicked(int x, int y) {
        return false;
    }

    @Override
    public void dragged(int x, int y) {
    }

    @Override
    public void rotate(double dx, double dy) {
        Transformations.rotateMesh(VERTICES, centerX, centerY, centerZ, dx, dy);
    }

    @Override
    public void translate(double dx, double dy) {
        for (ArrayList<Point> curve : VERTICES) {
            for (Point vertex : curve) {
                vertex.setX(vertex.getX() - dx);
                vertex.setY(vertex.getY() - dy);
            }
        }
    }

    private double getCenterX() {
        double center = 0;
        for (int i = 0; i < PLANS.size(); i++) {
            center += PLANS.get(i).getCenterX();
        }

        return center / PLANS.size();
    }

    private double getCenterY() {
        double center = 0;
        for (int i = 0; i < PLANS.size(); i++) {
            center += PLANS.get(i).getCenterY();
        }

        return center / PLANS.size();
    }

    private double getCenterZ() {
        double center = 0;
        for (int i = 0; i < PLANS.size(); i++) {
            center += PLANS.get(i).getCenterZ();
        }

        return center / PLANS.size();
    }

    @Override
    public void setCenter() {
        this.centerX = getCenterX();
        this.centerY = getCenterY();
        this.centerZ = getCenterZ();
    }
}
