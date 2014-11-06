package com.company;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.IOException;

/**
 * Created by alikh on 10/7/2014.
 */
public class Downloader {

    private String url;

    Document doc;

    Downloader (Url url) throws Exception {

        this.url = url.getString();
    }

    void connect() throws IOException{

        try{

            this.doc = Jsoup.connect(this.url).get();
        }
        catch (IOException e){

            throw new IOException("Can't connect to the server.");

        }

    }
}