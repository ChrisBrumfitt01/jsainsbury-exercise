package com.jsainsbury.serversidetest.services;

import com.jsainsbury.serversidetest.config.PropsConfig;
import com.jsainsbury.serversidetest.model.Product;
import com.jsainsbury.serversidetest.model.ProductResults;
import com.jsainsbury.serversidetest.model.ProductSummary;
import com.jsainsbury.serversidetest.scrapers.BerriesCherriesCurrantsScraper;
import java.math.BigDecimal;
import java.math.RoundingMode;
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
    private Product product1 = new Product("Product1", 10, BigDecimal.ONE, "Product 1 description");
    private Product product2 = new Product("Product2", 20, BigDecimal.valueOf(2), "Product 2 description");
    private Product product3 = new Product("Product3", 30, BigDecimal.valueOf(4), "Product 3 description");

    @Mock private ConcurrentProductService concurrentProductService;
    @Mock private BerriesCherriesCurrantsScraper berriesCherriesCurrantsScraper;
    @Mock private PropsConfig propsConfig;

    @InjectMocks
    private BerriesCherriesCurrantsService service;

    @Before
    public void before() {
        List<ProductSummary> productSummaries = buildProductSummaries();
        when(berriesCherriesCurrantsScraper.getProducts()).thenReturn(productSummaries);
        when(concurrentProductService.getProducts(productSummaries)).thenReturn(List.of(product1, product2, product3));
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
        BigDecimal expected = BigDecimal.valueOf(7).setScale(2, RoundingMode.HALF_UP);
        assertThat(results.getTotal().getGross()).isEqualTo(expected);
    }

    @Test
    public void shouldCalculateVatForAllProducts() {
        ProductResults results = service.getProductDetails();
        assertThat(results.getTotal().getVat()).isEqualTo(BigDecimal.valueOf(1.17));
    }

    private List<ProductSummary> buildProductSummaries() {
        ProductSummary ps1 = new ProductSummary("Berries", PRODUCT1_URL);
        ProductSummary ps2 = new ProductSummary("Cherries", PRODUCT2_URL);
        ProductSummary ps3 = new ProductSummary("Currants", PRODUCT3_URL);
        return List.of(ps1, ps2, ps3);
    }

}
