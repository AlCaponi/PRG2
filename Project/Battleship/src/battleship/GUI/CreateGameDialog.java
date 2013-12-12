/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship.GUI;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
    private JButton okBtn;
    private JPanel pnlGameMode;
    private ButtonGroup btgMode;
    private Lobby lobbyPtr;
    
    public CreateGameDialog(Lobby lobbyPtr)
    {
        super();
        this.lobbyPtr = lobbyPtr;
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
        
        //Radiobutton Host Mode
        rdbHostMode = new JRadioButton();
        rdbHostMode.setText("Host Mode:");
        btgMode.add(rdbHostMode);
        pnlGameMode.add(rdbHostMode);
        
        // Button OK
        okBtn = new JButton();
        okBtn.setText("OK");
        okBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(rdbHostMode.isSelected()) {
                    int portNb = 0;
                    lobbyPtr.StartGameServer(txtBezeichnung.getText(), portNb);
                }
                else if(rdbAIMode.isSelected()) {
                    // start AI
                }
                setVisible(false);
            }
        });
        pnlGameMode.add(okBtn);
        
        setModal(true);
        setResizable(false);
        setVisible(true);

    }
}
