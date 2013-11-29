/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship.GUI;

import battleship.Engine.BattleField;
import battleship.Engine.Field;
import battleship.Engine.eFieldState;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

/**
 *
 * @author Simon
 */
public class GuiField extends JButton {

    BattleField battleField;
    int positionX;
    int positionY;
    private eBattleFieldMode mode;
    private Field field;

    public GuiField(BattleField battleField, Field field, int x, int y) {
        this.battleField = battleField;
        this.field = field;
        this.positionX = x;
        this.positionX = x;
        this.positionY = y;
        this.addActionListener(new FieldListener());
        this.mode = eBattleFieldMode.Edit;

    }

    /**
     * @return the mode
     */
    public eBattleFieldMode getMode() {
        return mode;
    }

    /**
     * @param mode the mode to set
     */
    public void setMode(eBattleFieldMode mode) {
        UpdateLayout();
        this.mode = mode;

    }

    public void UpdateLayout() {
        // kann nicht mehr gesetzt werden
        if (field.getBattleState() != null) {
            return;
        }
        // Gegner grid kann nur anzeigen
        if (mode == eBattleFieldMode.Displaying) {
            setEnabled(false);
            
        } 
        else {
            
            setEnabled(true);
            // designMode
            if(field.getFieldState() == eFieldState.Filled)
            {
                setBackground(Color.black);
            }
        }
    }

    private void fieldGotHit() {

        boolean hit = battleField.hitField(positionX, positionY);
        if (hit) {
            setBackground(Color.red);
            setText("X");

        } else {
            setBackground(Color.blue);
            setText("~");
        }
        setEnabled(false);

    }

    class FieldListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (mode == eBattleFieldMode.Playable) {
                fieldGotHit();
            } else if (mode == eBattleFieldMode.Edit) {
                // call placeShip
            } else {
                throw new UnsupportedOperationException("Field mustn't be enabled, worng mode");
            }
        }
    }
}
