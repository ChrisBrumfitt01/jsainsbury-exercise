package com.jsainsbury.serversidetest.services;

import com.jsainsbury.serversidetest.model.Product;
import com.jsainsbury.serversidetest.model.ProductSummary;
import com.jsainsbury.serversidetest.scrapers.ProductScraper;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConcurrentProductService {

  private static final Logger LOG = Logger.getLogger(ConcurrentProductService.class.getName());

  private static final int TIMEOUT_SECS = 3;

  @Autowired private ProductScraper productScraper;

  public List<Product> getProducts(List<ProductSummary> productSummaries) {
    ExecutorService executorService = null;
    try {
      executorService = Executors.newFixedThreadPool(productSummaries.size());

      Collection<Callable<Product>> callables = productSummaries.stream()
          .map(p -> new GetProductCallable(p.getUrl()))
          .collect(Collectors.toList());

      List<Future<Product>> futures;
      try {
        futures = executorService.invokeAll(callables);
      } catch (InterruptedException e) {
        LOG.log(Level.WARNING, "Thread interrupted whilst parsing product pages");
        return List.of();
      }

      List<Product> products = new ArrayList<>();
      futures.forEach(future -> {
        extractProduct(future, products);
      });
      return products;
    } finally {
      executorService.shutdown();
    }
  }


  private void extractProduct(Future<Product> product, List<Product> products) {
    try {
      products.add(
          product.get(TIMEOUT_SECS, TimeUnit.SECONDS)
      );
    } catch (InterruptedException e) {
      LOG.log(Level.WARNING, "Thread interrupted whilst parsing product page");
    } catch (ExecutionException e) {
      LOG.log(Level.SEVERE, "Execution error when parsing product page");
    } catch (TimeoutException e) {
      LOG.log(Level.WARNING, "Timed out when parsing product page");
    }
  }


  private class GetProductCallable implements Callable<Product> {

    private final String url;

    public GetProductCallable(String url) {
      this.url = url;
    }

    @Override
    public Product call() throws Exception {
      return productScraper.getProductDetails(url);
    }
  }



}
