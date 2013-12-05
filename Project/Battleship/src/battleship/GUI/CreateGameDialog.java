/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship.GUI;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Angelo
 */
public class CreateGameDialog extends JDialog
{
    private JPanel pnlMain;
    private JLabel lblBezeichnung;
    
    public CreateGameDialog()
    {
        super();
        
        InitializeComponents();
    }

    private void InitializeComponents() 
    {
        setTitle("Create Game");
        
        //MainPanel
        pnlMain = new JPanel();
        
        
        //Label Bezeichnung
        lblBezeichnung = new JLabel("Bezeichnung");
        
    }
}
