package org.telegram.utils;

import java.util.Objects;

public class CurrencyDetails {
    private String toCountry;
    private String fromCountry;
    private double amountCurrency;

    public CurrencyDetails(String toCountry, String fromCountry, double amountCurrency) {
        this.toCountry = toCountry;
        this.fromCountry = fromCountry;
        this.amountCurrency = amountCurrency;
    }

    public CurrencyDetails(String toCountry, String fromCountry) {
        this.toCountry = toCountry;
        this.fromCountry = fromCountry;
    }

    public String getToCountry() {
        return toCountry;
    }

    public void setToCountry(String toCountry) {
        this.toCountry = toCountry;
    }

    public String getFromCountry() {
        return fromCountry;
    }

    public void setFromCountry(String fromCountry) {
        this.fromCountry = fromCountry;
    }

    public double getAmountCurrency() {
        return amountCurrency;
    }

    public void setAmountCurrency(double amountCurrency) {
        this.amountCurrency = amountCurrency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CurrencyDetails that = (CurrencyDetails) o;
        return Double.compare(that.amountCurrency, amountCurrency) == 0 && Objects.equals(toCountry, that.toCountry) && Objects.equals(fromCountry, that.fromCountry);
    }

    @Override
    public int hashCode() {
        return Objects.hash(toCountry, fromCountry, amountCurrency);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CurrencyDetails{");
        sb.append("toCountry='").append(toCountry).append('\'');
        sb.append(", fromCountry='").append(fromCountry).append('\'');
        sb.append(", amountCurrency=").append(amountCurrency);
        sb.append('}');
        return sb.toString();
    }
}
