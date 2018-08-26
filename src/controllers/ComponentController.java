package controllers;

import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Set;
import listeners.CanvasListener;
import listeners.ComponentListener;

/**
 *
 * @author Elias
 */
public class ComponentController implements ComponentListener, CanvasListener {//Escuta e fala com todas ferramentas

    private Set<ComponentListener> listeners; // Conjunto de listeners

    public ComponentController() {
        listeners = new HashSet<>();
    }

    public void addListener(ComponentListener listener) {
        listeners.add(listener);
    }

    public void removeListener(ComponentListener listener) {
        listeners.remove(listener);
    }

    @Override
    public void componentRemovedCanvas(int i) {
    }

    @Override
    public void componentMoved(int i, double x, double y, int ind) {

    }

    @Override
    public void componentSelected(int i) {
        listeners.stream().forEach((listener) -> {
            listener.componentSelected(i);
        });
    }

    @Override
    public void mouseClicked(MouseEvent evt) {

    }

    @Override
    public void mouseMoved(MouseEvent evt) {

    }

    @Override
    public void mouseMovedReal(double x, double y) {

    }

    @Override
    public void mouseDragged(double deltaX, double deltaY) {
        
    }

    @Override
    public void mouseWheelMoved(double deltaX, double deltaY) {

    }

    @Override
    public void componentAdded(int i) {

    }

    @Override
    public void pointClickedToComponent(int id) {

    }

    @Override
    public void pointClicked(int id) {
        listeners.stream().forEach((listener) -> {
            listener.pointClickedToComponent(id);
        });
    }

    @Override
    public void positionChanged() {
        listeners.stream().forEach((listener) -> {
            listener.positionChanged();
        });
    }

    @Override
    public void componentRemoved(int i) {
        listeners.stream().forEach((listener) -> {
            listener.componentRemoved(i);
        });
    }

    @Override
    public void mouseDraggedToComponent() {
        listeners.stream().forEach((listener) -> {
            listener.pointClickedToComponent(0);
        });
    }

}
