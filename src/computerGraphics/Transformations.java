/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package computerGraphics;

import java.util.ArrayList;
import userInterface.Canvas;

/**
 *
 * @author Elias
 */
public class Transformations {

    //m = {{p.getX()},{p.getY()},{p.getZ()},{1}};
    public static double[][] translate(double dx, double dy, double dz, double[][] m) {
        double[][] trans = {{1, 0, 0, dx},
        {0, 1, 0, dy},
        {0, 0, 1, dz},
        {0, 0, 0, 1}};
        double[][] resp = multiply(trans, m);
        return resp;
    }

    public static double[][] rotateX(double angle, double[][] m) {
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);

        double[][] rotation = {{1, 0, 0, 0},
        {0, cos, -sin, 0},
        {0, sin, cos, 0},
        {0, 0, 0, 1}};
        double[][] resp = multiply(rotation, m);
        return resp;
    }

    public static double[][] rotateY(double angle, double[][] m) {
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);

        double[][] rotation = {{cos, 0, sin, 0},
        {0, 1, 0, 0},
        {-sin, 0, cos, 0},
        {0, 0, 0, 1}};
        double[][] resp = multiply(rotation, m);
        return resp;
    }

    public static double[][] rotateZ(double angle, double[][] m) {
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);

        double[][] rotation = {{cos, -sin, 0, 0},
        {sin, cos, 0, 0},
        {0, 0, 1, 0},
        {0, 0, 0, 1}};
        double[][] resp = multiply(rotation, m);
        return resp;
    }

    public static ArrayList<Point> rotateCurveX(ArrayList<Point> curve, double centerX, double centerY, double centerZ, double angle) {
        ArrayList<Point> generatedCurve = new ArrayList<>();
        Canvas canvas = curve.get(0).canvas;

        for (int i = 0; i < curve.size(); i++) {
            //Original Curve
            double[][] m = {{curve.get(i).getX() - centerX},
            {curve.get(i).getY() - centerY},
            {curve.get(i).getZ() - centerZ},
            {1}};

            m = rotateX(angle, m);

            Point pxl = new Point(canvas);
            pxl.setX(m[0][0] + centerX);
            pxl.setY(m[1][0] + centerY);
            pxl.setZ(m[2][0] + centerZ);

            generatedCurve.add(pxl);
        }

        return generatedCurve;
    }

    public static ArrayList<Point> rotateCurveY(ArrayList<Point> curve, double centerX, double centerY, double centerZ, double angle) {
        ArrayList<Point> generatedCurve = new ArrayList<>();
        Canvas canvas = curve.get(0).canvas;

        for (int i = 0; i < curve.size(); i++) {
            //Original Curve
            double[][] m = {{curve.get(i).getX() - centerX},
            {curve.get(i).getY() - centerY},
            {curve.get(i).getZ() - centerZ},
            {1}};

            m = rotateY(angle, m);

            Point pxl = new Point(canvas);
            pxl.setX(m[0][0] + centerX);
            pxl.setY(m[1][0] + centerY);
            pxl.setZ(m[2][0] + centerZ);

            generatedCurve.add(pxl);
        }

        return generatedCurve;
    }

    public static void rotateMesh(ArrayList<ArrayList<Point>> mesh, double centerX, double centerY, double centerZ, double dx, double dy) {
        for (int countCurve = 0; countCurve < mesh.size(); countCurve++) {
            for (int i = 0; i < mesh.get(countCurve).size(); i++) {
                //Original Curve
                double[][] m = {{mesh.get(countCurve).get(i).getX() - centerX},
                {mesh.get(countCurve).get(i).getY() - centerY},
                {mesh.get(countCurve).get(i).getZ() - centerZ},
                {1}};

                m = rotateX(dy * 10, m);
                m = rotateY(dx * 10, m);

                mesh.get(countCurve).get(i).setX(m[0][0] + centerX);
                mesh.get(countCurve).get(i).setY(m[1][0] + centerY);
                mesh.get(countCurve).get(i).setZ(m[2][0] + centerZ);
            }
        }
    }

    public static double[][] projectPCz(double zcp, double[][] m) {
        double r = -1 / zcp;
        //System.out.println("r: " + r);
        double[][] proj = {{1, 0, 0, 0},
        {0, 1, 0, 0},
        {0, 0, 0, 0},
        {0, 0, r, 1}};
        double[][] resp = multiply(proj, m);
        for (int i = 0; i < resp.length; i++) {
            resp[i][0] /= resp[resp.length - 1][0];
        }
        return resp;
    }

    private static double[][] multiply(double[][] m1, double[][] m2) {
        int line = m1.length;
        int column = m2[0].length;
        double[][] mat = new double[line][column];
        for (int i = 0; i < line; i++) {
            for (int j = 0; j < column; j++) {
                for (int k = 0; k < m1[0].length; k++) {
                    mat[i][j] += m1[i][k] * m2[k][j];
                }
                //System.out.print("m" + i + j + " = " + mat[i][j] + "  ");
            }
            //System.out.println("");
        }
        return mat;
    }

    public static void printMatrix(double[][] m) {
        int line = m.length;
        int column = m[0].length;
        for (int i = 0; i < line; i++) {
            for (int j = 0; j < column; j++) {
                System.out.print("m" + i + j + " = " + m[i][j] + "  ");
            }
            System.out.println("");
        }
    }
}
