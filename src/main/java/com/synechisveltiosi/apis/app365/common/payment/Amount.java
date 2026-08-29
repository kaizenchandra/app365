package com.synechisveltiosi.apis.app365.common.payment;

public class Amount {

    private double total = 0.0;
    private Currency currency = Currency.USD;

    public Amount() {
        this(0.0);
    }

    public Amount(double total) {
        this(total, Currency.USD);
    }

    public Amount(double total, Currency currency) {
        this.setTotal(total);
        this.setCurrency(currency);
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        if (total < 0) throw new IllegalArgumentException("Amount total should be >= 0");

        this.total = total;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
