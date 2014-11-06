package com.company;

import java.util.concurrent.ExecutionException;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.apache.commons.io.FilenameUtils;
import java.io.*;
import java.net.URL;

/**
 * Created by alikh on 10/7/2014.
 */
public class ImgDownloader extends Downloader{

    private Elements img;
    private String folder;

    ImgDownloader (Url url, String folder) throws Exception{
        super(url);
        super.connect();

        this.img = super.doc.getElementsByTag("img");

        this.folder = folder;
    }

    int getImageCount() {

        int count = 0;

        String imageName;
        String src;


        for (Element el: this.img) {

            if(el.hasAttr("src")){

                src = el.absUrl("src");
                imageName = FilenameUtils.getName(src);
                if (this.isImage(imageName)) {
                    count++;
                }
            }
        }

        count = img.size();

        return count;

    }

    private Boolean isImage(String src) {

        String[] extentions = {"gif","jpg","jpeg","png"};


        String extention = FilenameUtils.getExtension(src);

        for (String x: extentions) {
            if(x.equalsIgnoreCase(extention)) {
                return true;
            }
        }

        return false;
    }

    private void download(String src, String imgName) throws IOException{
        URL url = new URL(src);

        InputStream in = url.openStream();

        OutputStream out = new BufferedOutputStream(new FileOutputStream(this.folder + imgName));

        for(int b; (b = in.read()) != -1 ; b++){
            out.write(b);
        }

        out.close();
        in.close();
    }

    void getImage() throws IOException{

        String imageName;
        String src;

        int count = 0;

        for (Element el: this.img) {

            if(el.hasAttr("src")){

                src = el.absUrl("src");
                imageName = FilenameUtils.getName(src);

                if (this.isImage(imageName)) {

                    System.out.println("Downloading img:" + imageName);

                    this.download(src,count + "-" + imageName);

                    count++;

                }
            }
        }
    }

}
