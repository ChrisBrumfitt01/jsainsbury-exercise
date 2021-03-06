package com.jsainsbury.serversidetest.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class Total {

    private final BigDecimal gross;
    private final BigDecimal vat;

    public Total(BigDecimal gross, BigDecimal vat) {
        this.gross = gross.setScale(2, RoundingMode.HALF_UP);
        this.vat = vat.setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getGross() {
        return gross;
    }

    public BigDecimal getVat() {
        return vat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Total total = (Total) o;
        return Objects.equals(gross, total.gross) &&
            Objects.equals(vat, total.vat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gross, vat);
    }
}
