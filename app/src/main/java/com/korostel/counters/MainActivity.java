package com.korostel.counters;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.korostel.counters.data.CountersContract;
import com.korostel.counters.data.DB;


//TODO По возможности добавить какую-то фичу и если хватит у тебя сил, то переделать под фрагменты =)
public class MainActivity extends Activity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private ListView lvCounters;
    private CountersAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvCounters = (ListView)findViewById(R.id.lvCounters);
        registerForContextMenu(lvCounters);
        setListView();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.counter_context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.action_delete_counter:
                adapter.deleteCounter(info.id);
                break;
            case R.id.action_change_counter:
                Intent intent = new Intent(this, ChangeCounterActivity.class);
                intent.putExtra("counterId", adapter.getCounter((int) info.id).getId());
                startActivityForResult(intent, 2);
                break;
        }
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
                String unitsMeasure = ((Counter)adapterView.getItemAtPosition(i)).getUnitsMeasure();
                String currency = ((Counter)adapterView.getItemAtPosition(i)).getCurrency();

                Log.d(LOG_TAG, "Counter ID = " + counterId);
                Intent intent = new Intent(MainActivity.this, IndicationsActivity.class);
                intent.putExtra(CountersContract.IndicationsEntry.COLUMN_COUNTER_ID, counterId);
                intent.putExtra(CountersContract.CountersEntry.COLUMN_RATE, rate);
                intent.putExtra(CountersContract.CountersEntry.COLUMN_START_VALUE, startValue);
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
