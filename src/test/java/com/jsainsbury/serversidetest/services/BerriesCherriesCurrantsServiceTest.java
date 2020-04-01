package com.jsainsbury.serversidetest.services;

import com.jsainsbury.serversidetest.config.PropsConfig;
import com.jsainsbury.serversidetest.model.Product;
import com.jsainsbury.serversidetest.model.ProductResults;
import com.jsainsbury.serversidetest.model.ProductSummary;
import com.jsainsbury.serversidetest.scrapers.BerriesCherriesCurrantsScraper;
import com.jsainsbury.serversidetest.scrapers.ProductScraper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BerriesCherriesCurrantsServiceTest {

    private static final String PRODUCT1_URL = "www.sainsburys.com/product1";
    private static final String PRODUCT2_URL = "www.sainsburys.com/product2";
    private static final String PRODUCT3_URL = "www.sainsburys.com/product3";
    private Product product1 = new Product("Product1", 10, 1.0, "Product 1 description");
    private Product product2 = new Product("Product2", 20, 2.0, "Product 2 description");
    private Product product3 = new Product("Product3", 30, 4.0, "Product 3 description");

    @Mock private ProductScraper productScraper;
    @Mock private BerriesCherriesCurrantsScraper berriesCherriesCurrantsScraper;
    @Mock private PropsConfig propsConfig;

    @InjectMocks
    private BerriesCherriesCurrantsService service;

    @Before
    public void before() {
        when(berriesCherriesCurrantsScraper.getProducts()).thenReturn(buildProductSummaries());
        when(productScraper.getProductDetails(PRODUCT1_URL)).thenReturn(product1);
        when(productScraper.getProductDetails(PRODUCT2_URL)).thenReturn(product2);
        when(productScraper.getProductDetails(PRODUCT3_URL)).thenReturn(product3);
        when(propsConfig.getVatRate()).thenReturn(0.2);
    }

    @Test
    public void shouldGetProductDetailsForEachSummaryReturned_andReturnExpectedDetails() {
        ProductResults results = service.getProductDetails();
        assertThat(results.getResults()).containsExactlyInAnyOrder(product1, product2, product3);
    }

    @Test
    public void shouldCalculateGrossTotalForAllProducts() {
        ProductResults results = service.getProductDetails();
        assertThat(results.getTotal().getGross()).isEqualTo(7.0);
    }

    @Test
    public void shouldCalculateVatForAllProducts() {
        ProductResults results = service.getProductDetails();
        assertThat(results.getTotal().getVat()).isEqualTo(1.17);
    }

    private List<ProductSummary> buildProductSummaries() {
        ProductSummary ps1 = new ProductSummary("Berries", PRODUCT1_URL);
        ProductSummary ps2 = new ProductSummary("Cherries", PRODUCT2_URL);
        ProductSummary ps3 = new ProductSummary("Currants", PRODUCT3_URL);
        return List.of(ps1, ps2, ps3);
    }

}
