/**
 * Created by alikh on 10/14/2014.
 */
package com.company;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.apache.commons.io.FilenameUtils;
import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class LinkExtractor extends Downloader {


    private String file;
    private Elements link;
    private OutputStream out;

    private File logFile;

    LinkExtractor(Url url, String file) throws Exception {

        super(url);
        super.connect();

        this.link = super.doc.getElementsByTag("a");

        this.file = file;

        this.logFile = new File(this.file + "links.html//");

    }

    int getLinkCount() {

        int count = 0;

        String linkName;
        String src;


        for (Element el : this.link) {

            if (el.hasAttr("href")) {

                src = el.absUrl("href");
                linkName = FilenameUtils.getPath(src);
                //if (this.isLink(linkName)) {
                count++;
                //}
            }
        }

        count = link.size();

        return count;

    }

    private Boolean isLink(String src) {

        try {
            Url url = new Url("http", src);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    void getLink() throws IOException {

        String linkName;
        String src;

        int count = 0;

        BufferedWriter writer = null;
        try {
            // This will output the full path where the file will be written to...
            System.out.println(logFile.getCanonicalPath());

            writer = new BufferedWriter(new FileWriter(logFile));

            for (Element el : this.link) {

                if (el.hasAttr("href")) {

                    src = el.absUrl("href");
                    linkName = FilenameUtils.getPath(src);
                    System.out.println("link: " + linkName);

                    String temp = linkName + "/n";


                    writer.write(linkName);
                    writer.newLine();

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        writer.close();
    }

    void getLink2() throws IOException {

        String linkText;
        String linkHtml;

        Writer writer = new BufferedWriter(new FileWriter(logFile));

        writer.write("<html><meta charset=\"utf-8\"><ul>\n");

        for (Element el : this.link) {
            if(el.hasAttr("href")) {
                linkText = el.text();
                linkHtml = el.attr("href");

                writer.write("<li><a href=\"" + linkHtml + "\">" + linkText + "</a></li>");
            }
        }

        writer.write("<html>");
        writer.close();
    }
}