/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship.GUI;

import battleship.Engine.BattleField;
import battleship.Engine.Game;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 *
 * @author Simon
 */
public class BattleFieldGrid extends JPanel {
    
    Game game;
    BattleField field;
    private eBattleFieldMode mode;
    private boolean isPlayerGrid;
    ArrayList<GuiField> displayedFields;

    public BattleFieldGrid(Game game, boolean isPlayerGrid) {
        this.game = game;
        this.isPlayerGrid = isPlayerGrid;
        
        displayedFields = new ArrayList<>();
        field = new BattleField(10, 10);
        setLayout(new GridLayout(10, 10));
        buildField();
    }
    
    private void buildField() {
        for (int i = 0; i < field.getWidth(); i++) {
            for (int j = 0; j < field.getHeigth(); j++) {
                GuiField displayField = new GuiField(this.field, field.getFields()[i][j], i, j);
                add(displayField);
                displayedFields.add(displayField);
            }
        }
        if (isPlayerGrid) {
            setMode(eBattleFieldMode.Edit);
        } else {
            setMode(eBattleFieldMode.Displaying);
    
        }
    }
    
    public void UpdateLayout()
    {
        for(GuiField button :displayedFields)
        {
            button.UpdateLayout();
        }
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
        // Gegner grid kann nur anzeigen
        if (isPlayerGrid == false && mode != eBattleFieldMode.Displaying) {
            return;
        }
        this.mode = mode;
        
        for (GuiField g : displayedFields) {
            g.setMode(mode);
        }
    }
}
