package com.paularagones.moode.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.paularagones.moode.Adapters.RowResultsWithBarAdapter;
import com.paularagones.moode.Database.DBAdapter;
import com.paularagones.moode.Models.Result;
import com.paularagones.moode.R;
import com.paularagones.moode.Services.ActivityOptionsService;

import java.util.ArrayList;
import java.util.List;

public class MoodActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private ListView dateListView;
    private ListView locationListView;
    private ListView personListView;
    private ListView suggestedAdviceListView;
    private Spinner spinnerCategory;
    private Spinner spinnerResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood);

        String[] categories = {"feelings", "person", "location", "activity"};

        spinnerCategory = (Spinner) findViewById(R.id.spinner_category);
        spinnerResults = (Spinner) findViewById(R.id.spinner_result);

        dateListView = (ListView) findViewById(R.id.dateListView);
        locationListView = (ListView) findViewById(R.id.locationListView);
        personListView = (ListView) findViewById(R.id.personListView);
        suggestedAdviceListView = (ListView) findViewById(R.id.suggestedAdviceListView);

        ArrayAdapter categoryAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, categories);

        spinnerCategory.setAdapter(categoryAdapter);
        spinnerCategory.setOnItemSelectedListener(this);


        ///TODO DB shouldn't be called here create an async task for this
        DBAdapter dbAdapter = DBAdapter.newInstance(this);
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.spinner_category :
                switch (position) {
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;

                }
                break;
            case R.id.spinner_result :
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
