package com.paularagones.moode.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.paularagones.moode.Adapters.RowResultsWithBarAdapter;
import com.paularagones.moode.Database.DBAdapter;
import com.paularagones.moode.Models.Result;
import com.paularagones.moode.R;
import com.paularagones.moode.Services.ActivityOptionsService;

import org.joda.time.DateTime;
import org.joda.time.YearMonth;

import java.util.List;

public class DateActivity2 extends AppCompatActivity {

    private ListView feelingsListView;
    private ListView locationListView;
    private ListView personListView;
    private ListView activityListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date2);

        TextView tvDateTitle = (TextView) findViewById(R.id.title_date_chart);
        feelingsListView = (ListView) findViewById(R.id.feelingsListView);
        locationListView = (ListView) findViewById(R.id.locationListView);
        personListView = (ListView) findViewById(R.id.personListView);
        activityListView = (ListView) findViewById(R.id.activityListView);

        YearMonth month = new YearMonth();

        tvDateTitle.setText(tvDateTitle.getText() + month.monthOfYear().getAsText() + month.year().getAsText() );


        ///TODO DB shouldn't be called here create an async task for this
        DBAdapter dbAdapter = new DBAdapter(this);
        List<Result> locationResults = dbAdapter.getLocationResultByDate();
//        List<Result> personResults = dbAdapter.getPersonResultByDate();
        List<Result> feelingsResults = dbAdapter.getFeelingsResultByDate();
//        List<Result> activityResults = dbAdapter.getActivityResultByDate();

        RowResultsWithBarAdapter locationResultsAdapter = new RowResultsWithBarAdapter(this, locationResults);
        RowResultsWithBarAdapter feelingsResultsAdapter = new RowResultsWithBarAdapter(this, feelingsResults);

        locationListView.setAdapter(locationResultsAdapter);
        feelingsListView.setAdapter(feelingsResultsAdapter);

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
