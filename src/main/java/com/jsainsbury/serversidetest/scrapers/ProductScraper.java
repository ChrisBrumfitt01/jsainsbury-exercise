package com.jsainsbury.serversidetest.scrapers;

import com.jsainsbury.serversidetest.model.Product;
import org.apache.commons.lang3.math.NumberUtils;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.logging.Logger;

@Component
public class ProductScraper {

    private static final Logger LOG = Logger.getLogger(ProductScraper.class.getName());

    @Autowired private WebScraper webScraper;

    public Product getProductDetails(String url) {
        LOG.info("Parsing the product page: " + url);
        Element page = webScraper.parseWebpage(url).body();

        return new Product(
                getTitle(page),
                getkcalPer100g(page).orElse(null),
                getPricePerUnit(page),
                getDescription(page)
        );
    }

    private String getTitle(Element element) {
        return element.getElementsByClass("productSummary").get(0)
                .getElementsByTag("h1")
                .text();
    }

    //TODO: Refactor this
    private Optional<Integer> getkcalPer100g(Element element) {
        Elements nutritionalTable = element.getElementsByClass("nutritionTable");
        if(nutritionalTable.isEmpty()) {
            return Optional.empty();
        }

        String nutritionalInfo = element.getElementsByClass("nutritionTable").get(0)
                .getElementsContainingText("kcal").get(0)
                .text();

        String[] infoParts = nutritionalInfo.split("kcal")[0]
                .split(" ");

        String kcal = infoParts[infoParts.length-1];
        if(!NumberUtils.isCreatable(kcal)) {
            kcal = nutritionalInfo.split("kcal")[1]
                    .trim()
                    .split(" ")[0];
        }

        return Optional.of(Integer.parseInt(kcal));
    }

    private double getPricePerUnit(Element element) {
        String pricing = element.getElementsByClass("pricePerUnit").get(0)
                .text()
                .trim();

        return Double.parseDouble(pricing.substring(1, pricing.indexOf("/")));
    }

    private String getDescription(Element element) {
        return element.getElementById("information")
                .getElementsByClass("productText").get(0)
                .text();
    }

}
