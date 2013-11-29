/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship.GUI;

import battleship.Engine.Field;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

/**
 *
 * @author Simon
 */
public class GuiField extends JButton {
    
    Field battleField;
    
    public GuiField(Field battleField)
    {
        this.battleField = battleField;
        this.setEnabled(true);
        this.addActionListener(new FieldListener());
        
    }
    
    
    private void setState()
    {
    
    }
    class FieldListener implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent e) {
        
            
            
        }
    
    }
}
