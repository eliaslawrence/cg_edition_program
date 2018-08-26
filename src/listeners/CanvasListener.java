/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package listeners;
/**
 *
 * @author Elias
 */
public interface CanvasListener {
    public void mouseClicked (java.awt.event.MouseEvent evt);
    public void mouseMoved (java.awt.event.MouseEvent evt);
    public void mouseMovedReal (double x, double y);
    public void mouseDragged(double deltaX, double deltaY);
    public void mouseDraggedToComponent();
    public void mouseWheelMoved(double deltaX, double deltaY);
    public void componentAdded(int i);    
    public void componentRemovedCanvas(int i);   
    public void pointClicked(int id);
}
