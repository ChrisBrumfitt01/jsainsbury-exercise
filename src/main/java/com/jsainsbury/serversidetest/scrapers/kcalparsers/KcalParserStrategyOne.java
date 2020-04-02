package com.jsainsbury.serversidetest.scrapers.kcalparsers;

import java.util.Optional;
import org.apache.commons.lang3.math.NumberUtils;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

@Component
public class KcalParserStrategyOne extends KcalParser {

  /**
   * Attempts to parse the KCAL per 100g within a given web element
   * @param element The element to parse
   * @return The Kcal per 100g for the product
   */
  @Override
  public Optional<Integer> getKcalPer100g(Element element) {
    Optional<String> nutritionalInfo = getNutritionalInfo(element);

    if(nutritionalInfo.isPresent()) {
      String[] infoParts = nutritionalInfo.get().split("kcal")[0]
          .split(" ");

      String kcal = infoParts[infoParts.length-1];

      return NumberUtils.isCreatable(kcal) ?
          Optional.of(Integer.parseInt(kcal)) :
          Optional.empty();
    }

    return Optional.empty();
  }
}
