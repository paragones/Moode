package com.paularagones.moode.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.paularagones.moode.Adapters.RowStatusListAdapter;
import com.paularagones.moode.Database.DBAdapter;
import com.paularagones.moode.Models.Status;
import com.paularagones.moode.R;
import com.paularagones.moode.Services.ActivityOptionsService;

import java.util.List;

public class StatusListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_list);
        ListView statuslistViewer = (ListView) findViewById(R.id.status_list_view);


        ///TODO DB shouldn't be called here create an async task for this
        DBAdapter dbAdapter = DBAdapter.newInstance(this);
        List<Status> statusList = dbAdapter.getStatusList();

        RowStatusListAdapter statusListAdapter = new RowStatusListAdapter(this, statusList);
        statuslistViewer.setAdapter(statusListAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_status_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        ActivityOptionsService activityOptionsService = new ActivityOptionsService();
        activityOptionsService.openActivity(this ,id);
        return super.onOptionsItemSelected(item);
    }


}
