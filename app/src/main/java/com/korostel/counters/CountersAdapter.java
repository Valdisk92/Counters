package com.korostel.counters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.korostel.counters.data.CountersContract.*;
import com.korostel.counters.data.DB;

import java.util.ArrayList;

/**
 * Created by korostel on 16.10.2014.
 */
public class CountersAdapter extends BaseAdapter {

    private static final String LOG_TAG = CountersAdapter.class.getSimpleName();

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private ArrayList<Counter> mCounters;


    public CountersAdapter(Context context) {
        mContext = context;
        mLayoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mCounters = getAllCounters();
    }

    private ArrayList<Counter> getAllCounters() {
        DB db = DB.getInstance(mContext);
        ArrayList<Counter> counters = new ArrayList<Counter>();
        Cursor cursor = db.getAllCounters();
        if (cursor.moveToFirst()) {
            do {
                Counter counter = new Counter();
                counter.setName(cursor.getString(cursor.getColumnIndex(CountersEntry.COLUMN_NAME)));
                counter.setId(cursor.getInt(cursor.getColumnIndex(CountersEntry.COLUMN_ID)));
                counter.setUnitsMeasure(cursor.getString(cursor.getColumnIndex(CountersEntry.COLUMN_UNITS_MEASURE)));
                counter.setRate(cursor.getDouble(cursor.getColumnIndex(CountersEntry.COLUMN_RATE)));
                counter.setCurrency(cursor.getString(cursor.getColumnIndex(CountersEntry.COLUMN_CURRENCY)));
                counter.setStartValue(cursor.getLong(cursor.getColumnIndex(CountersEntry.COLUMN_START_VALUE)));
                counters.add(counter);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return counters;
    }

    @Override
    public int getCount() {
        return mCounters.size();
    }

    @Override
    public Object getItem(int i) {
        return mCounters.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = view;
        if (v == null) {
            v = mLayoutInflater.inflate(R.layout.item_counter, viewGroup, false);
        }

        Counter counter = getCounter(i);
        ((TextView)v.findViewById(R.id.tvListCountersName)).setText(counter.getName());

        return v;
    }

    private Counter getCounter(int i) {
        return ((Counter)getItem(i));
    }

    public void updateAdapter() {
        mCounters = getAllCounters();
        notifyDataSetChanged();
    }
}
