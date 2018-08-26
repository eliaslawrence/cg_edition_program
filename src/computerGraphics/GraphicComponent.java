package computerGraphics;

import java.awt.Color;
import java.io.Serializable;
import userInterface.Canvas;
/**
 *
 * @author Elias
 */
public abstract class GraphicComponent implements Serializable {  
    protected String type;  
    protected boolean selected = false;
    protected transient Canvas canvas;
    protected Color color;
    
    public GraphicComponent(){    
    }

    public void setSelected(boolean selected) {        
        this.selected = selected;
    }

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }
        
    public void setColor(Color color) {
        this.color = color;
    }
    
    public Color getColor() {
        return color;
    }
    
    public void setCenter() {        
        
    }
    
    public String getType() {
        return type;
    }
    
    public void rotate(double dx, double dy) {        
        
    }

    public boolean isSelected() {
        return selected;
    }
            
    public void erase(userInterface.Canvas canvas) {
        Color colorTemp = this.getColor();
        this.setColor(canvas.getBackground());
        this.draw(canvas);
        this.setColor(colorTemp);
    }
    
    public abstract void draw(Canvas canvas);            
    public abstract boolean clicked(int x, int y);        
    public abstract void dragged(int x, int y);

    public void translate(double d, double deltaY) {
        
    }
}
