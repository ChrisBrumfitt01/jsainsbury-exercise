package com.jsainsbury.serversidetest.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JsonMapper<T> {

  private static final Logger LOG = Logger.getLogger(JsonMapper.class.getName());

  @Autowired
  private ObjectMapper objectMapper;

  /**
   * Takes an object and converts it to a JSON string
   * @param t The object to convert
   * @return The JSON string
   */
  public String convertToJson(T t) {
    String json = null;
    try {
      ObjectWriter prettyPrinter = objectMapper.writerWithDefaultPrettyPrinter();
      prettyPrinter.writeValue(new File("products.json"), t);
      json = prettyPrinter.writeValueAsString(t);
    } catch (JsonProcessingException e) {
      LOG.log(Level.WARNING, "Could not convert object to JSON");
    } catch (IOException e) {
      LOG.log(Level.WARNING, "IO error when attempting to write to JSON file");
    }

    return json;
  }

}
