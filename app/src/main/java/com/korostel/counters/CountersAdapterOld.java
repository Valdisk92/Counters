package com.korostel.counters;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.korostel.counters.data.CountersContract;

/**
 * Created by korostel on 16.10.2014.
 */
public class CountersAdapterOld extends CursorAdapter {

    private LayoutInflater mLayoutInflater;
    private Context mContext;

    public CountersAdapterOld(Context context, Cursor c) {
        super(context, c);
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View view = mLayoutInflater.inflate(R.layout.item, viewGroup, false);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        String name = cursor.getString(cursor.getColumnIndex(CountersContract.CountersEntry.COLUMN_NAME));

        ((TextView)view.findViewById(R.id.tvListCountersName)).setText(name);
    }
}
