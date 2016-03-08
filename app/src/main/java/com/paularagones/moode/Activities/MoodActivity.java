package com.paularagones.moode.Activities;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.paularagones.moode.Adapters.RowResultsWithBarAdapter;
import com.paularagones.moode.Adapters.RowSpinnerStyleAdapter;
import com.paularagones.moode.Constants.Constants;
import com.paularagones.moode.Database.DBAdapter;
import com.paularagones.moode.Models.DbRequest;
import com.paularagones.moode.Models.DbRequestActivity;
import com.paularagones.moode.Models.DbRequestFeelings;
import com.paularagones.moode.Models.DbRequestLocation;
import com.paularagones.moode.Models.DbRequestPerson;
import com.paularagones.moode.Models.Result;
import com.paularagones.moode.Models.SpinnerResult;
import com.paularagones.moode.R;
import com.paularagones.moode.Services.ActivityOptionsService;

import java.util.ArrayList;
import java.util.List;

public class MoodActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private ListView dateListView;
    private ListView locationListView;
    private ListView personListView;
    private ListView feelingsListView;
    private ListView activityListView;
    private HorizontalBarChart locationBarChartView;

    private ListView suggestedAdviceListView;
    private Spinner spinnerCategory;
    private Spinner spinnerResults;
    private LinearLayout feelingsLayout;
    private LinearLayout locationLayout;
    private LinearLayout activityLayout;
    private LinearLayout personLayout;


    private DBAdapter dbAdapter;
    private DbRequest dbRequest;
    private List<SpinnerResult> resultList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood);

        String[] categories = {"feelings", "person", "location", "activity"};

        feelingsLayout = (LinearLayout) findViewById(R.id.ll_feelings);
        locationLayout = (LinearLayout) findViewById(R.id.ll_location);
        activityLayout = (LinearLayout) findViewById(R.id.ll_activity);
        personLayout = (LinearLayout) findViewById(R.id.ll_person);

        spinnerCategory = (Spinner) findViewById(R.id.spinner_category);
        spinnerResults = (Spinner) findViewById(R.id.spinner_result);

        dateListView = (ListView) findViewById(R.id.date_list_view);
        locationListView = (ListView) findViewById(R.id.location_list_view);
        personListView = (ListView) findViewById(R.id.person_list_view);
        feelingsListView = (ListView) findViewById(R.id.feelings_list_view);
        activityListView = (ListView) findViewById(R.id.activity_list_view);
        suggestedAdviceListView = (ListView) findViewById(R.id.suggested_advice_list_view);
        locationBarChartView = (HorizontalBarChart) findViewById(R.id.location_bar_chart);

        ArrayAdapter categoryAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, categories);

        spinnerCategory.setAdapter(categoryAdapter);
        spinnerCategory.setOnItemSelectedListener(this);
        spinnerResults.setOnItemSelectedListener(this);

        ///TODO DB shouldn't be called here create an async task for this
        dbAdapter = DBAdapter.newInstance(this);


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
        activityOptionsService.openActivity(this, id);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        switch (parent.getId()) {
            case R.id.spinner_category :
                showLayouts();
                switch (position) {
                    case 0:
                        dbRequest = new DbRequestFeelings();
                        resultList = dbAdapter.getListResult(dbRequest);
                        feelingsLayout.setVisibility(View.GONE);
                        break;
                    case 1:
                        dbRequest = new DbRequestPerson();
                        resultList = dbAdapter.getListResult(dbRequest);
                        personLayout.setVisibility(View.GONE);
                        break;
                    case 2:
                        dbRequest = new DbRequestLocation();
                        resultList = dbAdapter.getListResult(dbRequest);
                        locationLayout.setVisibility(View.GONE);
                        break;
                    case 3:
                        dbRequest = new DbRequestActivity();
                        resultList = dbAdapter.getListResult(dbRequest);
                        activityLayout.setVisibility(View.GONE);
                        break;

                }

                RowSpinnerStyleAdapter spinnerResultAdapter = new RowSpinnerStyleAdapter(this, resultList);
                spinnerResults.setAdapter(spinnerResultAdapter);

                break;
            case R.id.spinner_result :

                List<String> dateList = dbAdapter.getDates(dbRequest, resultList.get(position).getID());
                ArrayAdapter datesAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, dateList);
                dateListView.setAdapter(datesAdapter);

                if (!(dbRequest instanceof DbRequestFeelings)) {
                    List<Result> feelingsResults = dbAdapter.getResultList(dbRequest.getTableID(),resultList.get(position).getID(), new DbRequestFeelings());
                    RowResultsWithBarAdapter feelingsResultsAdapter = new RowResultsWithBarAdapter(this, feelingsResults);
                    feelingsListView.setAdapter(feelingsResultsAdapter);
                }

                if (!(dbRequest instanceof DbRequestPerson)) {
                    List<Result> personResults = dbAdapter.getResultList(dbRequest.getTableID(),resultList.get(position).getID(), new DbRequestPerson());
                    RowResultsWithBarAdapter personResultsAdapter = new RowResultsWithBarAdapter(this, personResults);
                    personListView.setAdapter(personResultsAdapter);
                }

                if (!(dbRequest instanceof DbRequestLocation)) {
                    List<Result> locationResults = dbAdapter.getResultList(dbRequest.getTableID(),resultList.get(position).getID(), new DbRequestLocation());

                    ArrayList<IBarDataSet> barDataSets = new ArrayList<>();
                    ArrayList<String> xVals = new ArrayList<String>();

                    for (int i = 0; i < locationResults.size(); i++) {
                        BarEntry entry1 = new BarEntry(locationResults.get(i).getNumberOfTimes(), i);

                        ArrayList<BarEntry> entries = new ArrayList<>();
                        entries.add(entry1);

                        ArrayList<Integer> colors = new ArrayList<Integer>();

                        for (int c : ColorTemplate.VORDIPLOM_COLORS)
                            colors.add(c);

                        BarDataSet barDataSet = new BarDataSet(entries, "barDataSet = " +  locationResults.get(i).getDescription());
                        barDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
                        barDataSet.setColor(colors.get(i));
                        barDataSets.add(barDataSet);

//                        xVals.add("xVals = " +  locationResults.get(i).getDescription());
                        xVals.add("");
                    }

                    BarData barData = new BarData(xVals, barDataSets);
                    locationBarChartView.setData(barData);
                    locationBarChartView.setDrawGridBackground(false);
                    locationBarChartView.setDrawBarShadow(false);
//                    locationBarChartView.setDrawValueAboveBar(false);
                    locationBarChartView.setDescription("");

                    XAxis xl = locationBarChartView.getXAxis();
                    xl.setPosition(XAxis.XAxisPosition.BOTTOM);
                    xl.setDrawAxisLine(true);
                    xl.setDrawGridLines(false);

                    YAxis yl = locationBarChartView.getAxisLeft();
                    yl.setDrawAxisLine(true);
                    yl.setDrawGridLines(false);
                    yl.setGridLineWidth(0.3f);
                    yl.setAxisMinValue(0f); // this replaces setStartAtZero(true)

                    YAxis yr = locationBarChartView.getAxisRight();
                    yr.setDrawAxisLine(true);
                    yr.setDrawGridLines(false);
                    yr.setAxisMinValue(0f); // this replaces setStartAtZero(true)

                    locationBarChartView.animateY(2500);

//                    RowResultsWithBarAdapter locationResultsAdapter = new RowResultsWithBarAdapter(this, locationResults);
//                    locationListView.setAdapter(locationResultsAdapter);
                }

                if (!(dbRequest instanceof DbRequestActivity)) {
                    List<Result> activityResults = dbAdapter.getResultList(dbRequest.getTableID(),resultList.get(position).getID(), new DbRequestActivity());
                    RowResultsWithBarAdapter activityResultsAdapter = new RowResultsWithBarAdapter(this, activityResults);
                    activityListView.setAdapter(activityResultsAdapter);
                }

                break;
        }
    }

    private void showLayouts() {
        feelingsLayout.setVisibility(View.VISIBLE);
        personLayout.setVisibility(View.VISIBLE);
        locationLayout.setVisibility(View.VISIBLE);
        activityLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
