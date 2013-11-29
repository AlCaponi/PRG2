/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship.GUI;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Simon
 */
public class PlayingWindow extends JFrame {
    
    JPanel leftPanel;
    JPanel rigthPanel;
    JPanel centerPanel;
    BattleFieldGrid playerGrid;
    BattleFieldGrid oponentGrid;
    
    public PlayingWindow()
    {
        leftPanel =new JPanel(new GridLayout(0, 1));
        rigthPanel =new JPanel(new GridLayout(0, 1));
        centerPanel = new JPanel(new GridLayout(0,2));
        
        
        
        setLayout(new BorderLayout());
        setSize(600,600);
        setVisible(true);
    }
    
}
