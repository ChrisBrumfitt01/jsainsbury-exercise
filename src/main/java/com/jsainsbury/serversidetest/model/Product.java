package com.jsainsbury.serversidetest.model;

import java.util.Objects;
import java.util.Optional;

public class Product {

    private final String title;
    private final Integer kcalPer100g;
    private final double unitPrice;
    private final String description;

    public Product(String title, Integer kcalPer100g, double unitPrice, String description) {
        this.title = title;
        this.kcalPer100g = kcalPer100g;
        this.unitPrice = unitPrice;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public Optional<Integer> getKcalPer100g() {
        return Optional.ofNullable(kcalPer100g);
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Double.compare(product.getUnitPrice(), getUnitPrice()) == 0 &&
                Objects.equals(getTitle(), product.getTitle()) &&
                Objects.equals(getKcalPer100g(), product.getKcalPer100g()) &&
                Objects.equals(getDescription(), product.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle(), getKcalPer100g(), getUnitPrice(), getDescription());
    }
}
