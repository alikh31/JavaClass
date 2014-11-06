package com.company;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

/**
 * Created by alikh on 10/16/2014.
 */
public class Window extends JFrame implements ActionListener, FocusListener{
    private JPanel mainPanel;
    private JPanel bottomPanel;
    private JScrollPane scrollPane;
    private JTable table;
    private DefaultTableModel model;
    private JButton saveButton;
    private JButton clearButton;
    private JTextField inputField;

    public Window(){

        // DefaultTableModel
        this.model = new DefaultTableModel();
        this.model.addColumn("Links");


        // JTable
        this.table = new JTable(this.model);

        // JScrollPane
        this.scrollPane = new JScrollPane(this.table);

        // JButton and JTextField
        this.clearButton = new JButton("Get Links");
        this.saveButton = new JButton("Save");
        this.inputField = new JTextField("Put the link here", 32);

        // JPanel
        this.bottomPanel= new JPanel(new FlowLayout());
        this.bottomPanel.add(this.inputField);
        this.bottomPanel.add(this.clearButton);
        this.bottomPanel.add(this.saveButton);

        this.mainPanel = new JPanel(new BorderLayout());
        this.mainPanel.add(this.scrollPane, BorderLayout.CENTER);
        this.mainPanel.add(this.bottomPanel, BorderLayout.SOUTH);

        //Add event listener
        this.saveButton.addActionListener(this);
        this.clearButton.addActionListener(this);
        this.inputField.addFocusListener(this);

        //JFrame
        this.getContentPane().add(this.mainPanel);
        this.setTitle("Hello World!");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);


    }

    //event implementation
    public void actionPerformed(ActionEvent e){

        //JOptionPane.showMessageDialog(null, e.getActionCommand());

        this.doStuff(e.getActionCommand());
    }

    public void focusGained(FocusEvent e){

        this.inputField.setText("");
    }

    public void focusLost(FocusEvent e){

    }

    //method to add and clear data from the table model
    public void doStuff (String command) {

        if(command == "Get Links"){

            String input = this.inputField.getText();

            // split string into an array

            String[] aInput = input.split(" ");

            //data
            if (aInput.length > 2){

                this.model.addRow(new Object[]{aInput[0], aInput[1], aInput[2]});
            }
            else {
                JOptionPane.showMessageDialog(null, "Wrong format: at least 3 word");
            }

        } else if (command == "Save") {

            String input = this.inputField.getText();

            // split string into an array

            String[] aInput = input.split(" ");

            //data
            if (aInput.length > 2){

                this.model.addRow(new Object[]{aInput[0], aInput[1], aInput[2]});
            }
            else {
                JOptionPane.showMessageDialog(null, "Wrong format: at least 3 word");
            }
        }

    }
}