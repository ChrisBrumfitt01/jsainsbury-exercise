package com.jsainsbury.serversidetest.model;

import java.util.List;
import java.util.Objects;

public class ProductResults {

    private List<Product> results;
    private Total total;

    public ProductResults(List<Product> results, Total total) {
        this.results = results;
        this.total = total;
    }

    public List<Product> getResults() {
        return results;
    }

    public Total getTotal() {
        return total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductResults that = (ProductResults) o;
        return Objects.equals(getResults(), that.getResults()) &&
                Objects.equals(getTotal(), that.getTotal());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getResults(), getTotal());
    }
}
