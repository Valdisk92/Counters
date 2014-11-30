package com.korostel.counters;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.korostel.counters.data.CountersContract.*;
import com.korostel.counters.data.CountersDBHelper;
import com.korostel.counters.data.DB;

//TODO Сделать проверку на входные значения.
//TODO Сделать шрифт боле ли менее нормального и адекватного размера
public class AddCounterActivity extends Activity {

    private static final String LOG_TAG = AddCounterActivity.class.getSimpleName();
    EditText etAddCounterName, etAddCounterUnitsMeasure,
             etAddCounterRate, etAddCounterCurrency, etAddCounterStartValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_counter);

        etAddCounterName = (EditText)findViewById(R.id.etAddCounterName);
        etAddCounterUnitsMeasure = (EditText)findViewById(R.id.etAddCounterUnitsMeasure);
        etAddCounterRate = (EditText)findViewById(R.id.etAddCounterRate);
        etAddCounterCurrency = (EditText)findViewById(R.id.etAddCounterCurrency);
        etAddCounterStartValue = (EditText)findViewById(R.id.etAddCounterStartValue);
        setOnTextChangeListener(etAddCounterName);
        setOnTextChangeListener(etAddCounterUnitsMeasure);
        setOnTextChangeListener(etAddCounterRate);
        setOnTextChangeListener(etAddCounterCurrency);
        setOnTextChangeListener(etAddCounterStartValue);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_counter, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add_counter_confirm) {
            if (checkEditText()) {
                addToDb();
                setResult(RESULT_OK);
                finish();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addToDb() {
        Log.d(LOG_TAG, "---addToDb():");
        DB db = DB.getInstance(this);
        ContentValues contentValues = new ContentValues();

        contentValues.put(CountersEntry.COLUMN_NAME, etAddCounterName.getText().toString());
        contentValues.put(CountersEntry.COLUMN_UNITS_MEASURE, etAddCounterUnitsMeasure.getText().toString());
        contentValues.put(CountersEntry.COLUMN_RATE, Double.parseDouble(etAddCounterRate.getText().toString()));
        contentValues.put(CountersEntry.COLUMN_CURRENCY, etAddCounterCurrency.getText().toString());
        contentValues.put(CountersEntry.COLUMN_START_VALUE, String.valueOf(etAddCounterStartValue.getText().toString()));

        long rowId = db.insert(CountersEntry.TABLE_NAME, contentValues);
        Log.d(LOG_TAG, "row inserted: ID = " + rowId);
    }

    private boolean checkEditText() {
        boolean state = true;
        if (etAddCounterName.getText().toString().isEmpty()) {
            etAddCounterName.setBackgroundResource(R.drawable.edit_text_error_bg);
            state = false;
        }
        if (etAddCounterUnitsMeasure.getText().toString().isEmpty()) {
            etAddCounterUnitsMeasure.setBackgroundResource(R.drawable.edit_text_error_bg);
            state = false;
        }
        if (etAddCounterRate.getText().toString().isEmpty()) {
            etAddCounterRate.setBackgroundResource(R.drawable.edit_text_error_bg);
            state = false;
        }
        if (etAddCounterCurrency.getText().toString().isEmpty()) {
            etAddCounterCurrency.setBackgroundResource(R.drawable.edit_text_error_bg);
            state = false;
        }
        if (etAddCounterStartValue.getText().toString().isEmpty()) {
            etAddCounterStartValue.setBackgroundResource(R.drawable.edit_text_error_bg);
            state = false;
        }

        return state;
    }

    private void setOnTextChangeListener(final EditText editText) {
        final Drawable originalBG = editText.getBackground();
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                editText.setBackground(originalBG);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}
