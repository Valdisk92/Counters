package com.korostel.counters;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.korostel.counters.data.CountersContract;


public class IndicationsActivity extends Activity {

    private Intent intent;
    private ListView lvIndications;
    private int counterId;
    private IndicationsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indication);

        intent = getIntent();
        counterId = intent.getIntExtra(CountersContract.IndicationsEntry.COLUMN_COUNTER_ID, 0);
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
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
