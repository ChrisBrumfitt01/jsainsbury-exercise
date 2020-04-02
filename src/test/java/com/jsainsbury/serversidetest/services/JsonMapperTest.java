package com.jsainsbury.serversidetest.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.jsainsbury.serversidetest.model.Product;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JsonMapperTest {

  @Mock private ObjectMapper objectMapper;
  @Mock private ObjectWriter prettyPrinter;

  @Captor
  ArgumentCaptor<File> fileCaptor;

  @InjectMocks
  private JsonMapper<Product> jsonMapper;

  @Before
  public void before() {
    when(objectMapper.writerWithDefaultPrettyPrinter()).thenReturn(prettyPrinter);
  }

  @Test
  public void shouldReturnStringValue_fromObjectMapper() throws JsonProcessingException {
    Product product = buildProduct();
    String json = "{test: testVal}";
    when(prettyPrinter.writeValueAsString(product)).thenReturn(json);

    String actual = jsonMapper.convertToJson(product);
    assertThat(actual).isEqualTo(json);
  }

  @Test
  public void shouldReturnNull_whenObjectMapperThrowsException() throws JsonProcessingException {
    Product product = buildProduct();
    when(prettyPrinter.writeValueAsString(product)).thenThrow(JsonProcessingException.class);
    assertThat(jsonMapper.convertToJson(product)).isNull();
  }

  @Test
  public void shouldWriteJsonToFile() throws IOException {
    Product product = buildProduct();
    jsonMapper.convertToJson(product);
    verify(prettyPrinter).writeValue(fileCaptor.capture(), eq(product));
    assertThat(fileCaptor.getValue().getName()).isEqualTo("products.json");
  }

  private Product buildProduct() {
    return new Product("p1", 100, BigDecimal.ONE, "P1 desc");
  }
}
