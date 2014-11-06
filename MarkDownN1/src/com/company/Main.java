package com.company;
import java.io.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {

    static String fileContent;
    static String fileName;

    public static void main(String[] args) {
	// write your code here


        if (args.length < 2) {

            showUsage();
            return;
        }

        if (args[0].equalsIgnoreCase("-h")) {

            showUsage();
            return;
        }

        if (args[0].equalsIgnoreCase("-read") && args.length == 2) {

            fileName = args[1];
            readFile(fileName);

            inputMode();

            return;
        }

        showUsage();
        return;

    }

    private static void showUsage() {

        System.out.println("USAGE: ");
        System.out.println("MarkDownN1 -flag source destination");
        System.out.println("destination path to a folder to save output");
    }

    private static void inputMode() {

        Scanner scanner = new Scanner(System.in);

        while (true) {

            String resultScanner = scanner.nextLine();

            if (resultScanner.equalsIgnoreCase("exit")) {
                break;
            }

            if (resultScanner.equalsIgnoreCase("clear") && resultScanner.length() == 5) {

                fileContent = "";
                continue;
            }

            if (resultScanner.equalsIgnoreCase("stats") && resultScanner.length() == 5) {

                String tempFileIn = fileContent;

                int lineCount = 0, charCount = 0;

                Scanner tempScanner = new Scanner(tempFileIn);

                while (tempScanner.hasNextLine()) {

                    String line = tempScanner.nextLine();
                    lineCount ++;
                    charCount += line.length();
                }

                System.out.println("Number of line is:" + lineCount);
                System.out.println("Number of char is:" + charCount);


                continue;
            }

            if (resultScanner.equalsIgnoreCase("print")) {

                String tempFileIn = fileContent;

                Scanner tempScanner = new Scanner(tempFileIn);

                while (tempScanner.hasNextLine()) {

                    String line = tempScanner.nextLine();

                    System.out.println(line);

                }

                continue;
            }

            if (resultScanner.startsWith("clear")) {

                String temp = resultScanner.substring(6);

                try {
                    int lineNum = Integer.parseInt(temp);

                    String tempFileIn = fileContent;
                    String tempFileOut = "";

                    int count = 1;

                    Scanner tempScanner = new Scanner(tempFileIn);

                    while (tempScanner.hasNextLine()) {

                        String line = tempScanner.nextLine();

                        if (count == lineNum) {

                            count ++;
                            continue;
                        }

                        tempFileOut += line + System.lineSeparator();

                        count ++;
                    }

                    fileContent = tempFileOut;

                }
                catch (Exception e) {
                    System.out.println("line num is wrong!");
                }

                continue;
            }

            fileContent = resultScanner + System.lineSeparator() + fileContent;

        }

        saveFile();

    }

    private static void saveFile() {

        try {
            PrintWriter out = new PrintWriter(fileName);
            out.println(fileContent);
            out.close();
        }
        catch (Exception e) {

            System.out.println("Can't write!");
        }

    }

    private static void readFile(String source) {


        if( source.isEmpty() ) {

            System.out.println("wrong name!");
            try {
                fileName = "test.txt";
                saveFile();

            } catch (Exception e) {
                System.out.println("access denied!");
                e.printStackTrace();
            }
        }


        BufferedReader reader = null;

        try {
            StringBuilder sb = new StringBuilder();

            File file = new File(source);
            reader = new BufferedReader(new FileReader(file));

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                sb.append(line);
                sb.append(System.lineSeparator());
            }

            fileContent = sb.toString();

        } catch (IOException e) {
            System.out.println("wrong name!");
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
