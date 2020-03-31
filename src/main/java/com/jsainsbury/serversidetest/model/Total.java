package com.jsainsbury.serversidetest.model;

import java.util.Objects;

public class Total {

    private final double gross;
    private final double vat;

    public Total(double gross, double vat) {
        this.gross = gross;
        this.vat = vat;
    }

    public double getGross() {
        return gross;
    }

    public double getVat() {
        return vat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Total total = (Total) o;
        return Double.compare(total.getGross(), getGross()) == 0 &&
                Double.compare(total.getVat(), getVat()) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getGross(), getVat());
    }
}
