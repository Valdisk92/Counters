package com.korostel.counters.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.SimpleCursorAdapter;

import com.korostel.counters.R;

/**
 * Created by korostel on 11.09.2014.
 */
public class DB {
    private Context context;
    private CountersDBHelper dbHelper;
    SQLiteDatabase database;

    public DB(Context context) {
        this.context = context;
    }

    public void open() {
        dbHelper = new CountersDBHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    public Cursor getAllData(String tableName) {
        return database.query(tableName, null, null, null, null, null, null);
    }

    public void close() {
        dbHelper.close();
    }
}
