package com.korostel.counters;

/**
 * Created by Владислав on 15.10.2014.
 */
public class Indication {
    private int mYear;
    private String mMonth;
    private int mIndicationId;
    private long mPrevIndication;
    private long mCurrIndication;
    private double mPrice;
    private double mDifference;
    private int mCounterId;

    public Indication() {

    }

    public int getIndicationId() {
        return mIndicationId;
    }

    public void setIndicationId(int mIndicationId) {
        this.mIndicationId = mIndicationId;
    }

    public int getCounterId() {
        return mCounterId;
    }

    public void setCounterId(int mCounterId) {
        this.mCounterId = mCounterId;
    }

    public int getYear() {
        return mYear;
    }

    public void setYear(int mYear) {
        this.mYear = mYear;
    }

    public String getMonth() {
        return mMonth;
    }

    public void setMonth(String mMonth) {
        this.mMonth = mMonth;
    }

    public long getPrevIndication() {
        return mPrevIndication;
    }

    public void setPrevIndication(long mPrevIndication) {
        this.mPrevIndication = mPrevIndication;
    }

    public long getCurrIndication() {
        return mCurrIndication;
    }

    public void setCurrIndication(long mCurrIndication) {
        this.mCurrIndication = mCurrIndication;
    }

    public double getPrice() {
        return mPrice;
    }

    public void setPrice(double mPrice) {
        this.mPrice = mPrice;
    }

    public double getDifference() {
        return mDifference;
    }

    public void setDifference(double mDifference) {
        this.mDifference = mDifference;
    }
}
