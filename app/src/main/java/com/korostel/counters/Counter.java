package com.korostel.counters;

/**
 * Created by korostel on 16.10.2014.
 */
public class Counter {
    private int id;
    private String name;
    private String unitsMeasure;
    private double rate;
    private String currency;
    private long startValue;

    public Counter() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUnitsMeasure() {
        return unitsMeasure;
    }

    public void setUnitsMeasure(String unitsMeasure) {
        this.unitsMeasure = unitsMeasure;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public long getStartValue() {
        return startValue;
    }

    public void setStartValue(long startValue) {
        this.startValue = startValue;
    }

    @Override
    public String toString() {
        return "Counter{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", unitsMeasure='" + unitsMeasure + '\'' +
                ", rate=" + rate +
                ", currency='" + currency + '\'' +
                ", startValue=" + startValue +
                '}';
    }
}
