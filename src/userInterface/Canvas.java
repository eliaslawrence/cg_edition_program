package userInterface;

import computerGraphics.B_Spline_Cubica;
import computerGraphics.B_Spline_Quadratica;
import computerGraphics.Bezier_Cubica;
import computerGraphics.Circle;
import computerGraphics.GraphicComponent;
import computerGraphics.Hermite;
import computerGraphics.Line;
import computerGraphics.Point;
import computerGraphics.Surface;
import computerGraphics.Transformations;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import listeners.CanvasListener;
import listeners.ComponentListener;
import listeners.PropertiesListener;
import listeners.ToolListener;

/**
 *
 * @author Elias
 */
public class Canvas extends javax.swing.JPanel implements ToolListener, PropertiesListener, ComponentListener {

    private int width;
    private int height;
    private double xMin;
    private double xMax;
    private double yMin;
    private double yMax;
    private boolean blockRepainting = false;

    //Component not completed
    private boolean newComponent = true;
    private String lastTool = "not selected";

    //Parametro
    public double parameterDelta = 0.001;

    //Mouse 
    //->Mover
    private int xMouse;
    private int yMouse;
    private double deltaX;
    private double deltaY;
    //->Girar
    private int xMouseRot;
    private int yMouseRot;
    private double deltaXRot;
    private double deltaYRot;

    //Tool
    private String toolSelected = "not selected";

    //Component
    private int componentSelected = -1;

    private BufferedImage image;

    private ArrayList<GraphicComponent> components;

    private Set<CanvasListener> listeners; // Conjunto de listeners

    //z-buffer: foi utilizado a técnica de Z-BUFFER, 
    //por se tratar de um método mais genérico e de 
    //mais fácil escalabilidade
    public double[][] zBuffer;

