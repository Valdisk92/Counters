package com.korostel.counters;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import com.korostel.counters.data.CountersContract;
import com.korostel.counters.data.DB;


public class ChangeCounterActivity extends Activity {

    public static final String LOG_TAG = ChangeCounterActivity.class.getSimpleName();

    EditText etChangeCounterName, etChangeCounterUnitsMeasure,
            etChangeCounterRate, etChangeCounterCurrency;
    int counterId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_counter);
        counterId = getIntent().getIntExtra("counterId", -1);

        etChangeCounterName = (EditText)findViewById(R.id.etChangeCounterName);
        etChangeCounterUnitsMeasure = (EditText)findViewById(R.id.etChangeCounterUnitsMeasure);
        etChangeCounterRate = (EditText)findViewById(R.id.etChangeCounterRate);
        etChangeCounterCurrency = (EditText)findViewById(R.id.etChangeCounterCurrency);
        setOnTextChangeListener(etChangeCounterName);
        setOnTextChangeListener(etChangeCounterUnitsMeasure);
        setOnTextChangeListener(etChangeCounterRate);
        setOnTextChangeListener(etChangeCounterCurrency);

        Counter counter = getCounter(counterId);
        counter.toString();
        initField(counter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_change_counter, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_change_counter_confirm:
                if (checkEditText()) {
                    setResult(RESULT_OK);
                    finish();
                }
                break;
        }


        return super.onOptionsItemSelected(item);
    }

    private boolean checkEditText() {
        boolean state = true;
        if (etChangeCounterName.getText().toString().isEmpty()) {
            etChangeCounterName.setBackgroundResource(R.drawable.edit_text_error_bg);
            state = false;
        }
        if (etChangeCounterUnitsMeasure.getText().toString().isEmpty()) {
            etChangeCounterUnitsMeasure.setBackgroundResource(R.drawable.edit_text_error_bg);
            state = false;
        }
        if (etChangeCounterRate.getText().toString().isEmpty()) {
            etChangeCounterRate.setBackgroundResource(R.drawable.edit_text_error_bg);
            state = false;
        }
        if (etChangeCounterCurrency.getText().toString().isEmpty()) {
            etChangeCounterCurrency.setBackgroundResource(R.drawable.edit_text_error_bg);
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

    private void initField(Counter counter) {
        etChangeCounterName.setText(counter.getName());
        etChangeCounterCurrency.setText(counter.getCurrency());
        etChangeCounterUnitsMeasure.setText(counter.getUnitsMeasure());
        etChangeCounterRate.setText(Double.toString(counter.getRate()));
    }

    private Counter getCounter(int counterId) {
        DB db = DB.getInstance(this);
        Cursor cursor = db.getCounterById(counterId);
        Log.d(LOG_TAG, "COUNT = " + cursor.getCount());
        Counter counter = null;

        if (cursor.moveToFirst()) {
            counter = new Counter();
            counter.setName(cursor.getString(cursor.getColumnIndex(CountersContract.CountersEntry.COLUMN_NAME)));
            counter.setId(cursor.getInt(cursor.getColumnIndex(CountersContract.CountersEntry.COLUMN_ID)));
            counter.setUnitsMeasure(cursor.getString(cursor.getColumnIndex(CountersContract.CountersEntry.COLUMN_UNITS_MEASURE)));
            counter.setRate(cursor.getDouble(cursor.getColumnIndex(CountersContract.CountersEntry.COLUMN_RATE)));
            counter.setCurrency(cursor.getString(cursor.getColumnIndex(CountersContract.CountersEntry.COLUMN_CURRENCY)));
        }
        return counter;
    }
    
}
