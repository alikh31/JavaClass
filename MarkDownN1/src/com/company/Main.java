package com.company;
import java.io.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {

    static String fileName;

    public static void main(String[] args) {
	// write your code here


        MainWindow win = new MainWindow();
        win.setLocation(50, 50);
        win.setSize(600, 600);
        win.setVisible(true);

//        if (args.length < 2) {
//
//            showUsage();
//            return;
//        }
//
//        if (args[0].equalsIgnoreCase("-h")) {
//
//            showUsage();
//            return;
//        }
//
//        if (args[0].equalsIgnoreCase("-read") && args.length == 2) {
//
//            fileName = args[1];
//
//            parseEntry();
//
//            return;
//        }
//
//        showUsage();

    }

    private static void showUsage() {

        System.out.println("USAGE: ");
        System.out.println("MarkDownN1 -flag source destination");
        System.out.println("destination path to a folder to save output");
    }


    private static void parseEntry() {

        MyFile myFile;

        if(fileName.isEmpty()) {
            myFile = new MyFile();
        }
        else {
            myFile = new MyFile(fileName);
        }

        myFile.read();

        Scanner scanner = new Scanner(System.in);

        while (true) {

            String resultScanner = scanner.nextLine();

            if (resultScanner.equalsIgnoreCase("exit")) {
                break;
            }

            if (resultScanner.equalsIgnoreCase("clear") && resultScanner.length() == 5) {

                myFile.clear();
                continue;
            }

            if (resultScanner.equalsIgnoreCase("stats") && resultScanner.length() == 5) {

                System.out.println("Number of line is:" + myFile.getLineCount());
                System.out.println("Number of word is:" + myFile.getWordCount());
                System.out.println("Number of char is:" + myFile.getCharCount());

                continue;
            }

            if (resultScanner.equalsIgnoreCase("print")) {

                String tempFileIn = myFile.getContent();

                Scanner tempScanner = new Scanner(tempFileIn);

                while (tempScanner.hasNextLine()) {

                    String line = tempScanner.nextLine();

                    System.out.println(line);

                }

                continue;
            }

            if (resultScanner.startsWith("clear")) {

                myFile.deleteLine(Integer.parseInt(resultScanner.substring(6)));

                continue;
            }

            myFile.append(resultScanner);

        }

        myFile.save();

    }
}
