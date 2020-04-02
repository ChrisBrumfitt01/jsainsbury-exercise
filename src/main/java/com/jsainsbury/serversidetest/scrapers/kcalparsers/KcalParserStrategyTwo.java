package com.jsainsbury.serversidetest.scrapers.kcalparsers;

import java.util.Optional;
import org.apache.commons.lang3.math.NumberUtils;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

@Component
public class KcalParserStrategyTwo extends KcalParser {

  /**
   * Attempts to parse the KCAL per 100g within a given web element
   * @param element The element to parse
   * @return The Kcal per 100g for the product
   */
  @Override
  public Optional<Integer> getKcalPer100g(Element element) {
    Optional<String> nutritionalInfo = getNutritionalInfo(element);

    if(nutritionalInfo.isPresent()) {
      String kcal = nutritionalInfo.get().split("kcal")[1]
          .trim()
          .split(" ")[0];

      return NumberUtils.isCreatable(kcal) ?
          Optional.of(Integer.parseInt(kcal)) :
          Optional.empty();
    }

    return Optional.empty();
  }
}
