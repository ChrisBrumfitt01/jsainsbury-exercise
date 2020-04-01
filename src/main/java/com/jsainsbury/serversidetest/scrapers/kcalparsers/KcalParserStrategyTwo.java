package com.jsainsbury.serversidetest.scrapers.kcalparsers;

import java.util.Optional;
import org.apache.commons.lang3.math.NumberUtils;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

@Component
public class KcalParserStrategyTwo extends KcalParser {
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
