package com.jsainsbury.serversidetest.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix="props")
public class PropsConfig {

  private double vatRate;

  public double getVatRate() {
    return vatRate;
  }

  public void setVatRate(double vatRate) {
    this.vatRate = vatRate;
  }
}
