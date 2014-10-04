package com.korostel.counters.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.korostel.counters.data.CountersContract.CountersEntry;
import com.korostel.counters.data.CountersContract.ValuesEntry;

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
                CountersEntry.COLUMN_COUNT_INT_BITS + " INTEGER NOT NULL," +
                CountersEntry.COLUMN_COUNT_DEC_BITS + " INTEGER NOT NULL," +
                CountersEntry.COLUMN_UNITS_MEASURE + " TEXT NOT NULL," +
                CountersEntry.COLUMN_RATE + " REAL NOT NULL," +
                CountersEntry.COLUMN_CURRENCY + " TEXT NOT NULL" + ");");

        Log.d(LOG_TAG, "---CREATE TABLE: " + ValuesEntry.TABLE_NAME);
        db.execSQL("CREATE TABLE " + ValuesEntry.TABLE_NAME + " (" +
                ValuesEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                ValuesEntry.COLUMN_YEAR + " INTEGER NOT NULL," +
                ValuesEntry.COLUMN_MONTH + " TEXT NOT NULL," +
                ValuesEntry.COLUMN_INDICATION + " INTEGER NOT NULL," +
                ValuesEntry.COLUMN_PRICE + " REAL NOT NULL," +
                ValuesEntry.COLUMN_COUNTER_ID + " INTEGER NOT NULL," +
                "FOREIGN KEY(" + ValuesEntry.COLUMN_COUNTER_ID + ") " +
                    "REFERENCES " + CountersEntry.TABLE_NAME + "(" + CountersEntry.COLUMN_ID + ")" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
