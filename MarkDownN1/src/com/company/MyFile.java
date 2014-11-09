package com.company;
import java.io.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Created by Student on 11/6/2014.
 */
public class MyFile {

    private String fileContent;
    private String fileName;

    public MyFile() {

        try {
            fileName = "test.txt";
            save();

        } catch (Exception e) {
            System.out.println("access denied!");
            e.printStackTrace();
        }

    }

    public MyFile(String path) {

        try {
            fileName = path;
            save();

        } catch (Exception e) {
            System.out.println("access denied!");
            e.printStackTrace();
        }
    }

    public void save() {

        try {
            PrintWriter out = new PrintWriter(fileName);
            out.println(fileContent);
            out.close();
        }
        catch (Exception e) {

            System.out.println("Problem in writing the file!");
        }
    }

    public void read(){

        BufferedReader reader = null;

        try {
            StringBuilder sb = new StringBuilder();

            File file = new File(fileName);
            reader = new BufferedReader(new FileReader(file));

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                sb.append(line);
                sb.append(System.getProperty("line.separator"));
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

    public void append(String content){

        fileContent = content + System.getProperty("line.separator") + fileContent;
    }

    public void clear(){

        fileContent = "";
    }

    public String getContent() {
        return fileContent;
    }

    public void deleteLine(int lineNo){

        try {
            int lineNum = lineNo;

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

                tempFileOut += line + System.getProperty("line.separator");

                count ++;
            }

            fileContent = tempFileOut;

        }
        catch (Exception e) {
            System.out.println("line num is wrong!");
        }

    }

    public int getLineCount(){

        String tempFileIn = fileContent;

        int lineCount = 0;

        Scanner tempScanner = new Scanner(tempFileIn);

        while (tempScanner.hasNextLine()) {

            String line = tempScanner.nextLine();
            lineCount ++;
        }

        return lineCount;
    }

    public int getWordCount(){

        String tempFileIn = fileContent;

        int wordCount = 0;

        Scanner tempScanner = new Scanner(tempFileIn);

        while (tempScanner.hasNextLine()) {

            String line = tempScanner.nextLine();
            wordCount += countWords(line);
        }

        return wordCount;
    }

    public static int countWords(String s){

        int wordCount = 0;

        boolean word = false;
        int endOfLine = s.length() - 1;

        for (int i = 0; i < s.length(); i++) {
            // if the char is a letter, word = true.
            if (Character.isLetter(s.charAt(i)) && i != endOfLine) {
                word = true;
                // if char isn't a letter and there have been letters before,
                // counter goes up.
            } else if (!Character.isLetter(s.charAt(i)) && word) {
                wordCount++;
                word = false;
                // last word of String; if it doesn't end with a non letter, it
                // wouldn't count without this.
            } else if (Character.isLetter(s.charAt(i)) && i == endOfLine) {
                wordCount++;
            }
        }
        return wordCount;
    }

    public int getCharCount(){

        String tempFileIn = fileContent;

        int charCount = 0;

        Scanner tempScanner = new Scanner(tempFileIn);

        while (tempScanner.hasNextLine()) {

            String line = tempScanner.nextLine();
            charCount += line.length();
        }

        return charCount;
    }
}