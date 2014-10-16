package com.korostel.counters;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.korostel.counters.data.DB;

import java.util.ArrayList;

import com.korostel.counters.data.CountersContract.*;

/**
 * Created by Владислав on 15.10.2014.
 */
public class CountersAdapter extends BaseAdapter {

    private static final String LOG_TAG = CountersAdapter.class.getSimpleName();

    DB db;
    Context context;
    ArrayList<Counter> counters;
    LayoutInflater inflater;
    Cursor cursor;

    public CountersAdapter(Context context) {
        this.context = context;
        db = new DB(context);
        counters = getAllCounters();
        inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    private ArrayList<Counter> getAllCounters() {
        db.open();
        ArrayList<Counter> localCounters = new ArrayList<Counter>();
        Counter localCounter;
        cursor = db.getAllData(CountersEntry.TABLE_NAME);
        if (cursor.moveToFirst()) {
            do {
                localCounter = new Counter();
                localCounter.setName(cursor.getString(cursor.getColumnIndex(CountersEntry.COLUMN_NAME)));
                localCounter.setIntBits(cursor.getInt(cursor.getColumnIndex(CountersEntry.COLUMN_COUNT_INT_BITS)));
                localCounter.setFracBits(cursor.getInt(cursor.getColumnIndex(CountersEntry.COLUMN_COUNT_FRAC_BITS)));
                localCounter.setUnitsMeasure(cursor.getString(cursor.getColumnIndex(CountersEntry.COLUMN_UNITS_MEASURE)));
                localCounter.setRate(cursor.getDouble(cursor.getColumnIndex(CountersEntry.COLUMN_RATE)));
                localCounter.setCurrency(cursor.getString(cursor.getColumnIndex(CountersEntry.COLUMN_CURRENCY)));
                localCounter.setId(cursor.getInt(cursor.getColumnIndex(CountersEntry.COLUMN_ID)));
                localCounters.add(localCounter);
                Log.d(LOG_TAG, "Method: getAllCounters(); Counter id = " + localCounter.getId());
            } while (cursor.moveToNext());
        }
        db.close();
        return localCounters;
    }

    @Override
    public int getCount() {
        return counters.size();
    }

    @Override
    public Object getItem(int i) {
        return counters.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.item, viewGroup, false);
        }

        Counter counter = getCounter(i);
        ((TextView)view.findViewById(R.id.tvListCountersName)).setText(counter.getName());

        return view;
    }

    private Counter getCounter(int i) {
        return ((Counter) getItem(i));
    }

    public Cursor getCursor() {
        return cursor;
    }
}
