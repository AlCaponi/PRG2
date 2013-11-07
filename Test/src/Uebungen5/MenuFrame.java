/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Uebungen5;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JWindow;

/**
 *
 * @author Simon
 */
public class MenuFrame extends JFrame {
    
    JMenuBar menu = new JMenuBar();
    public MenuFrame()
    {
        this.setResizable(true);
        this.setSize(200, 100);
        this.setLayout(new BorderLayout(50, 50));
        
        JMenu item = new JMenu("So ein menu");
        JMenuItem file = new JMenuItem("Test");
        item.add(file);
        file.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(menu, "So eine meldung");
            }
        });
        menu.add(item);
        this.setJMenuBar(menu);
        this.setVisible(true);
        
        
    }
    
    private void ExceptionTest()
    {
    try {
            bla();
        } catch (Exception ex) {
            Logger.getLogger(MenuFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void bla()
    throws Exception
    {
        if(0==0)
            throw new Exception("bla");
    }
}
