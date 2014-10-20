package com.korostel.counters;

/**
 * Created by Владислав on 15.10.2014.
 */
public class Indication {
    private int mYear;
    private int mMonth;
    private int mIndicationId;
    private long mPrevIndication;
    private long mCurrIndication;
    private double mPrice;
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

    public int getMonth() {
        return mMonth;
    }

    public void setMonth(int mMonth) {
        this.mMonth = mMonth;
    }

    public long getPrevIndicationValue() {
        return mPrevIndication;
    }

    public void setPrevIndicationValue(long mPrevIndication) {
        this.mPrevIndication = mPrevIndication;
    }

    public long getCurrIndicationValue() {
        return mCurrIndication;
    }

    public void setCurrIndicationValue(long mCurrIndication) {
        this.mCurrIndication = mCurrIndication;
    }

    public double getPrice() {
        return mPrice;
    }

    public void setPrice(double mPrice) {
        this.mPrice = mPrice;
    }
}
