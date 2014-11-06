package com.company;
import org.apache.commons.validator.routines.UrlValidator;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by alikh on 10/2/2014.
 */
class Url extends UrlValidator {

    private String url;

    Url(String scheme, String url) throws Exception {

        super(new String[] {scheme});

        if (!super.isValid(url)) {
            throw new Exception("invalid url");
        }
        this.url = url;


    }

    String getDomain() throws URISyntaxException{

        URI uri = new URI(this.url);

        return uri.getHost();
    }

    String getString() {
        return this.url;
    }

}
