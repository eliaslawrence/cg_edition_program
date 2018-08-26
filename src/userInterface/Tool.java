/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userInterface;

import java.awt.Color;
import java.awt.Cursor;
import java.util.HashSet;
import java.util.Set;
import listeners.ToolListener;

/**
 *
 * @author Elias
 */
public class Tool extends javax.swing.JPanel implements ToolListener {

    private boolean selected;
    private final String TYPE;

    private Set<ToolListener> listeners; // Conjunto de listeners

    public Tool(String type) {
        initComponents();
        listeners = new HashSet<>();
        this.TYPE = type;
        jLabel1.setText(type);
    }

    public void addListener(ToolListener listener) {
        listeners.add(listener);
    }

    public void removeListener(ToolListener listener) {
        listeners.remove(listener);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(153, 153, 153));
        setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                formMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                formMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
        });
        setLayout(new java.awt.GridBagLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setText("Ponto");
        add(jLabel1, new java.awt.GridBagConstraints());
    }// </editor-fold>//GEN-END:initComponents

    private void formMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseExited
        // TODO add your handling code here:
        if (!selected) {
            setBackground(new Color(153, 153, 153));
        }
    }//GEN-LAST:event_formMouseExited

    private void formMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseEntered
        // TODO add your handling code here:
        if (!selected) {
            setBackground(new Color(204, 204, 204));
        }
    }//GEN-LAST:event_formMouseEntered

    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        // TODO add your handling code here:
        if (!selected) {
            selected = true;
            setBackground(new Color(240, 240, 240));
            for (ToolListener listener : listeners) {
                listener.toolSelected(TYPE);
            }
        }else{
            selected = false;
            setBackground(new Color(153, 153, 153));
            for (ToolListener listener : listeners) {
                listener.toolSelected("not selected");
            }
        }
    }//GEN-LAST:event_formMousePressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables

    @Override
    public void toolSelected(String type) {
        if (this.selected && !this.TYPE.equals(type)) {
            setBackground(new Color(153, 153, 153));
            this.selected = false;
        }        
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }

    public String getTYPE() {
        return TYPE;
    }
}