    public Canvas(double xMin, double xMax, double yMin, double yMax, int width, int height) {
        initComponents();
        listeners = new HashSet<>();
        this.width = width;
        this.height = height;
        this.xMin = xMin;
        this.xMax = xMax;
        this.yMax = yMax;
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        zBuffer = new double[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                zBuffer[i][j] = Double.MAX_VALUE;
            }
        }
        components = new ArrayList<>();
    }

    public Canvas() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBackground(new java.awt.Color(255, 255, 255));
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                formMouseDragged(evt);
            }
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                formMouseMoved(evt);
            }
        });
        addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                formMouseWheelMoved(evt);
            }
        });
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                formMouseEntered(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(image, null, null);
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void addListener(CanvasListener listener) {
        this.listeners.add(listener);
    }

    public void removeListener(CanvasListener listener) {
        this.listeners.remove(listener);
    }

    public void addComponent(GraphicComponent component) {
        component.setCanvas(this);
        this.components.add(component);
        for (CanvasListener listener : listeners) {
            listener.componentAdded(components.size() - 1);
        }
        update();
    }

    public void removeComponent(int i) {
        this.components.remove(i);
        update();
        for (CanvasListener listener : listeners) {
            listener.componentRemovedCanvas(i);
        }
    }

    public void removeAll() {
        for (CanvasListener listener : listeners) {
            for (int i = 0; i < components.size(); i++) {
                listener.componentRemovedCanvas(0);
            }
        }
        this.components.clear();
        update();
    }

    public ArrayList<GraphicComponent> getGraphicComponents() {
        return components;
    }

    public double getxMin() {
        return xMin;
    }

    public double getxMax() {
        return xMax;
    }

    public double getyMin() {
        return yMin;
    }

    public double getyMax() {
        return yMax;
    }

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked

    }//GEN-LAST:event_formMouseClicked

    private void formMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseMoved
        // TODO add your handling code here:
        for (CanvasListener listener : listeners) {
            listener.mouseMoved(evt);
        }

        for (CanvasListener listener : listeners) {
            listener.mouseMovedReal(viewPortXtoReal(evt.getX()), viewPortYtoReal(evt.getY()));
        }
    }//GEN-LAST:event_formMouseMoved

    private void formMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseEntered
        // TODO add your handling code here:        
        switch (toolSelected) {
            case "Mover":
                this.setCursor(new Cursor(Cursor.MOVE_CURSOR));
                break;
            default:
                this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                break;
        }
    }//GEN-LAST:event_formMouseEntered

    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        // TODO add your handling code here: 

        if (toolSelected.equals("Mover")) {
            deltaX = evt.getX() - xMouse;
            deltaY = evt.getY() - yMouse;

            deltaX = deltaX * (xMax - xMin) / width;
            deltaY = deltaY * (yMax - yMin) / height;

            xMouse = evt.getX();
            yMouse = evt.getY();

            if (componentSelected != -1 && components.get(componentSelected).isSelected()) {
                components.get(componentSelected).translate(-deltaX, deltaY);
            } else {
                for (CanvasListener listener : listeners) {
                    listener.mouseDragged(-deltaX, deltaY);
                }
            }
        }

        if (toolSelected.equals("Girar")) {
            deltaXRot = evt.getX() - xMouseRot;
            deltaYRot = evt.getY() - yMouseRot;

            deltaXRot = deltaXRot * (xMax - xMin) / width;
            deltaYRot = deltaYRot * (yMax - yMin) / height;

            xMouseRot = evt.getX();
            yMouseRot = evt.getY();

            if (componentSelected != -1) {
                components.get(componentSelected).rotate(deltaXRot, deltaYRot);
            }
        } else {

            for (CanvasListener listener : listeners) {
                listener.mouseDraggedToComponent();
            }
            //if (componentSelected != -1) {
            components.stream().forEach((component) -> {
                component.dragged(evt.getX(), evt.getY());
            });
        }
        //components.get(componentSelected).dragged(evt.getX(), evt.getY());
        //}
        update();
    }//GEN-LAST:event_formMouseDragged

    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        // TODO add your handling code here:
        xMouse = evt.getX();
        yMouse = evt.getY();

        xMouseRot = evt.getX();
        yMouseRot = evt.getY();

        Point pxl;
        switch (toolSelected) {
            case "Ponto":
                if (!newComponent) {
                    removeComponent(components.size() - 1);
                }
                pxl = new Point(viewPortXtoReal(evt.getX()), viewPortYtoReal(evt.getY()));

                pxl.setViewportX(evt.getX());
                pxl.setViewportY(evt.getY());

                pxl.draw(this);//drawFilledCircle(pxl.getX(), pxl.getY(), 4);
                addComponent(pxl);
                newComponent = true;
                lastTool = toolSelected;
                break;
            case "Círculo":
                if (!newComponent) {
                    removeComponent(components.size() - 1);
                }
                Circle circle = new Circle(viewPortXtoReal(evt.getX()), viewPortYtoReal(evt.getY()));
                circle.draw(this);
                //pxl = new Pixel(viewPortXtoReal(evt.getX()), viewPortYtoReal(evt.getY()));
                //drawCircle(pxl.getX(), pxl.getY(), 100);
                addComponent(circle);
                newComponent = true;
                lastTool = toolSelected;
                break;
            case "Reta":
                pxl = new Point(viewPortXtoReal(evt.getX()), viewPortYtoReal(evt.getY()));

                pxl.setViewportX(evt.getX());
                pxl.setViewportY(evt.getY());

                pxl.draw(this);

                if (newComponent) {
                    Line line = new Line(this);
                    line.setP0(pxl);
                    addComponent(line);

                    newComponent = false;
                } else {
                    Line line = (Line) components.get(components.size() - 1);
                    line.setP1(pxl);
                    components.get(components.size() - 1).draw(this);
                    newComponent = true;
                }
                lastTool = toolSelected;
                break;
            case "Bezier":
                if (newComponent) {
                    Bezier_Cubica bezier = new Bezier_Cubica();
                    addComponent(bezier);
                    newComponent = false;
                }
                Bezier_Cubica bezier = (Bezier_Cubica) components.get(components.size() - 1);

                pxl = new Point(viewPortXtoReal(evt.getX()), viewPortYtoReal(evt.getY()));

                pxl.setViewportX(evt.getX());
                pxl.setViewportY(evt.getY());

                pxl.draw(this);

                bezier.addPoint(pxl);

                if ((bezier.getNumPoints() % 3) == 0) {
                    bezier.draw(this);
                }

                lastTool = toolSelected;
                break;
            case "Hermite":
                if (newComponent) {
                    Hermite hermite = new Hermite();
                    addComponent(hermite);
                    newComponent = false;
                }
                Hermite hermite = (Hermite) components.get(components.size() - 1);

                pxl = new Point(viewPortXtoReal(evt.getX()), viewPortYtoReal(evt.getY()));

                pxl.setViewportX(evt.getX());
                pxl.setViewportY(evt.getY());

                pxl.draw(this);

                hermite.addPoint(pxl);

                if ((hermite.getNumPoints() % 3) == 0) {
                    hermite.draw(this);
                }

                lastTool = toolSelected;
                break;
            case "B_Spline_C":
                if (newComponent) {
                    B_Spline_Cubica bspline = new B_Spline_Cubica();
                    addComponent(bspline);
                    newComponent = false;
                }
                B_Spline_Cubica bspline = (B_Spline_Cubica) components.get(components.size() - 1);

                pxl = new Point(viewPortXtoReal(evt.getX()), viewPortYtoReal(evt.getY()));

                pxl.setViewportX(evt.getX());
                pxl.setViewportY(evt.getY());

                pxl.draw(this);

                bspline.addPoint(pxl);

                if ((bspline.getNumPoints() % 3) == 0) {
                    bspline.draw(this);
                }

                lastTool = toolSelected;
                break;
            case "B_Spline_Q":
                if (newComponent) {
                    B_Spline_Quadratica bsq = new B_Spline_Quadratica();
                    addComponent(bsq);
                    newComponent = false;
                }
                B_Spline_Quadratica bsq = (B_Spline_Quadratica) components.get(components.size() - 1);

                pxl = new Point(viewPortXtoReal(evt.getX()), viewPortYtoReal(evt.getY()));

                pxl.setViewportX(evt.getX());
                pxl.setViewportY(evt.getY());

                pxl.draw(this);

                bsq.addPoint(pxl);
                //bsq.setZcp(bsq.greaterDistance() * 40);
                if ((bsq.getNumPoints() % 2) == 0) {
                    bsq.draw(this);
                }

                lastTool = toolSelected;
                break;
            case "Mover":
                break;
            case "not selected":
                //if (componentSelected != -1) {
                int pos = 0;
                for (GraphicComponent component : components) {
                    if (component.clicked(evt.getX(), evt.getY())) {
                        for (CanvasListener listener : listeners) {
                            listener.pointClicked(pos);
                        }
                    }
                    pos++;
                }
                //components.get(componentSelected).clicked(evt.getX(), evt.getY());
                update();
                //}
                break;
        }
    }//GEN-LAST:event_formMousePressed

    private void formMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_formMouseWheelMoved
        // TODO add your handling code here:
        double dx, dy;
        int k = evt.getWheelRotation();
        //Aumenta o zoom
        if (k < 0) {
            dx = (-k) * 0.125 * (xMax - xMin) / 2;
            dy = (-k) * 0.125 * (yMax - yMin) / 2;
        } else {
            dx = -k * 1.125 * (xMax - xMin) / 2;
            dy = -k * 1.125 * (yMax - yMin) / 2;
        }
        listeners.stream().forEach((listener) -> {
            listener.mouseWheelMoved(dx, dy);
        });
    }//GEN-LAST:event_formMouseWheelMoved

    public void setToolSelected(String toolSelected) {
        this.toolSelected = toolSelected;
//        if (toolSelected.equals("Girar")) {
//            for (GraphicComponent component : components) {
//                component.setCenter();
//            }
//        }
    }

    public void drawLine(Point p0, Point p1, double zcp, double distance, Color color, double delta) {
        blockRepainting();

        int x, y;
        Point pxl = new Point(this);
        pxl.setColor(color);
        
        double[][] m = {{p0.getX()}, {p0.getY()}, {p0.getZ()}, {1}};
        m = Transformations.projectPCz(zcp, m);

        int x0 = realXtoViewPort(m[0][0]);
        int y0 = realYtoViewPort(m[1][0]);
        
        m[0][0] = p1.getX(); 
        m[1][0] = p1.getY(); 
        m[2][0] = p1.getZ(); 
        m[3][0] = 1;
        
        m = Transformations.projectPCz(zcp, m);

        int x1 = realXtoViewPort(m[0][0]);
        int y1 = realYtoViewPort(m[1][0]);

        for (double i = 0; i <= 1; i += delta) {
            x = (int)(x0 + (x1 - x0) * i);
            y = (int)(y0 + (y1 - y0) * i);

            drawPixel(x, y, color, distance);
        }

        unblockRepainting();
        performRepainting();
    }

    public void drawPixel(Point pxl) {
        int x = realXtoViewPort(pxl.getX());
        int y = realYtoViewPort(pxl.getY());

        if (x < width && x >= 0
                && y < height && y >= 0) {
            image.setRGB(x, y, pxl.getColor().getRGB());
            performRepainting();
            pxl.setPainted(true);
        }
    }
    
    public void drawPixel(int x, int y, Color color, double distance) {
        
        if (x < width && x >= 0
                && y < height && y >= 0 && zBuffer[x][y] > distance) {
            image.setRGB(x, y, color.getRGB());
            zBuffer[x][y] = distance;
            performRepainting();            
        }
    }

    public void drawPixel(Point pxl, double zcp, double distance) {
        double[][] m = {{pxl.getX()}, {pxl.getY()}, {pxl.getZ()}, {1}};
        m = Transformations.projectPCz(zcp, m);

        int x = realXtoViewPort(m[0][0]);
        int y = realYtoViewPort(m[1][0]);

        if (x < width && x >= 0
                && y < height && y >= 0 && zBuffer[x][y] > distance) {
            image.setRGB(x, y, pxl.getColor().getRGB());
            zBuffer[x][y] = distance;
            performRepainting();
            pxl.setPainted(true);
        }
    }

    public void drawPixel(int x, int y, Color color) {
        if (x < width && x >= 0
                && y < height && y >= 0) {
            image.setRGB(x, y, color.getRGB());
            performRepainting();
        }
    }

    public void erasePixel(Point pxl) {
        Color colorTemp = pxl.getColor();
        pxl.setColor(this.getBackground());
        drawPixel(pxl);
        pxl.setColor(colorTemp);
    }

    public int realXtoViewPort(double x) {
        double resp = ((x - xMin) * (width)) / (xMax - xMin);
        return (int) resp;
    }

    public int realYtoViewPort(double y) {
        double resp = ((y - yMin) * (-height)) / (yMax - yMin) + height;
        return (int) resp;
    }

    public double viewPortXtoReal(int x) {
        return ((x) * (xMax - xMin)) / (width) + xMin;
    }

    public double viewPortYtoReal(int y) {
        return ((y - height) * (yMax - yMin)) / (-height) + yMin;
    }

    @Override
    public void toolSelected(String type) {
        if (lastTool.equals("Bezier") && !newComponent) {
            Bezier_Cubica b = (Bezier_Cubica) components.get(components.size() - 1);
            if (b.getNumPoints() < 3) {
                removeComponent(components.size() - 1);
            }
        } else if (lastTool.equals("Hermite") && !newComponent) {
            Hermite h = (Hermite) components.get(components.size() - 1);
            if (h.getNumPoints() < 3) {
                removeComponent(components.size() - 1);
            }
        } else if (lastTool.equals("B_Spline_C") && !newComponent) {
            B_Spline_Cubica bs = (B_Spline_Cubica) components.get(components.size() - 1);
            if (bs.getNumPoints() < 3) {
                removeComponent(components.size() - 1);
            }
        } else if (lastTool.equals("B_Spline_Q") && !newComponent) {
            B_Spline_Quadratica bs = (B_Spline_Quadratica) components.get(components.size() - 1);
            if (bs.getNumPoints() < 2) {
                removeComponent(components.size() - 1);
            }
        } else if (!type.equals(lastTool) && !newComponent) {
            removeComponent(components.size() - 1);
        }
        newComponent = true;
        update();
        setToolSelected(type);
    }

    public void blockRepainting() {
        blockRepainting = true;
    }

    public void unblockRepainting() {
        blockRepainting = false;
    }

    public void performRepainting() {
        if (!blockRepainting) {
            repaint();
        }
    }

    public void clear() {
        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                drawPixel(i, j, getBackground());
                zBuffer[i][j] = Double.MAX_VALUE;
            }
        }
    }

    public void update() {
        clear();
        components.stream().forEach((component) -> {
            component.draw(this);
        });
    }

    @Override
    public void limitsChanged(double xMin, double xMax, double yMin, double yMax) {
        this.xMax = xMax;
        this.xMin = xMin;
        this.yMax = yMax;
        this.yMin = yMin;

        clear();
        update();
    }

    @Override
    public void sizeChanged(int width, int height) {
        this.height = height;
        this.width = width;
        this.setSize(new Dimension(width, height));
        this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        this.zBuffer = new double[width][height];
        this.revalidate();
        clear();
    }

    @Override
    public void componentRemoved(int i) {
        removeComponent(i);
    }

    @Override
    public void componentMoved(int i, double x, double y, int ind) {
        GraphicComponent component = components.get(i);
        switch (component.getType()) {
            case "Point":
                Point p = (Point) component;
                p.setX(x);
                p.setY(y);
                break;
            case "Circle":
                Circle c = (Circle) component;
                if (ind == 0) {
                    c.setX(x);
                    c.setY(y);
                } else {
                    c.setRadius(x);
                }
                break;
            case "Line":
                Line l = (Line) component;
                if (ind == 0) {
                    l.getP0().setX(x);
                    l.getP0().setY(y);
                } else {
                    l.getP1().setX(x);
                    l.getP1().setY(y);
                }
                break;
            case "Bezier":
                Bezier_Cubica b = (Bezier_Cubica) component;
                b.getPoints().get(ind).setX(x);
                b.getPoints().get(ind).setX(x);
                break;
        }
        update();
    }

    @Override
    public void componentSelected(int i) {
        if (components.get(i).isSelected()) {
            components.get(i).setSelected(false);
//            components.stream().forEach((component) -> {
//                component.setSelected(false);
//            });
        } else {
//            components.stream().forEach((component) -> {
//                component.setSelected(false);
//            });
            components.get(i).setSelected(true);
        }
        componentSelected = i;
        update();
    }

    @Override
    public void pointClickedToComponent(int id) {

    }

    @Override
    public void positionChanged() {
        update();
    }

    @Override
    public void lightChanged() {
        update();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
