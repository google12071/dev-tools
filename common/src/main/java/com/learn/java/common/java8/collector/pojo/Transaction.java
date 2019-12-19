package com.learn.java.common.java8.collector.pojo;

public class Transaction {
    private Currency currency;
    private Double value;

    public Transaction(Currency currency, Double value) {
        this.currency = currency;
        this.value = value;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                       "currency=" + currency +
                       ", value=" + value +
                       '}';
    }
}
