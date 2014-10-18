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
import com.korostel.counters.data.CountersContract.IndicationsEntry;

import java.util.ArrayList;

/**
 * Created by korostel on 18.10.2014.
 */
public class IndicationsAdapter extends BaseAdapter {

    private static final String LOG_TAG = IndicationsAdapter.class.getSimpleName();

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private ArrayList<Indication> mIndications;
    private int mCounterId;

    public IndicationsAdapter(Context context, int counterId) {
        mContext = context;
        mLayoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mCounterId = counterId;
        mIndications = getAllIndications();
    }

    private ArrayList<Indication> getAllIndications() {
        DB db = DB.getInstance(mContext);
        ArrayList<Indication> indications = new ArrayList<Indication>();
        Cursor cursor = db.getIndicationsByCounterId(mCounterId);
        if (cursor.moveToFirst()) {
            do {
                Indication indication = new Indication();
                indication.setIndicationId(cursor.getInt(cursor.getColumnIndex(IndicationsEntry.COLUMN_ID)));
                indication.setPrevIndication(cursor.getInt(cursor.getColumnIndex(IndicationsEntry.COLUMN_PREVIOUS_INDICATION)));
                indication.setCurrIndication(cursor.getInt(cursor.getColumnIndex(IndicationsEntry.COLUMN_CURRENT_INDICATION)));
                indication.setPrice(cursor.getDouble(cursor.getColumnIndex(IndicationsEntry.COLUMN_PRICE)));
                indication.setYear(cursor.getInt(cursor.getColumnIndex(IndicationsEntry.COLUMN_YEAR)));
                indication.setMonth(cursor.getString(cursor.getColumnIndex(IndicationsEntry.COLUMN_MONTH)));
                indication.setCounterId(cursor.getInt(cursor.getColumnIndex(IndicationsEntry.COLUMN_COUNTER_ID)));
            } while (cursor.moveToNext());
        } else {
            Log.d(LOG_TAG, "CURSOR IS EMPTY");
        }

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
        ((TextView)v.findViewById(R.id.tvListIndicationIndication)).setText(indication.getCurrIndication() + "");
        //TODO Добавить все значения показаний
        return v;
    }

    private Indication getIndication(int position) {
        return ((Indication)getItem(position));
    }
}
