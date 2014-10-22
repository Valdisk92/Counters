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
    private int mDate;

    public Indication() {

    }

    public String getStringMonth() {
        String month;
        switch (mMonth) {
            case 1: month = "Янаварь";
                break;
            case 2: month = "Февраль";
                break;
            case 3: month = "Март";
                break;
            case 4: month = "Апрель";
                break;
            case 5: month = "Май";
                break;
            case 6: month = "Июнь";
                break;
            case 7: month = "Июль";
                break;
            case 8: month = "Август";
                break;
            case 9: month = "Сентябрь";
                break;
            case 10: month = "Октябрь";
                break;
            case 11: month = "Ноябрь";
                break;
            case 12: month = "Декабрь";
                break;
            default: month = "Default";
        }

        return month;
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

    public int getDate() {
        return mDate;
    }

    public void setDate(int mDate) {
        this.mDate = mDate;
    }
}
