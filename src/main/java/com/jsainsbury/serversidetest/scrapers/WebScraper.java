package com.jsainsbury.serversidetest.scrapers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class WebScraper {

    private static final Logger LOG = Logger.getLogger(WebScraper.class.getName());

    public Document parseWebpage(String url) {
        try {
            return Jsoup.connect(url)
                    .get();
        } catch (IOException e) {
            LOG.log(Level.SEVERE, String.format("Could not parse the URL: %s", url));
            return null; //TODO: Replace with Exception handler
        }
    }

}
