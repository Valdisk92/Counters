package com.korostel.counters;

import java.util.ArrayList;

/**
 * Created by korostel on 16.10.2014.
 */
public class Counter {
    private int id;
    private String name;
    private int intBits;
    private int fracBits;
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

    public int getIntBits() {
        return intBits;
    }

    public void setIntBits(int intBits) {
        this.intBits = intBits;
    }

    public int getFracBits() {
        return fracBits;
    }

    public void setFracBits(int fracBits) {
        this.fracBits = fracBits;
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
}
