package com.jsainsbury.serversidetest.scrapers;

import com.jsainsbury.serversidetest.model.Product;
import com.jsainsbury.serversidetest.scrapers.kcalparsers.KcalParserStrategyOne;
import com.jsainsbury.serversidetest.scrapers.kcalparsers.KcalParserStrategyTwo;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductScraperTest {

    private static final String URL = "www.sainsburys.com/strawberries";
    private static final String TITLE = "Strawberries 500g";
    private static final String DESCRIPTION = "500g of strawberries by Sainsburys";

    @Mock private WebScraper webScraper;
    @Mock private Document document;

    @Mock private KcalParserStrategyOne kcalParserOne;
    @Mock private KcalParserStrategyTwo kcalParserTwo;
    private Element body;

    private ProductScraper productScraper;


    @Before
    public void before() {
        productScraper = new ProductScraper(webScraper, List.of(kcalParserOne, kcalParserTwo));
        when(kcalParserOne.getKcalPer100g(any())).thenReturn(Optional.of(20));

        body = new Element("body");
        when(webScraper.parseWebpage(URL)).thenReturn(document);
        when(document.body()).thenReturn(body);

        Element pricingElement = new Element("p").addClass("pricePerUnit").text("£1.50/unit");

        Element description = new Element("p").addClass("productText").text(DESCRIPTION);
        Element information = new Element("div").attr("id", "information").insertChildren(0, description);

        Element productTitle = new Element("h1").text(TITLE);
        Element productSummary = new Element("div").addClass("productSummary").insertChildren(0, productTitle);

        body.insertChildren(0, productSummary);
        body.insertChildren(1, pricingElement);
        body.insertChildren(2, information);
    }



    @Test
    public void shouldParseUrl_andReturnTheProductTitle() {
        Product product = productScraper.getProductDetails(URL);
        assertThat(product.getTitle()).isEqualTo(TITLE);
    }

    @Test
    public void shouldParseUrl_andReturnTheProductKcalPer100g_whenFirstStrategyReturnsAValue() {
        when(kcalParserOne.getKcalPer100g(body)).thenReturn(Optional.of(32));
        Product product = productScraper.getProductDetails(URL);
        assertThat(product.getKcalPer100g().get()).isEqualTo(32);
    }

    @Test
    public void shouldParseUrl_andReturnTheProductKcalPer100g_whenSecondStrategyReturnsAValue() {
        when(kcalParserOne.getKcalPer100g(body)).thenReturn(Optional.empty());
        when(kcalParserTwo.getKcalPer100g(body)).thenReturn(Optional.of(32));
        Product product = productScraper.getProductDetails(URL);
        assertThat(product.getKcalPer100g().get()).isEqualTo(32);
    }

    @Test
    public void shouldParseUrl_andSetTheKcalPer100gToEmpty_whenKcalIsNotPresent() {
        when(kcalParserOne.getKcalPer100g(body)).thenReturn(Optional.empty());
        when(kcalParserTwo.getKcalPer100g(body)).thenReturn(Optional.empty());
        Product product = productScraper.getProductDetails(URL);
        assertThat(product.getKcalPer100g()).isEmpty();
    }

    @Test
    public void shouldParseUrl_andReturnTheProductPricePerUnit() {
        Product product = productScraper.getProductDetails(URL);
        BigDecimal expected = BigDecimal.valueOf(1.5).setScale(2, RoundingMode.HALF_UP);
        assertThat(product.getUnitPrice()).isEqualTo(expected);
    }

    @Test
    public void shouldParseUrl_andReturnTheProductDescription() {
        Product product = productScraper.getProductDetails(URL);
        assertThat(product.getDescription()).isEqualTo(DESCRIPTION);
    }

    private void addKcalInfo() {
        Element kcalElement = new Element("div").text("32kcal");
        Element nutritionTable = new Element("table").addClass("nutritionTable").insertChildren(0, kcalElement);
        body.insertChildren(3, nutritionTable);
    }

    private void addKcalInfoAlternateFormat() {
        Element kcalElement = new Element("div").text("Some info kcal 52 Some more info");
        Element nutritionTable = new Element("table").addClass("nutritionTable").insertChildren(0, kcalElement);
        body.insertChildren(3, nutritionTable);

    }

}
