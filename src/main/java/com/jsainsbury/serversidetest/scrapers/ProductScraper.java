package com.jsainsbury.serversidetest.scrapers;

import com.jsainsbury.serversidetest.model.Product;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;

public class ProductScraper {

    @Autowired private WebScraper webScraper;


    public Product getProductDetails(String url) {
        Element page = webScraper.parseWebpage(url).body();

        return new Product(getTitle(page), getkcalPer100g(page), getPricePerUnit(page), null);
    }

    private String getTitle(Element element) {
        return element.getElementsByClass("productSummary").get(0)
                .getElementsByTag("h1")
                .text();
    }

    private int getkcalPer100g(Element element) {
        String kcal = element.getElementsByClass("nutritionTable").get(0)
                .getElementsContainingText("kcal").get(0)
                .text();

        return Integer.parseInt(kcal.substring(0, kcal.indexOf("k")));
    }

    private double getPricePerUnit(Element element) {
        String pricing = element.getElementsByClass("pricePerUnit").get(0)
                .text()
                .trim();

        return Double.parseDouble(pricing.substring(1, pricing.indexOf("/")));
    }

}
