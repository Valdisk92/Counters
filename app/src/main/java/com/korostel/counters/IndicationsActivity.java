package com.korostel.counters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.korostel.counters.data.CountersContract.*;
import com.korostel.counters.data.DB;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;


public class IndicationsActivity extends Activity {

    private static final String LOG_TAG = IndicationsActivity.class.getSimpleName();

    private Intent intent;
    private ListView lvIndications;
    private int counterId;
    private double counterRate;
    private IndicationsAdapter adapter;
    private long counterStartValue;
    private String counterUnitsMeasure;
    private String counterCurrency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indication);

        intent = getIntent();
        counterId = intent.getIntExtra(IndicationsEntry.COLUMN_COUNTER_ID, 0);
        counterRate = intent.getDoubleExtra(CountersEntry.COLUMN_RATE, 0.0);
        counterStartValue = intent.getLongExtra(CountersEntry.COLUMN_START_VALUE, 0);
        counterUnitsMeasure = intent.getStringExtra(CountersEntry.COLUMN_UNITS_MEASURE);
        counterCurrency = intent.getStringExtra(CountersEntry.COLUMN_CURRENCY);

        Bundle bundle = intent.getExtras();
        lvIndications = (ListView)findViewById(R.id.lvIndications);
        registerForContextMenu(lvIndications);
        setListView(bundle);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.indication_context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.action_delete_indication:
                adapter.deleteIndication(info.id);
                Toast.makeText(this, "TOAST " + info.id, Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    private void setListView(Bundle bundle) {
        adapter = new IndicationsAdapter(this, counterId, bundle);
        lvIndications.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.indication, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add_indication) {
            final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("Новое показание");
            dialog.setMessage("Введите новое показание счетчика:");
            final EditText input = new EditText(this);
            input.setInputType(InputType.TYPE_CLASS_NUMBER);
            dialog.setView(input);
            dialog.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                        addToDb(Long.parseLong(input.getText().toString()));
                        adapter.updateAdapter();
                }
            });
            dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //Canceled
                }
            });
            dialog.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addToDb(long currentIndicationValue) {
        DB db = DB.getInstance(this);
        ContentValues contentValues = new ContentValues();
        Indication currentIndication = setUpNewIndication(currentIndicationValue);
        contentValues.put(IndicationsEntry.COLUMN_MONTH, currentIndication.getMonth());
        contentValues.put(IndicationsEntry.COLUMN_YEAR, currentIndication.getYear());
        contentValues.put(IndicationsEntry.COLUMN_DATE, currentIndication.getDate());
        contentValues.put(IndicationsEntry.COLUMN_CURRENT_INDICATION, currentIndication.getCurrIndicationValue());
        contentValues.put(IndicationsEntry.COLUMN_PREVIOUS_INDICATION, currentIndication.getPrevIndicationValue());
        contentValues.put(IndicationsEntry.COLUMN_PRICE, currentIndication.getPrice());
        contentValues.put(IndicationsEntry.COLUMN_COUNTER_ID, currentIndication.getCounterId());

        long rowId = db.insert(IndicationsEntry.TABLE_NAME, contentValues);
        Log.d(LOG_TAG, "addToDb(): row inserted id = " + rowId);
    }

    private Indication setUpNewIndication(long currentIndicationValue) {
        long previousIndicationValue;
        if (adapter.getCount() == 0) {
            previousIndicationValue = counterStartValue;
        } else {
            previousIndicationValue = ((Indication)adapter.getItem(0)).getCurrIndicationValue();
        }
        Indication newIndication = new Indication();
        Calendar calendar =  Calendar.getInstance();
        newIndication.setYear(calendar.get(Calendar.YEAR));
        newIndication.setMonth(calendar.get(Calendar.MONTH));
        newIndication.setDate(calendar.get(Calendar.DATE));
        newIndication.setCurrIndicationValue(currentIndicationValue);
        newIndication.setPrevIndicationValue(previousIndicationValue);
        double price = (currentIndicationValue - previousIndicationValue) * counterRate;
        double newDouble = new BigDecimal(price).setScale(2, RoundingMode.UP).doubleValue();
        newIndication.setPrice(newDouble);
        newIndication.setCounterId(counterId);

        return newIndication;
    }
}
