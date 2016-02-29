package com.paularagones.moode.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.paularagones.moode.Adapters.RowResultsWithBarAdapter;
import com.paularagones.moode.Database.DBAdapter;
import com.paularagones.moode.Models.Result;
import com.paularagones.moode.R;
import com.paularagones.moode.Services.ActivityOptionsService;

import java.util.List;

public class MoodActivity extends AppCompatActivity {

    private ListView dateListView;
    private ListView locationListView;
    private ListView personListView;
    private ListView suggestedAdviceListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood);

        dateListView = (ListView) findViewById(R.id.dateListView);
        locationListView = (ListView) findViewById(R.id.locationListView);
        personListView = (ListView) findViewById(R.id.personListView);
        suggestedAdviceListView = (ListView) findViewById(R.id.suggestedAdviceListView);


        ///TODO DB shouldn't be called here create an async task for this
        DBAdapter dbAdapter = new DBAdapter(this);
        List<Result> locationResults = dbAdapter.getFeelingsResultByLocation();
        List<Result> personResults = dbAdapter.getFeelingsResultByPerson();

        RowResultsWithBarAdapter locationResultsAdapter = new RowResultsWithBarAdapter(this, locationResults);
        RowResultsWithBarAdapter personResultsAdapter = new RowResultsWithBarAdapter(this, personResults);

        locationListView.setAdapter(locationResultsAdapter);
        personListView.setAdapter(personResultsAdapter);

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
