package com.jsainsbury.serversidetest.scrapers;

import com.jsainsbury.serversidetest.model.Product;
import com.jsainsbury.serversidetest.scrapers.kcalparsers.KcalParser;
import java.math.BigDecimal;
import java.util.List;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.logging.Logger;

@Component
public class ProductScraper {

    private static final Logger LOG = Logger.getLogger(ProductScraper.class.getName());

    private WebScraper webScraper;
    private List<KcalParser> kcalParsers;

    public ProductScraper(WebScraper webScraper, List<KcalParser> kcalParsers) {
        this.webScraper = webScraper;
        this.kcalParsers = kcalParsers;
    }

    public Product getProductDetails(String url) {
        LOG.info("Parsing the product page: " + url);
        Element page = webScraper.parseWebpage(url).body();

        return new Product(
                getTitle(page),
                getkcalPer100g(page).orElse(null),
                BigDecimal.valueOf(getPricePerUnit(page)),
                getDescription(page)
        );
    }

    private String getTitle(Element element) {
        return element.getElementsByClass("productSummary").get(0)
                .getElementsByTag("h1")
                .text();
    }

    private Optional<Integer> getkcalPer100g(Element element) {
        for(KcalParser kcalParser : kcalParsers) {
            Optional<Integer> kcal = kcalParser.getKcalPer100g(element);
            if(kcal.isPresent()) {
                return kcal;
            }
        }
        return Optional.empty();
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
