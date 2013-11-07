/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Uebungen4;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.*;

/**
 *
 * @author Simon
 */
public class CalculatorFrame extends JFrame {

    String operator = "";
    JFormattedTextField input1;
    JFormattedTextField input2;
    JTextField output;

    public CalculatorFrame() {
        JButton buttonPlus = new JButton("+");
        buttonPlus.addActionListener(
                new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                operator = "+";
                Calculate();
            }
        });

        JButton buttonMinus = new JButton("-");
        buttonMinus.setName("-");
        buttonMinus.addActionListener(new MyClicker());

        JButton buttonDivide = new JButton("/");
        buttonDivide.setName("/");
        buttonDivide.addActionListener(new MyClicker());

        JButton buttonMultiply = new JButton("*");
        buttonMultiply.setName("*");
        buttonMultiply.addActionListener(new MyClicker());

        input1 = new JFormattedTextField(DecimalFormat.getInstance());
        input1.setColumns(10);
        input1.addActionListener(
                new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Text changed ?
                int x = 0;
            }
        });
        input2 = new JFormattedTextField(DecimalFormat.getInstance());
        input2.setColumns(10);
        output = new JTextField(20);

        JPanel panelInput = new JPanel();
        //panelInput.setLayout(new GridBagLayout(1,0));

        JPanel panelOutput = new JPanel();
        JPanel panelOperations = new JPanel();
        panelOperations.add(buttonDivide);
        panelOperations.add(buttonMinus);
        panelOperations.add(buttonMultiply);
        panelOperations.add(buttonPlus);
        panelOperations.setLayout(new GridLayout(2, 0));

        panelInput.setLayout(new FlowLayout());
        panelInput.add(input1);
        panelInput.add(input2);
        panelOutput.setLayout(new BorderLayout());
        panelOutput.add(output);

        setLayout(new GridLayout(0, 1));

        add(panelInput);
        add(panelOperations);
        add(panelOutput);
        setSize(200, 500);
        addWindowListener(new Adapter());
        setVisible(true);



    }

    private void Calculate() {
        Float value1 = 0.0f;
        try {
            value1 = Float.parseFloat(input1.getText());
        } catch (NumberFormatException ex) {
            output.setText("input1 Not supported ");
        }
        Float value2 = 0.0f;
        try {
            value2 = Float.parseFloat(input2.getText());
        } catch (NumberFormatException ex) {
            output.setText("input2 Not supported ");
        }
        Float result = 0.0f;
        switch (operator) {
            case "+":
                result = value1 + value2;
                break;
            case "-":
                result = value1 - value2;
                break;
            case "*":
                result = value1 * value2;
                break;
            case "/":
                result = value1 / value2;
                break;
            default:
                output.setText("Not supported Operation");
                break;
        }
        output.setText(result.toString());
    }

    class MyClicker implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() instanceof JButton) {
                JButton button = (JButton) e.getSource();
                operator = button.getName();
                Calculate();
            }

        }
    }

    class Adapter extends WindowAdapter {

        @Override
        public void windowClosing(WindowEvent e) {
            System.exit(0);
        }
    }
}
