package com.jsainsbury.serversidetest;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import com.jsainsbury.serversidetest.model.ProductResults;
import com.jsainsbury.serversidetest.model.Total;
import com.jsainsbury.serversidetest.services.BerriesCherriesCurrantsService;
import com.jsainsbury.serversidetest.services.JsonMapper;
import java.math.BigDecimal;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServersideTestApplicationTest {

  @Mock private BerriesCherriesCurrantsService service;
  @Mock private JsonMapper<ProductResults> jsonMapper;

  @InjectMocks
  private ServersideTestApplication application;

  @Test
  public void shouldGetProductsFromTheService_andMapToJson() {
    ProductResults results = new ProductResults(List.of(), new Total(BigDecimal.valueOf(1), BigDecimal.valueOf(2)));
    when(service.getProductDetails()).thenReturn(results);
    application.run();
    verify(jsonMapper).convertToJson(results);
  }


}
