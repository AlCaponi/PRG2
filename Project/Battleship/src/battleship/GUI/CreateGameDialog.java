/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship.GUI;

import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.ButtonGroup;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

/**
 *
 * @author Angelo
 */
public class CreateGameDialog extends JDialog
{
    private JPanel pnlMain;
    private JLabel lblBezeichnung;
    private JTextField txtBezeichnung;
    private JLabel lblSpielmodus;
    private JRadioButton rdbIPMode;
    private JRadioButton rdbHostMode;
    private JRadioButton rdbAIMode;
    private JPanel pnlGameMode;
    private ButtonGroup btgMode;
    
    public CreateGameDialog()
    {
        super();
        
        InitializeComponents();
    }

    private void InitializeComponents() 
    {
        //Create Game Dialog
        setTitle("Create Game");
        setSize(250, 200);
        
        //MainPanel
        pnlMain = new JPanel();
        pnlMain.setBackground(Color.WHITE);
        pnlMain.setLayout(new GridLayout(0, 2));
        this.add(pnlMain);
        
        //Label Bezeichnung
        lblBezeichnung = new JLabel("Description");
        pnlMain.add(lblBezeichnung);
        
        //Textbox Beziechnung
        txtBezeichnung = new JTextField();
        txtBezeichnung.setSize(200, 25);
        pnlMain.add(txtBezeichnung);
        
        //Label Spielmodus
        lblSpielmodus = new JLabel("Game mode:");
        pnlMain.add(lblSpielmodus);
        
        //Panel Gamemode
        pnlGameMode = new JPanel();
        pnlGameMode.setLayout(new GridLayout(0, 1));
        pnlMain.add(pnlGameMode);
        
        //Buttongroup ModeGroup
        btgMode = new ButtonGroup();
        
        //Radibutton IP Mode
        rdbIPMode = new JRadioButton();
        rdbIPMode.setText("IP Mode:");
        btgMode.add(rdbIPMode);
        pnlGameMode.add(rdbIPMode);
       
        //Radiobutton Host Mode
        rdbHostMode = new JRadioButton();
        rdbHostMode.setText("Host Mode:");
        btgMode.add(rdbHostMode);
        pnlGameMode.add(rdbHostMode);
        
        //Radiobutton AI Mode
        rdbAIMode = new JRadioButton();
        rdbAIMode.setText("AI Mode:");
        btgMode.add(rdbAIMode);
        pnlGameMode.add(rdbAIMode);
        
        setModal(true);
        setResizable(false);
        setVisible(true);
        
    }
}
