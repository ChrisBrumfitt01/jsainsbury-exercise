package com.jsainsbury.serversidetest.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JsonMapper<T> {

  private static final Logger LOG = Logger.getLogger(JsonMapper.class.getName());

  @Autowired
  private ObjectMapper objectMapper;

  public String convertToJson(T t) {
    String json = null;
    try {
      json = objectMapper.writeValueAsString(t);
    } catch (JsonProcessingException e) {
      LOG.log(Level.WARNING, "Could not convert object to JSON");
    }

    return json;
  }

}
