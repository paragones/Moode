package com.paularagones.moode.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.paularagones.moode.Adapters.RowResultsWithBarAdapter;
import com.paularagones.moode.Database.DBAdapter;
import com.paularagones.moode.Models.DbRequestActivity;
import com.paularagones.moode.Models.DbRequestFeelings;
import com.paularagones.moode.Models.DbRequestLocation;
import com.paularagones.moode.Models.DbRequestPerson;
import com.paularagones.moode.Models.Result;
import com.paularagones.moode.R;
import com.paularagones.moode.Services.ActivityOptionsService;

import org.joda.time.YearMonth;

import java.util.List;

public class DateActivity extends AppCompatActivity {

    private ListView feelingsListView;
    private ListView locationListView;
    private ListView personListView;
    private ListView activityListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);

        TextView tvDateTitle = (TextView) findViewById(R.id.title_date_chart);
        feelingsListView = (ListView) findViewById(R.id.feelingsListView);
        locationListView = (ListView) findViewById(R.id.locationListView);
        personListView = (ListView) findViewById(R.id.personListView);
        activityListView = (ListView) findViewById(R.id.activityListView);

        YearMonth month = new YearMonth();

        tvDateTitle.setText(tvDateTitle.getText() + month.monthOfYear().getAsText() + month.year().getAsText() );

        ///TODO DB shouldn't be called here create an async task for this
        DBAdapter dbAdapter = DBAdapter.newInstance(this);
        List<Result> locationResults = dbAdapter.getDateResult(new DbRequestLocation());
        List<Result> personResults = dbAdapter.getDateResult(new DbRequestPerson());
        List<Result> feelingsResults = dbAdapter.getDateResult(new DbRequestFeelings());
        List<Result> activityResults = dbAdapter.getDateResult(new DbRequestActivity());

        RowResultsWithBarAdapter locationResultsAdapter = new RowResultsWithBarAdapter(this, locationResults);
        RowResultsWithBarAdapter feelingsResultsAdapter = new RowResultsWithBarAdapter(this, feelingsResults);
        RowResultsWithBarAdapter personResultsAdapter = new RowResultsWithBarAdapter(this, personResults);
        RowResultsWithBarAdapter activityResultsAdapter = new RowResultsWithBarAdapter(this, activityResults);

        locationListView.setAdapter(locationResultsAdapter);
        feelingsListView.setAdapter(feelingsResultsAdapter);
        personListView.setAdapter(personResultsAdapter);
        activityListView.setAdapter(activityResultsAdapter);

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
