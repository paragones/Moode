package com.paularagones.moode.Fragments;

import android.app.Application;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.paularagones.moode.Application.MooderApplication;
import com.paularagones.moode.Models.DbRequest;
import com.paularagones.moode.Models.DbRequestFeelings;
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
public class ChartFragment extends Fragment {

    private static final String LOG_TAG = ChartFragment.class.getSimpleName();

    private MooderApplication application;
    private PieChart pieChart;
    private EventBus eventBus;
    private ChartService chartService;
    private DbRequest dbRequest;

    public static ChartFragment newInstance(DbRequest dbRequest) {
        ChartFragment chartFragment = new ChartFragment();
        chartFragment.setDbRequest(dbRequest);

        return chartFragment;
    }

    public DbRequest getDbRequest() {
        return dbRequest;
    }

    public void setDbRequest(DbRequest dbRequest) {
        this.dbRequest = dbRequest;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

//        Log.e(LOG_TAG, "onCreateView");
        Log.e(LOG_TAG, "dbRequest : " + dbRequest.getTableName());

        application = (MooderApplication) getActivity().getApplication();

        View view = inflater.inflate(R.layout.fragment_chart, container, false);
        TextView tvDateTitle = (TextView) view.findViewById(R.id.title_date_chart);
        pieChart = (PieChart) view.findViewById(R.id.pie_chart);

        YearMonth month = new YearMonth();
        tvDateTitle.setText(month.monthOfYear().getAsText() + " " + month.year().getAsText());

        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (pieChart != null)
            pieChart.animateY(1400);
        }
    }

    @Override
    public void onStart() {
//        Log.e(LOG_TAG, "onStart");
        super.onStart();
        eventBus = EventBus.getDefault();
//        eventBus.register(this);
        chartService = ChartService.newInstance(getContext());

        List<Result> results = application.getResultChart(dbRequest);
        Log.e(LOG_TAG, "results " + results);
        chartService.setupPieChart(results, pieChart);

//        chartService.getDateResult(dbRequest);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onReturnResults(List<Result> results) {
        Log.e(LOG_TAG, "onReturnResults");
        Log.e(LOG_TAG, "results " + results);
        Log.e(LOG_TAG, "pieChart " +pieChart);

        if (results != null && pieChart != null) {
            if (results.get(1) instanceof Result)
                chartService.setupPieChart(results, pieChart);
        }
    }

    @Override
    public void onStop() {
        eventBus.unregister(this);
        super.onStop();
    }
}