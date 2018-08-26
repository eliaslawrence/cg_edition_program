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
public interface ComponentListener {
    public void componentRemoved (int i);
    public void componentMoved (int i, double x, double y, int ind);
    public void componentSelected (int i);
    public void pointClickedToComponent (int id);
    public void positionChanged();
}
