package com.jsainsbury.serversidetest.scrapers.kcalparsers;

import static org.assertj.core.api.Assertions.assertThat;


import java.util.Optional;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class KcalParserStrategyOneTest {

  @Autowired
  private KcalParserStrategyOne parser;

  private Element body;

  @Before
  public void before() {
    Element kcalElement = new Element("div").text("Some info 33kcal more info");

    Element nutritionTable = new Element("div").addClass("nutritionTable").insertChildren(0, kcalElement);
    Elements nutritionTables = new Elements(nutritionTable);

    body = new Element("body");
    body.insertChildren(0, nutritionTables);
  }

  @Test
  public void shouldParseExpectedKcalValue() {
    Optional<Integer> value = parser.getKcalPer100g(body);
    assertThat(value).contains(33);
  }


}
