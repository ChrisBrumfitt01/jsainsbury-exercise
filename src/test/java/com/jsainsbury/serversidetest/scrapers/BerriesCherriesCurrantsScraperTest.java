package com.jsainsbury.serversidetest.scrapers;

import com.jsainsbury.serversidetest.model.ProductSummary;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
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
    private static final String PRODUCT1_URL = "www.sainsburys.com/berries";
    private static final String PRODUCT2_NAME = "Cherries";
    private static final String PRODUCT2_URL = "www.sainsburys.com/cherries";


    @Mock private WebScraper webScraper;
    @Mock private Document document;

    private Element body;

    @InjectMocks
    private BerriesCherriesCurrantsScraper scraper;


    private void buildHTMLStub() {
        Element product1Link = new Element("a").attr("abs:href", PRODUCT1_URL);
        Element product1Title = new Element("div").addClass("productNameAndPromotions").text(PRODUCT1_NAME);
        Element gridItem1 = new Element("div").addClass("gridItem").insertChildren(0, product1Title, product1Link);

        Element product2Link = new Element("a").attr("abs:href", PRODUCT2_URL);
        Element product2Title = new Element("div").addClass("productNameAndPromotions").text(PRODUCT2_NAME);
        Element gridItem2 = new Element("div").addClass("gridItem").insertChildren(0, product2Title, product2Link);

        Element productLister = new Element("div").attr("id", "productLister")
                .insertChildren(0, gridItem1, gridItem2);


        body.insertChildren(0, productLister);
    }

    @Before
    public void before() {
        body = new Element("body");
        buildHTMLStub();
        when(webScraper.parseWebpage(anyString())).thenReturn(document);
        when(document.body()).thenReturn(body);
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
        List<ProductSummary> products = scraper.getProducts();
        ProductSummary[] expected = {
                new ProductSummary(PRODUCT1_NAME, PRODUCT1_URL),
                new ProductSummary(PRODUCT2_NAME, PRODUCT2_URL)
        };
        assertThat(products).containsExactlyInAnyOrder(expected);
    }


}
