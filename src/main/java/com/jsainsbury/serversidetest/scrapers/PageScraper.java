package com.jsainsbury.serversidetest.scrapers;

import com.jsainsbury.serversidetest.model.ProductSummary;
import java.util.List;

public interface PageScraper {
  List<ProductSummary> getProducts();
}
