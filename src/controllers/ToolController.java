package controllers;

import java.util.HashSet;
import java.util.Set;
import listeners.ToolListener;

/**
 *
 * @author Elias
 */
public class ToolController implements ToolListener{//Escuta e fala com todas ferramentas
    private Set<ToolListener> listeners; // Conjunto de listeners
    
    public ToolController() {        
        listeners = new HashSet<>();
    }
    
    public void addListener(ToolListener listener) {
        listeners.add(listener);
    }

    public void removeListener(ToolListener listener) {
        listeners.remove(listener);
    }
    
    @Override
    public void toolSelected(String type) {               
        for (ToolListener listener : listeners) {
            listener.toolSelected(type);
        }
    }
    
}
