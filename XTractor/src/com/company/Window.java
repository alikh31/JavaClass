package com.company;


import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.Scanner;
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
    DefaultTableModel model;
    private JButton saveButton;
    private JButton clearButton;
    private JTextField inputField;

    public Window(){

        // DefaultTableModel
        this.model = new DefaultTableModel();
        this.model.addColumn("Link");


        // JTable
        this.table = new JTable(this.model);

        // JScrollPane
        this.scrollPane = new JScrollPane(this.table);

        // JButton and JTextField
        this.clearButton = new JButton("Get Link");
        this.saveButton = new JButton("Save");
        this.inputField = new JTextField("Write the address here", 32);

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

        //this.inputField.setText("");
    }

    public void focusLost(FocusEvent e){

    }

    //method to add and clear data from the table model
    public void doStuff (String command) {

        if(command == "Get Link"){

            for (int i = model.getRowCount() - 1  ; i >= 0  ; i--) {
                model.removeRow(i);
            }

            String input = this.inputField.getText();

            String folderName = "";

            writeImagesName(input, folderName);

        } else if (command == "Save") {

            String input = this.inputField.getText();

            String folderName = "";


            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new java.io.File("."));
            chooser.setDialogTitle("brows dir");
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            //
            // disable the "All files" option.
            //
            chooser.setAcceptAllFileFilterUsed(false);

            if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                System.out.println("getCurrentDirectory(): "+  chooser.getCurrentDirectory());
                folderName = String.valueOf(chooser.getSelectedFile());
                if(folderName.charAt(folderName.length()-1) != '\\') {
                    folderName += "\\";
                }

                //folderName = checkFolder(folderName);

                downloadImages(input , folderName);
            }
            else {
                //System.out.println("No Selection ");
            }


        }

    }

    private static String checkFolder (String source) {

        File theDir = new File(source);


        if (!theDir.exists()) {
            System.out.println("creating directory: " + source);
            boolean result = false;

            try{
                theDir.mkdir();
                result = true;
            }
            catch(SecurityException e){
                System.err.println(e.getMessage());
                return source;
            }
            if(result) {
                System.out.println(source + "directory created");
            }
        }
        else {

            if(theDir.list().length>0) {
                System.out.println(source + "Directory is not empty");
                source = source.substring(0,source.length()-1);
                source += "-1\\";
                return checkFolder(source);

            }
            else {
                System.out.println(source + "Directory is empty");
                return source;
            }
        }




        return source;
    }

    private void downloadImages(String source, String destination) {

        try {
            Url url = new Url("http", source);

            System.out.println("Downloading images from: " + url.getDomain());

            ImgDownloader downloader = new ImgDownloader(url,destination,this);

            int imageCount = downloader.getImageCount();

            if(imageCount > 0){

                downloader.getImage();
            }
            else {
                System.out.println("there are no images to download");

                JOptionPane.showMessageDialog(null, "there are no images to download");

            }

        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());

        }

    }

    private void writeImagesName (String source, String destination) {

        try {
            Url url = new Url("http", source);

            System.out.println("Downloading images from: " + url.getDomain());

            ImgDownloader downloader = new ImgDownloader(url,destination,this);

            int imageCount = downloader.getImageCount();

            if(imageCount > 0){


                downloader.showImage();

            }
            else {
                System.out.println("there are no images to download");

                JOptionPane.showMessageDialog(null, "there are no images to download");

            }

        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());

        }

    }
}