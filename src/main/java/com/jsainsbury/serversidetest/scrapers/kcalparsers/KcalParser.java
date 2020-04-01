package com.jsainsbury.serversidetest.scrapers.kcalparsers;

import java.util.Optional;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public abstract class KcalParser {
  public abstract Optional<Integer> getKcalPer100g(Element element);

  Optional<String> getNutritionalInfo(Element element) {
    Elements nutritionalTable = element.getElementsByClass("nutritionTable");
    if(nutritionalTable.isEmpty()) {
      return Optional.empty();
    }

    return Optional.of(
        nutritionalTable.get(0)
        .getElementsContainingText("kcal").get(0)
        .text()
    );
  }
}
