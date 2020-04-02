package com.jsainsbury.serversidetest.services;

import com.jsainsbury.serversidetest.config.PropsConfig;
import com.jsainsbury.serversidetest.model.Product;
import com.jsainsbury.serversidetest.model.ProductResults;
import com.jsainsbury.serversidetest.model.ProductSummary;
import com.jsainsbury.serversidetest.model.Total;
import com.jsainsbury.serversidetest.scrapers.BerriesCherriesCurrantsScraper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class BerriesCherriesCurrantsService {

    @Autowired private BerriesCherriesCurrantsScraper berriesCherriesCurrantsScraper;
    @Autowired private PropsConfig propsConfig;

    @Autowired private ConcurrentProductService concurrentProductService;

    /**
     * Parses the Berries, Cherries & Currants page and returns details for the products on the page
     * @return The ProductResults
     */
    public ProductResults getProductDetails() {
        List<ProductSummary> productSummaries = berriesCherriesCurrantsScraper.getProducts();
        List<Product> products = concurrentProductService.getProducts(productSummaries);

        double total = products.stream()
                .mapToDouble(product -> product.getUnitPrice().doubleValue())
                .sum();

        return new ProductResults(products, new Total(BigDecimal.valueOf(total), calculateVAT(total)));
    }

    private BigDecimal calculateVAT(double total) {
        BigDecimal divisor = BigDecimal.ONE
                .divide(BigDecimal.valueOf(propsConfig.getVatRate()), 2, RoundingMode.HALF_UP)
                .add(BigDecimal.ONE);

        return BigDecimal.valueOf(total)
                .divide(divisor, 2, RoundingMode.HALF_UP);
    }



}
