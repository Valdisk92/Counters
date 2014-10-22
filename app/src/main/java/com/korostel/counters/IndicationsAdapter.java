package com.korostel.counters;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.korostel.counters.data.CountersContract;
import com.korostel.counters.data.DB;
import com.korostel.counters.data.CountersContract.IndicationsEntry;

import java.util.LinkedList;

/**
 * Created by korostel on 18.10.2014.
 */
public class IndicationsAdapter extends BaseAdapter {

    private static final String LOG_TAG = IndicationsAdapter.class.getSimpleName();

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private LinkedList<Indication> mIndications;
    private int mCounterId;
//    private int counterIntBits;
//    private int counterFracBits;
    private String counterUnitsMeasure;
    private String counterCurrency;

    public IndicationsAdapter(Context context, int counterId, Bundle bundle) {
        mContext = context;
        mLayoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mCounterId = counterId;
        mIndications = getAllIndications();
//        counterIntBits = bundle.getInt(CountersContract.CountersEntry.COLUMN_COUNT_INT_BITS, 0);
//        counterFracBits = bundle.getInt(CountersContract.CountersEntry.COLUMN_COUNT_FRAC_BITS, 0);
        counterUnitsMeasure = bundle.getString(CountersContract.CountersEntry.COLUMN_UNITS_MEASURE);
        counterCurrency = bundle.getString(CountersContract.CountersEntry.COLUMN_CURRENCY);
    }

    private LinkedList<Indication> getAllIndications() {
        DB db = DB.getInstance(mContext);
        LinkedList<Indication> indications = new LinkedList<Indication>();
        Cursor cursor = db.getIndicationsByCounterId(mCounterId);
        if (cursor.moveToFirst()) {
            do {
                Indication indication = new Indication();
                indication.setIndicationId(cursor.getInt(cursor.getColumnIndex(IndicationsEntry.COLUMN_ID)));
                indication.setPrevIndicationValue(cursor.getInt(cursor.getColumnIndex(IndicationsEntry.COLUMN_PREVIOUS_INDICATION)));
                indication.setCurrIndicationValue(cursor.getInt(cursor.getColumnIndex(IndicationsEntry.COLUMN_CURRENT_INDICATION)));
                indication.setPrice(cursor.getDouble(cursor.getColumnIndex(IndicationsEntry.COLUMN_PRICE)));
                indication.setYear(cursor.getInt(cursor.getColumnIndex(IndicationsEntry.COLUMN_YEAR)));
                indication.setMonth(cursor.getInt(cursor.getColumnIndex(IndicationsEntry.COLUMN_MONTH)));
                indication.setDate(cursor.getInt(cursor.getColumnIndex(IndicationsEntry.COLUMN_DATE)));
                indication.setCounterId(cursor.getInt(cursor.getColumnIndex(IndicationsEntry.COLUMN_COUNTER_ID)));
                indications.push(indication);
            } while (cursor.moveToNext());
        } else {
            Log.d(LOG_TAG, "CURSOR IS EMPTY");
        }

        Log.d(LOG_TAG, indications.toString());

        return indications;
    }

    @Override
    public int getCount() {
        return mIndications.size();
    }

    @Override
    public Object getItem(int position) {
        return mIndications.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View v = view;
        if (v == null) {
            v = mLayoutInflater.inflate(R.layout.item_indication, viewGroup, false);
        }

        Indication indication = getIndication(position);
        ((TextView)v.findViewById(R.id.tvIndicationItemCurrIndValue)).setText(indication.getCurrIndicationValue() + " " + counterUnitsMeasure + ".");
        ((TextView)v.findViewById(R.id.tvIndicationItemPrice)).setText(indication.getPrice() + " " + counterCurrency + ".");
        ((TextView)v.findViewById(R.id.tvIndicationItemYear)).setText(indication.getYear() + "");
        ((TextView)v.findViewById(R.id.tvIndicationItemMonth)).setText(indication.getStringMonth());
        ((TextView)v.findViewById(R.id.tvIndicationItemDate)).setText(indication.getDate() + "");
        return v;
    }

    private Indication getIndication(int position) {
        return ((Indication)getItem(position));
    }

    public void updateAdapter() {
        mIndications = getAllIndications();
        notifyDataSetChanged();
    }
}
