package com.korostel.counters;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.korostel.counters.data.CountersContract;
import com.korostel.counters.data.DB;


public class MainActivity extends Activity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    ListView lvCounters;
    CountersAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvCounters = (ListView)findViewById(R.id.lvCounters);
        setListView();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_counter:
                Intent intent = new Intent(this, AddCounterActivity.class);
                startActivityForResult(intent, 1);
                return true;

            case R.id.action_clear_db:
                DB db = DB.getInstance(this);
                db.clearDB();
                adapter.updateAdapter();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setListView() {
        adapter = new CountersAdapter(this);
        lvCounters.setAdapter(adapter);
        lvCounters.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int counterId = ((Counter)adapterView.getItemAtPosition(i)).getId();
                double rate = ((Counter)adapterView.getItemAtPosition(i)).getRate();
                long startValue = ((Counter)adapterView.getItemAtPosition(i)).getStartValue();
                int intBits = ((Counter)adapterView.getItemAtPosition(i)).getIntBits();
                int fracBits = ((Counter)adapterView.getItemAtPosition(i)).getFracBits();
                String unitsMeasure = ((Counter)adapterView.getItemAtPosition(i)).getUnitsMeasure();
                String currency = ((Counter)adapterView.getItemAtPosition(i)).getCurrency();

                Log.d(LOG_TAG, "Counter ID = " + counterId);
                Intent intent = new Intent(MainActivity.this, IndicationsActivity.class);
                intent.putExtra(CountersContract.IndicationsEntry.COLUMN_COUNTER_ID, counterId);
                intent.putExtra(CountersContract.CountersEntry.COLUMN_RATE, rate);
                intent.putExtra(CountersContract.CountersEntry.COLUMN_START_VALUE, startValue);
                intent.putExtra(CountersContract.CountersEntry.COLUMN_COUNT_INT_BITS, intBits);
                intent.putExtra(CountersContract.CountersEntry.COLUMN_COUNT_FRAC_BITS, fracBits);
                intent.putExtra(CountersContract.CountersEntry.COLUMN_UNITS_MEASURE, unitsMeasure);
                intent.putExtra(CountersContract.CountersEntry.COLUMN_CURRENCY, currency);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            adapter.updateAdapter();
        }
    }
}
