/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship.GUI;

import battleship.Engine.BattleField;
import battleship.Engine.Field;
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

        // kann nicht mehr gesetzt werden
        if (field.getBattleState() != null) {
            return;
        }

        if (mode != eBattleFieldMode.Displaying) {
            setEnabled(true);
        } // Gegner grid kann nur anzeigen
        else {
            setEnabled(false);
        }
        this.mode = mode;

    }

    private void fieldGotHit() {

        boolean hit = battleField.hitField(positionX, positionY);
        if (hit) {
            setBackground(Color.red);
            setText("X");

        } else {
            setBackground(Color.blue);
            setText("Y");
        }
        setEnabled(false);

    }

    class FieldListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (mode == eBattleFieldMode.Playable) {
                fieldGotHit();
            }
            else if(mode == eBattleFieldMode.Edit)
            {
                // call placeShip
            }
            else
            {
                throw new UnsupportedOperationException("Field mustn't be enabled, worng mode");
            }
        }
    }
}
