package com.jsainsbury.serversidetest.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import com.jsainsbury.serversidetest.model.Product;
import com.jsainsbury.serversidetest.model.ProductSummary;
import com.jsainsbury.serversidetest.scrapers.ProductScraper;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConcurrentProductServiceTest {

  private static final String PRODUCT1_URL = "p1.com";
  private static final String PRODUCT2_URL = "p2.com";
  private static final String PRODUCT3_URL = "p3.com";

  @Mock private ProductScraper productScraper;

  @InjectMocks
  private ConcurrentProductService concurrentProductService;

  private List<ProductSummary> productSummaries;
  private Product product1;
  private Product product2;
  private Product product3;

  @Before
  public void before() {
    productSummaries = new ArrayList<>();
    productSummaries.add(new ProductSummary("p1", PRODUCT1_URL));
    productSummaries.add(new ProductSummary("p2", PRODUCT2_URL));
    productSummaries.add(new ProductSummary("p3", PRODUCT3_URL));

    product1 = new Product("p1", 10, BigDecimal.ONE, "Product 1 desc");
    product2 = new Product("p2", 20, BigDecimal.valueOf(2.0), "Product 2 desc");
    product3 = new Product("p3", 30, BigDecimal.valueOf(3.0), "Product 3 desc");
    when(productScraper.getProductDetails(PRODUCT1_URL)).thenReturn(product1);
    when(productScraper.getProductDetails(PRODUCT2_URL)).thenReturn(product2);
    when(productScraper.getProductDetails(PRODUCT3_URL)).thenReturn(product3);
  }

  @Test
  public void shouldAttemptToGetProduct_forEachProductSummary() {
    concurrentProductService.getProducts(productSummaries);

    verify(productScraper).getProductDetails(PRODUCT1_URL);
    verify(productScraper).getProductDetails(PRODUCT2_URL);
    verify(productScraper).getProductDetails(PRODUCT3_URL);
  }

  @Test
  public void shouldReturnListOfProducts() {
    List<Product> products = concurrentProductService.getProducts(productSummaries);
    assertThat(products).containsExactlyInAnyOrder(product1, product2, product3);
  }


}
