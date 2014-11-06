package com.company;
import java.util.Scanner;
import java.io.*;

public class Main {


    public static void main(String[] args) {


        if (args.length < 3) {

            showUsage();
            return;
        }

        if (args[0].equalsIgnoreCase("-h")) {

            showUsage();
            return;
        }

        if (args[0].equalsIgnoreCase("-img") && args.length == 3) {

            String folderName = args[2];

            if(folderName.charAt(folderName.length()-1) != '\\') {
                folderName += "\\";
            }


            folderName = checkFolder(folderName);

            //}
            downloadImages(args[1] , folderName);

            getLink(args[1] , folderName);

            return;
        }




    }

    private static void downloadImages(String source, String destination) {

        try {
            Url url = new Url("http", source);

            System.out.println("Downloading images from: " + url.getDomain());

            ImgDownloader downloader = new ImgDownloader(url,destination);

            int imageCount = downloader.getImageCount();

            if(imageCount > 0){
                Scanner scanner = new Scanner(System.in);

                System.out.println("the number of image to download: " + imageCount + ", Downlaod [y/n]?");

                String resultScanner = scanner.nextLine();

                if(resultScanner.equalsIgnoreCase("y")) {
                    downloader.getImage();
                }
                else {
                }

            }
            else {
                System.out.println("there are no images to download");
            }

        }
        catch (Exception e) {

            System.err.println(e.getMessage());
            System.exit(0);

        }

    }

    private static void getLink(String source, String destination) {

        try {
            Url url = new Url("http", source);

            System.out.println("getting link from: " + url.getDomain());

            LinkExtractor extractor = new LinkExtractor(url,destination);

            int linkCount = extractor.getLinkCount();

            if(linkCount > 0){
                Scanner scanner = new Scanner(System.in);

                System.out.println("the number of link : " + linkCount + ",save the links?[y/n]");

                String resultScanner = scanner.nextLine();

                if(resultScanner.equalsIgnoreCase("y")) {
                    extractor.getLink2();
                }
                else {
                }

            }
            else {
                System.out.println("there are no Link");
            }

        }
        catch (Exception e) {

            System.err.println(e.getMessage());
            System.exit(0);

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

    private static void showUsage() {

        System.out.println("USAGE: ");
        System.out.println("XTract -flag source destination");
        System.out.println("-flag -img");
        System.out.println("source valid http url");
        System.out.println("destination path to a folder to save output");
    }
}
