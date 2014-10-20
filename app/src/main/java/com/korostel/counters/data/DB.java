package com.korostel.counters.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by korostel on 11.09.2014.
 */
public class DB {
    private static DB instance;

    private static Context mContext;
    private static CountersDBHelper dbHelper;
    private static SQLiteDatabase database;

    private DB() {
    }

    public static DB getInstance(Context context) {
        if (instance == null) {
            instance = new DB();
            mContext = context;
        }

        return instance;
    }

    private void open() {
        if (dbHelper == null) {
            dbHelper = new CountersDBHelper(mContext);
            database = dbHelper.getWritableDatabase();
        }
    }

    public void clearDB() {
        this.open();
        database.execSQL("DELETE FROM " + CountersContract.CountersEntry.TABLE_NAME + ";");
        database.execSQL("VACUUM;");
        database.execSQL("DELETE FROM " + CountersContract.IndicationsEntry.TABLE_NAME + ";");
        database.execSQL("VACUUM;");
        //this.close();
    }

    public Cursor getAllCounters() {
        this.open();
        Cursor cursor = database.query(
                CountersContract.CountersEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );
        //this.close();
        return cursor;
    }

    public Cursor getIndicationsByCounterId(int counterId) {
        this.open();
        Cursor cursor = database.query(
                CountersContract.IndicationsEntry.TABLE_NAME,
                null,
                CountersContract.IndicationsEntry.COLUMN_COUNTER_ID + " = ?",
                new String[]{String.valueOf(counterId)},
                null,
                null,
                null
        );
        return cursor;
    }

    public long insert(String tableName, ContentValues contentValues) {
        this.open();
        long rowId = database.insert(tableName, null, contentValues);
        //this.close();
        return rowId;
    }

    private void close() {
        dbHelper.close();
    }
}
