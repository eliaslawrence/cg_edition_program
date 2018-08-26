package computerGraphics;

import java.awt.Color;
import userInterface.Canvas;

/**
 *
 * @author Elias
 */
public class Point extends GraphicComponent {

    private double x;
    private double y;
    private double z;

    private double zcp;

    private int viewportX;
    private int viewportY;

    private boolean painted;

    public static int radius = 4;
    public static final Color SELECTED_COLOR = Color.red;

    public Point(Canvas canvas) {
        super();
        super.type = "Point";

        this.x = 0;
        this.y = 0;
        this.z = 0;

        this.color = Color.BLACK;
        this.painted = false;
        this.canvas = canvas;
    }

    public Point(double x, double y) {
        super();
        super.type = "Point";
        this.x = x;
        this.y = y;
        this.color = Color.BLACK;
        this.painted = false;
    }

    public Point(double x, double y, double z) {
        super();
        super.type = "Point";

        this.x = x;
        this.y = y;
        this.z = z;

        this.color = Color.BLACK;
        this.painted = false;
    }

    public Point(double x, double y, Color color) {
        super();
        super.type = "Point";
        this.x = x;
        this.y = y;
        this.color = color;
        this.painted = false;
    }

    @Override
    public String toString() {
        return "(" + this.x + "," + this.y + ") " + this.color + "point";
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public boolean isPainted() {
        return painted;
    }

    public int getViewportX() {
        return viewportX;
    }

    public int getViewportY() {
        return viewportY;
    }

    public void setX(double x) {
        this.x = x;
        //this.viewportX = canvas.realXtoViewPort(x);
    }

    public void setY(double y) {
        this.y = y;
        //this.viewportY = canvas.realYtoViewPort(y);
    }

    public void setZ(double z) {
        this.z = z;
    }

    public void setViewportX(int viewportX) {
        //this.x = canvas.viewPortXtoReal(viewportX);
        this.viewportX = viewportX;
    }

    public void setViewportY(int viewportY) {
        //this.y = canvas.viewPortYtoReal(viewportY);
        this.viewportY = viewportY;
    }

    public void setPainted(boolean painted) {
        this.painted = painted;
    }

    public void draw(Canvas canvas, double zcp) {
        //canvas.drawFilledCircle(x, y, 4);
        Color drawColor = color;
        if (selected) {
            drawColor = SELECTED_COLOR;
            //canvas.drawPoint(x, y, SELECTED_COLOR);
        }

        canvas.blockRepainting();

        double[][] m = {{x}, {y}, {z}, {1}};
        m = Transformations.projectPCz(zcp, m);
        this.zcp = zcp;

        int root;
        int screenX0 = canvas.realXtoViewPort(m[0][0]);
        int screenY0 = canvas.realYtoViewPort(m[1][0]);

        for (int i = 0; i <= 4; i++) {
            //
            root = (int) Math.sqrt(16 - i * i);

            for (int yT = screenY0 + root; yT >= screenY0 - root; yT--) {
                canvas.drawPixel(screenX0 - i, yT, drawColor);
                canvas.drawPixel(screenX0 + i, yT, drawColor);
            }
        }
        canvas.unblockRepainting();
        canvas.performRepainting();
    }

    @Override
    public void draw(Canvas canvas) {
        //canvas.drawFilledCircle(x, y, 4);
        Color drawColor = color;
        if (selected) {
            drawColor = SELECTED_COLOR;
            //canvas.drawPoint(x, y, SELECTED_COLOR);
        }

        canvas.blockRepainting();
        int root;
        int screenX0 = canvas.realXtoViewPort(this.x);
        int screenY0 = canvas.realYtoViewPort(this.y);

        for (int i = 0; i <= 4; i++) {
            //
            root = (int) Math.sqrt(16 - i * i);

            for (int yT = screenY0 + root; yT >= screenY0 - root; yT--) {
                canvas.drawPixel(screenX0 - i, yT, drawColor);
                canvas.drawPixel(screenX0 + i, yT, drawColor);
            }
        }
        canvas.unblockRepainting();
        canvas.performRepainting();
    }

    @Override
    public boolean clicked(int x, int y) {
        selected = Math.abs(x - this.viewportX) <= radius
                && Math.abs(y - this.viewportY) <= radius;
        return selected;
    }

    @Override
    public void dragged(int x, int y) {
        if (selected) {
            this.x = canvas.viewPortXtoReal(x);
            this.y = canvas.viewPortYtoReal(y);

            viewportX = x;
            viewportY = y;
        }
    }

    public static double distanceBetweenPoints(Point p1, Point p2) {
        double distance = 0;

        double dx = p1.getX() - p2.getX();
        double dy = p1.getY() - p2.getY();

        return Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
    }
}
