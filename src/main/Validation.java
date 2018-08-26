/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 *
 * @author Elias
 */
public class Validation {

    private static final int MAXIMUM_WIDTH = 900;
    private static final int MAXIMUM_HEIGHT = 600;
    private static final double DEFAULT_MIN = 0;
    private static final double DEFAULT_MAX = 1;
    private static final double DEFAULT_TRANSLATION = 0.1;
    private static final double DEFAULT_ROTATION = 360;
    private static final NumberFormat FORMATTER = new DecimalFormat("0.00");

    public static int validateWidth(String width) {
        try {
            int w = Integer.parseInt(width);
            if (w > MAXIMUM_WIDTH) {
                return MAXIMUM_WIDTH;
            }
            return w;
        } catch (Exception e) {
            return MAXIMUM_WIDTH;
        }
    }

    public static int validateHeight(String height) {
        try {
            int w = Integer.parseInt(height);
            if (w > MAXIMUM_HEIGHT) {
                return MAXIMUM_HEIGHT;
            }
            return w;
        } catch (Exception e) {
            return MAXIMUM_HEIGHT;
        }
    }

    public static double validateMin(String min) {
        try {
            double m = Double.parseDouble(FORMATTER.format(Double.parseDouble(min)).replace(',', '.'));
            return m;
        } catch (Exception e) {
            System.out.println(e);
            return DEFAULT_MIN;
        }
    }

    public static double validateMax(String max) {
        try {
            double m = Double.parseDouble(FORMATTER.format(Double.parseDouble(max)).replace(',', '.'));
            return m;
        } catch (Exception e) {
            System.out.println(e);
            return DEFAULT_MAX;
        }
    }

    public static double validateRotation(String rot) {
        try {
            double r = Double.parseDouble(FORMATTER.format(Double.parseDouble(rot)).replace(',', '.'));
            return r;
        } catch (Exception e) {
            System.out.println(e);
            return DEFAULT_ROTATION;
        }
    }

    public static double validateTranslation(String trans) {
        try {
            double t = Double.parseDouble(FORMATTER.format(Double.parseDouble(trans)).replace(',', '.'));
            return t;
        } catch (Exception e) {
            System.out.println(e);
            return DEFAULT_TRANSLATION;
        }
    }
}
