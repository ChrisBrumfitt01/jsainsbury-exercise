package com.jsainsbury.serversidetest.scrapers;

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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BerriesCherriesCurrantsScraperTest {

    private static final String EXPECTED_URL = "https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk" +
            "/webapp/wcs/stores/servlet/gb/groceries/berries-cherries-currants6039.html";

    @Mock private WebScraper webScraper;

    @Mock private Document document;
    private Element body;

    @InjectMocks
    private BerriesCherriesCurrantsScraper scraper;

    @Before
    public void before() {
        body = new Element("body");
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


}
