package com.paularagones.moode.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.paularagones.moode.Adapters.RowResultsWithBarAdapter;
import com.paularagones.moode.Database.DBAdapter;
import com.paularagones.moode.Models.DbRequestActivity;
import com.paularagones.moode.Models.DbRequestFeelings;
import com.paularagones.moode.Models.DbRequestLocation;
import com.paularagones.moode.Models.DbRequestPerson;
import com.paularagones.moode.Models.Result;
import com.paularagones.moode.R;
import com.paularagones.moode.Services.ChartService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.joda.time.YearMonth;

import java.util.List;

/**
 * Created by Mae Anjanette Docena on 3/4/2016.
 */
public class ChartFragment2 extends Fragment {

    private static final String LOG_TAG = ChartFragment2.class.getSimpleName();
    private ListView feelingsListView;
    private ListView locationListView;
    private ListView personListView;
    private ListView activityListView;

    private PieChart feelingsPieChart;
    private EventBus eventBus;
    private ChartService chartService;

    public ChartFragment2() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.e(LOG_TAG, "onCreateView");



        View view = inflater.inflate(R.layout.fragment_chart, container, false);

        TextView tvDateTitle = (TextView) view.findViewById(R.id.title_date_chart);
//        feelingsListView = (ListView) view.findViewById(R.id.feelings_pie_chart);
        locationListView = (ListView) view.findViewById(R.id.location_list_view);
        personListView = (ListView) view.findViewById(R.id.person_list_view);
        activityListView = (ListView) view.findViewById(R.id.activity_list_view);

        feelingsPieChart = (PieChart) view.findViewById(R.id.pie_chart);

        YearMonth month = new YearMonth();

        tvDateTitle.setText(tvDateTitle.getText() + month.monthOfYear().getAsText() + month.year().getAsText() );

        ///TODO DB shouldn't be called here create an async task for this
        DBAdapter dbAdapter = DBAdapter.newInstance(getContext());
        List<Result> locationResults = dbAdapter.getDateResult(new DbRequestLocation());
        List<Result> personResults = dbAdapter.getDateResult(new DbRequestPerson());


//        List<Result> feelingsResults = dbAdapter.getDateResult(new DbRequestFeelings());


        List<Result> activityResults = dbAdapter.getDateResult(new DbRequestActivity());

        RowResultsWithBarAdapter locationResultsAdapter = new RowResultsWithBarAdapter(getContext(), locationResults);
//        RowResultsWithBarAdapter feelingsResultsAdapter = new RowResultsWithBarAdapter(getContext(), feelingsResults);
        RowResultsWithBarAdapter personResultsAdapter = new RowResultsWithBarAdapter(getContext(), personResults);
        RowResultsWithBarAdapter activityResultsAdapter = new RowResultsWithBarAdapter(getContext(), activityResults);

//        chartService.setupPieChart(feelingsResults, feelingsPieChart);



        locationListView.setAdapter(locationResultsAdapter);
//        feelingsListView.setAdapter(feelingsResultsAdapter);
        personListView.setAdapter(personResultsAdapter);
        activityListView.setAdapter(activityResultsAdapter);
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            feelingsPieChart.animateY(1400);
        }
    }

    @Override
    public void onStart() {
        Log.e(LOG_TAG, "onStart");
        super.onStart();
        eventBus = EventBus.getDefault();
        eventBus.register(this);
        chartService = ChartService.newInstance(getContext());
        chartService.getDateResult(new DbRequestFeelings());
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onReturnResults(List<Result> results) {
        Log.e(LOG_TAG, "onReturnResults");
        if (results.get(1) instanceof Result)
        chartService.setupPieChart(results, feelingsPieChart);
    }

    @Override
    public void onStop() {
        eventBus.unregister(this);
        super.onStop();
    }
}