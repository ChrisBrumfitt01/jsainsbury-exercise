package com.jsainsbury.serversidetest.scrapers;

import com.jsainsbury.serversidetest.model.Product;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BerriesCherriesCurrantsScraper {

    private static final String URL = "https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk" +
            "/webapp/wcs/stores/servlet/gb/groceries/berries-cherries-currants6039.html";

    @Autowired private WebScraper webScraper;

    public Element parse() {
        Document doc = webScraper.parseWebpage(URL);
        return doc.body();
    }

    public List<Product> getProducts() {
        Elements rawProducts = parse()
                .getElementById("productLister")
                .getElementsByClass("gridItem");

        List<Product> products = new ArrayList<>();
        rawProducts.forEach(e -> {
            String productName = e.getElementsByClass("productNameAndPromotions").get(0).text();
            products.add(new Product(productName, null, 0, null));
        });

        return products;

    }

}
