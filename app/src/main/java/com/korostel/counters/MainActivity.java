package com.korostel.counters;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.korostel.counters.data.CountersContract.*;
import com.korostel.counters.data.CountersDBHelper;
import com.korostel.counters.data.DB;


public class MainActivity extends Activity {

    ListView lvCounters;
    SimpleCursorAdapter adapter;


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
        }
        return super.onOptionsItemSelected(item);
    }

    private void setListView() {
        DB db = new DB(this);
        db.open();
        Cursor cursor = db.getAllData(CountersEntry.TABLE_NAME);
        startManagingCursor(cursor);
        String[] from = new String[] {CountersEntry.COLUMN_NAME};
        int[] to = new int[] {R.id.tvListCountersName};
        adapter = new SimpleCursorAdapter(this, R.layout.item, cursor, from, to);
        lvCounters.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            adapter.notifyDataSetChanged();
        }
    }
}
