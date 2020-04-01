package com.jsainsbury.serversidetest.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
import java.util.Optional;

public class Product {

    private final String title;
    private final Integer kcalPer100g;
    private final BigDecimal unitPrice;
    private final String description;

    public Product(String title, Integer kcalPer100g, BigDecimal unitPrice, String description) {
        this.title = title;
        this.kcalPer100g = kcalPer100g;
        this.unitPrice = unitPrice.setScale(2, RoundingMode.HALF_UP);
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public Optional<Integer> getKcalPer100g() {
        return Optional.ofNullable(kcalPer100g);
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Product product = (Product) o;
        return Objects.equals(title, product.title) &&
            Objects.equals(kcalPer100g, product.kcalPer100g) &&
            Objects.equals(unitPrice, product.unitPrice) &&
            Objects.equals(description, product.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, kcalPer100g, unitPrice, description);
    }
}
