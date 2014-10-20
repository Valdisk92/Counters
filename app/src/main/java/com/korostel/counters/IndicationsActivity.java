package com.korostel.counters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;

import com.korostel.counters.data.CountersContract;
import com.korostel.counters.data.DB;

import java.util.Calendar;


public class IndicationsActivity extends Activity {

    private static final String LOG_TAG = IndicationsActivity.class.getSimpleName();

    private Intent intent;
    private ListView lvIndications;
    private int counterId;
    private double counterRate;
    private IndicationsAdapter adapter;
    private Indication mNewIndication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indication);

        intent = getIntent();
        counterId = intent.getIntExtra(CountersContract.IndicationsEntry.COLUMN_COUNTER_ID, 0);
        counterRate = intent.getDoubleExtra(CountersContract.CountersEntry.COLUMN_RATE, 0.0);
        lvIndications = (ListView)findViewById(R.id.lvIndications);
        setListView();
    }

    private void setListView() {
        adapter = new IndicationsAdapter(this, counterId);
        lvIndications.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.indication, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_add_indication) {
            final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("Add indication");
            dialog.setMessage("Enter new indication: ");
            final EditText input = new EditText(this);
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
        Indication currentIndication = setNewIndication(currentIndicationValue);
        contentValues.put(CountersContract.IndicationsEntry.COLUMN_MONTH, currentIndication.getMonth());
        contentValues.put(CountersContract.IndicationsEntry.COLUMN_YEAR, currentIndication.getYear());
        contentValues.put(CountersContract.IndicationsEntry.COLUMN_CURRENT_INDICATION, currentIndication.getCurrIndicationValue());
        contentValues.put(CountersContract.IndicationsEntry.COLUMN_PREVIOUS_INDICATION, currentIndication.getPrevIndicationValue());
        contentValues.put(CountersContract.IndicationsEntry.COLUMN_PRICE, currentIndication.getPrice());
        contentValues.put(CountersContract.IndicationsEntry.COLUMN_COUNTER_ID, currentIndication.getCounterId());

        long rowid = db.insert(CountersContract.IndicationsEntry.TABLE_NAME, contentValues);
        Log.d(LOG_TAG, "addToDb(): row inserted id = " + rowid);
    }

    private Indication setNewIndication(long currentIndicationValue) {
        Indication previousIndication = ((Indication)adapter.getItem(0));
        Indication newIndication = new Indication();
        Calendar calendar =  Calendar.getInstance();
        newIndication.setYear(calendar.get(Calendar.YEAR));
        newIndication.setMonth(calendar.get(Calendar.MONTH));
        newIndication.setCurrIndicationValue(currentIndicationValue);
        newIndication.setPrevIndicationValue(previousIndication.getCurrIndicationValue());
        newIndication.setPrice((currentIndicationValue - previousIndication.getCurrIndicationValue()) * counterRate);
        newIndication.setCounterId(counterId);

        return newIndication;
    }
}
