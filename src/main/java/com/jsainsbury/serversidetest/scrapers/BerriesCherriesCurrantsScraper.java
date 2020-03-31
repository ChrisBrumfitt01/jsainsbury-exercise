package com.jsainsbury.serversidetest.scrapers;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BerriesCherriesCurrantsScraper {

    private static final String URL = "https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk" +
            "/webapp/wcs/stores/servlet/gb/groceries/berries-cherries-currants6039.html";

    @Autowired private WebScraper webScraper;

    public Element parse() {
        Document doc = webScraper.parseWebpage(URL);
        return doc.body();
    }

}
