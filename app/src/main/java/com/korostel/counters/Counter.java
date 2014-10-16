package com.korostel.counters;

import java.util.ArrayList;

/**
 * Created by Владислав on 15.10.2014.
 */
public class Counter {

    int id;
    String name;
    int intBits;
    int fracBits;
    String unitsMeasure;
    double rate;
    String currency;
    ArrayList<Indication> indications;

    public Counter() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
