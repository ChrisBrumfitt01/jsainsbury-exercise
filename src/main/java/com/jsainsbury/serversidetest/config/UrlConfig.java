package com.jsainsbury.serversidetest.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix="urls")
public class UrlConfig {

  private String berriesCherriesCurrantsUrl;

  public String getBerriesCherriesCurrantsUrl() {
    return berriesCherriesCurrantsUrl;
  }

  public void setBerriesCherriesCurrantsUrl(String berriesCherriesCurrantsUrl) {
    this.berriesCherriesCurrantsUrl = berriesCherriesCurrantsUrl;
  }
}
