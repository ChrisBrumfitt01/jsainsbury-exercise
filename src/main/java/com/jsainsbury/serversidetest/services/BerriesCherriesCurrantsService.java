package com.jsainsbury.serversidetest.services;

import com.jsainsbury.serversidetest.config.PropsConfig;
import com.jsainsbury.serversidetest.model.Product;
import com.jsainsbury.serversidetest.model.ProductResults;
import com.jsainsbury.serversidetest.model.Total;
import com.jsainsbury.serversidetest.scrapers.BerriesCherriesCurrantsScraper;
import com.jsainsbury.serversidetest.scrapers.ProductScraper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BerriesCherriesCurrantsService {

    @Autowired private BerriesCherriesCurrantsScraper berriesCherriesCurrantsScraper;
    @Autowired private ProductScraper productScraper;
    @Autowired private PropsConfig propsConfig;

    public ProductResults getProductDetails() {
        List<Product> products = berriesCherriesCurrantsScraper.getProducts().stream()
                .map(summary -> productScraper.getProductDetails(summary.getUrl()))
                .collect(Collectors.toList());

        double total = products.stream()
                .mapToDouble(Product::getUnitPrice)
                .sum();

        return new ProductResults(products, new Total(total, calculateVAT(total)));
    }

    private double calculateVAT(double total) {
        BigDecimal divisor = BigDecimal.ONE
                .divide(BigDecimal.valueOf(propsConfig.getVatRate()), 2, RoundingMode.HALF_UP)
                .add(BigDecimal.ONE);

        return BigDecimal.valueOf(total)
                .divide(divisor, 2, RoundingMode.HALF_UP)
                .doubleValue();
    }

}
