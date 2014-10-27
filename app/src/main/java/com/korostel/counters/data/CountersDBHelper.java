package com.korostel.counters.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.korostel.counters.data.CountersContract.CountersEntry;
import com.korostel.counters.data.CountersContract.IndicationsEntry;

/**
 * Created by korostel on 07.09.14.
 */
public class CountersDBHelper extends SQLiteOpenHelper {

    private static final String LOG_TAG = CountersDBHelper.class.getSimpleName();

    public CountersDBHelper(Context context) {
        super(context, CountersContract.DB_NAME, null, CountersContract.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(LOG_TAG, "---onCreate:");
        Log.d(LOG_TAG, "---CREATE TABLE: " + CountersEntry.TABLE_NAME);
        db.execSQL("CREATE TABLE " + CountersEntry.TABLE_NAME + " (" +
                CountersEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                CountersEntry.COLUMN_NAME + " TEXT NOT NULL," +
                CountersEntry.COLUMN_UNITS_MEASURE + " TEXT NOT NULL," +
                CountersEntry.COLUMN_RATE + " REAL NOT NULL," +
                CountersEntry.COLUMN_CURRENCY + " TEXT NOT NULL," +
                CountersEntry.COLUMN_START_VALUE + " INTEGER NOT NULL);");

        Log.d(LOG_TAG, "---CREATE TABLE: " + IndicationsEntry.TABLE_NAME);
        db.execSQL("CREATE TABLE " + IndicationsEntry.TABLE_NAME + " (" +
                IndicationsEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                IndicationsEntry.COLUMN_YEAR + " INTEGER NOT NULL," +
                IndicationsEntry.COLUMN_MONTH + " INTEGER NOT NULL," +
                IndicationsEntry.COLUMN_DATE + " INTEGER NOT NULL," +
                IndicationsEntry.COLUMN_PREVIOUS_INDICATION + " INTEGER NOT NULL," +
                IndicationsEntry.COLUMN_CURRENT_INDICATION + " INTEGER NOT NULL," +
                IndicationsEntry.COLUMN_PRICE + " REAL NOT NULL," +
                IndicationsEntry.COLUMN_COUNTER_ID + " INTEGER NOT NULL," +
                "FOREIGN KEY(" + IndicationsEntry.COLUMN_COUNTER_ID + ") " +
                    "REFERENCES " + CountersEntry.TABLE_NAME + "(" + CountersEntry.COLUMN_ID + ")" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
