package com.jsainsbury.serversidetest.scrapers;

import com.jsainsbury.serversidetest.model.Product;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductScraperTest {

    private static final String URL = "www.sainsburys.com/strawberries";
    private static final String TITLE = "Strawberries 500g";

    @Mock
    private WebScraper webScraper;

    @Mock private Document document;
    private Element body;

    @InjectMocks
    private ProductScraper productScraper;


    @Before
    public void before() {
        body = new Element("body");
        when(webScraper.parseWebpage(URL)).thenReturn(document);
        when(document.body()).thenReturn(body);

        Element productTitle = new Element("h1").text(TITLE);
        Element productSummary = new Element("div").addClass("productSummary").insertChildren(0, productTitle);
        body.insertChildren(0, productSummary);
    }

    @Test
    public void shouldParseUrl_andReturnTheProductTitle() {
        Product product = productScraper.getProductDetails(URL);
        assertThat(product.getTitle()).isEqualTo(TITLE);
    }

}
