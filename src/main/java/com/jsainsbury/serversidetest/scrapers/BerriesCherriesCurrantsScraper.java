package com.jsainsbury.serversidetest.scrapers;

import com.jsainsbury.serversidetest.model.ProductSummary;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BerriesCherriesCurrantsScraper implements PageScraper {

    private static final String URL = "https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk" +
            "/webapp/wcs/stores/servlet/gb/groceries/berries-cherries-currants6039.html";

    @Autowired private WebScraper webScraper;

    public List<ProductSummary> getProducts() {
        Elements rawProducts = parse()
                .getElementById("productLister")
                .getElementsByClass("gridItem");

        return rawProducts.stream()
                .map(e -> new ProductSummary(getProductTitle(e), getProductHref(e)))
                .collect(Collectors.toList());
    }

    private Element parse() {
        Document doc = webScraper.parseWebpage(URL);
        return doc.body();
    }

    private String getProductTitle(Element element) {
        return element.getElementsByClass("productNameAndPromotions").get(0).text();
    }

    private String getProductHref(Element element) {
        return element.getElementsByTag("a").get(0).attr("abs:href");
    }

}
