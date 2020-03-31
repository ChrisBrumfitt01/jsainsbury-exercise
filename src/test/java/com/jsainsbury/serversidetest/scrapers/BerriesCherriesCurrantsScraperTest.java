package com.jsainsbury.serversidetest.scrapers;

import com.jsainsbury.serversidetest.model.Product;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BerriesCherriesCurrantsScraperTest {

    private static final String EXPECTED_URL = "https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk" +
            "/webapp/wcs/stores/servlet/gb/groceries/berries-cherries-currants6039.html";

    private static final String PRODUCT1_NAME = "Berries";
    private static final String PRODUCT2_NAME = "Cherries";

    @Mock private WebScraper webScraper;

    @Mock private Document document;

    @Mock private Element body;
    @Mock private Element productLister;
    @Mock private Element gridItem1;
    @Mock private Element gridItem2;

    @InjectMocks
    private BerriesCherriesCurrantsScraper scraper;


    @Before
    public void before() {
        when(webScraper.parseWebpage(anyString())).thenReturn(document);
        when(document.body()).thenReturn(body);
        buildHTMLStub();
    }

    @Test
    public void shouldDelegateToWebScraper_withTheCorrentURL() {
        scraper.parse();
        verify(webScraper).parseWebpage(EXPECTED_URL);
    }

    @Test
    public void shouldReturnTheBody_ofTheReturnedDocument() {
        Element actual = scraper.parse();
        assertThat(actual).isSameAs(body);
    }

    @Test
    public void getProducts_shouldReturnProducts_includingTheProductName() {
        List<Product> products = scraper.getProducts();
        assertThat(products.stream().map(Product::getTitle)).containsExactlyInAnyOrder(PRODUCT1_NAME, PRODUCT2_NAME);
    }

    private void buildHTMLStub() {
        when(body.getElementById("productLister")).thenReturn(productLister);
        Elements gridItems = new Elements(List.of(gridItem1, gridItem2));
        when(productLister.getElementsByClass("gridItem")).thenReturn(gridItems);

        Element product1Name = new Element("div").text(PRODUCT1_NAME);
        Elements product1 = new Elements(product1Name);
        Element product2Name = new Element("div").text(PRODUCT2_NAME);
        Elements product2 = new Elements(product2Name);

        when(gridItem1.getElementsByClass("productNameAndPromotions")).thenReturn(product1);
        when(gridItem2.getElementsByClass("productNameAndPromotions")).thenReturn(product2);
    }
}
