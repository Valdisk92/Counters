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


public class AddCounterActivity extends Activity {

    private static final String LOG_TAG = AddCounterActivity.class.getSimpleName();
    EditText etAddCounterName, etAddCounterIntBits, etAddCounterFracBits, etAddCounterUnitsMeasure,
             etAddCounterRate, etAddCounterCurrency;

    TextView tvAddCounterDecBits;
    Switch switchAddCounterShowDecBits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_counter);

        etAddCounterName = (EditText)findViewById(R.id.etAddCounterName);
        etAddCounterIntBits = (EditText)findViewById(R.id.etAddCounterIntBits);
        etAddCounterFracBits = (EditText)findViewById(R.id.etAddCounterFracBits);
        etAddCounterUnitsMeasure = (EditText)findViewById(R.id.etAddCounterUnitsMeasure);
        etAddCounterRate = (EditText)findViewById(R.id.etAddCounterRate);
        etAddCounterCurrency = (EditText)findViewById(R.id.etAddCounterCurrency);
        setOnTextChangeListener(etAddCounterName);
        setOnTextChangeListener(etAddCounterIntBits);
        setOnTextChangeListener(etAddCounterFracBits);
        setOnTextChangeListener(etAddCounterUnitsMeasure);
        setOnTextChangeListener(etAddCounterRate);
        setOnTextChangeListener(etAddCounterCurrency);


        tvAddCounterDecBits = (TextView)findViewById(R.id.tvAddCounterDecBits);

        switchAddCounterShowDecBits = (Switch)findViewById(R.id.switchAddCounterShowDecBits);


        etAddCounterFracBits.setVisibility(View.GONE);
        tvAddCounterDecBits.setVisibility(View.GONE);

        switchAddCounterShowDecBits.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tvAddCounterDecBits.setVisibility(View.VISIBLE);
                    etAddCounterFracBits.setVisibility(View.VISIBLE);
                    switchAddCounterShowDecBits.setText("Вкл");
                } else {
                    tvAddCounterDecBits.setVisibility(View.GONE);
                    etAddCounterFracBits.setVisibility(View.GONE);
                    switchAddCounterShowDecBits.setText("Выкл");
                }
            }
        });

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
        contentValues.put(CountersEntry.COLUMN_COUNT_INT_BITS, Integer.parseInt(etAddCounterIntBits.getText().toString()));
        if (etAddCounterFracBits.getText().toString().isEmpty()) {
            contentValues.put(CountersEntry.COLUMN_COUNT_FRAC_BITS, 0);
        } else {
            contentValues.put(CountersEntry.COLUMN_COUNT_FRAC_BITS, Integer.parseInt(etAddCounterFracBits.getText().toString()));
        }
        contentValues.put(CountersEntry.COLUMN_UNITS_MEASURE, etAddCounterUnitsMeasure.getText().toString());
        contentValues.put(CountersEntry.COLUMN_RATE, Double.parseDouble(etAddCounterRate.getText().toString()));
        contentValues.put(CountersEntry.COLUMN_CURRENCY, etAddCounterCurrency.getText().toString());

        long rowId = db.insert(CountersEntry.TABLE_NAME, contentValues);
        Log.d(LOG_TAG, "row inserted: ID = " + rowId);
    }

    private boolean checkEditText() {
        boolean state = true;
        if (etAddCounterName.getText().toString().equals("")) {
            etAddCounterName.setBackgroundResource(R.drawable.edit_text_error_bg);
            state = false;
        }
        if (etAddCounterIntBits.getText().toString().isEmpty()) {
            etAddCounterIntBits.setBackgroundResource(R.drawable.edit_text_error_bg);
            state = false;
        }
        if (switchAddCounterShowDecBits.isChecked() && etAddCounterFracBits.getText().toString().isEmpty()) {
            etAddCounterFracBits.setBackgroundResource(R.drawable.edit_text_error_bg);
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
