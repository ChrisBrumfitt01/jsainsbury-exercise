package com.jsainsbury.serversidetest.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsainsbury.serversidetest.model.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JsonMapperTest {

  @Mock private ObjectMapper objectMapper;

  @InjectMocks
  private JsonMapper<Product> jsonMapper;

  @Test
  public void shouldStringReturnValue_fromObjectMapper() throws JsonProcessingException {
    Product product = new Product("p1", 100, 1.0, "P1 desc");
    String json = "{test: testVal}";
    when(objectMapper.writeValueAsString(product)).thenReturn(json);

    String actual = jsonMapper.convertToJson(product);
    assertThat(actual).isEqualTo(json);
  }

  @Test
  public void shouldReturnNull_whenObjectMapperThrowsException() throws JsonProcessingException {
    Product product = new Product("p1", 100, 1.0, "P1 desc");
    when(objectMapper.writeValueAsString(product)).thenThrow(JsonProcessingException.class);
    assertThat(jsonMapper.convertToJson(product)).isNull();
  }

}
