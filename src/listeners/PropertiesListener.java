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
public interface PropertiesListener {
    public void limitsChanged (double xMin, double yMin, double xMax, double yMax);
    public void sizeChanged (int width, int height);
    public void lightChanged ();
}
