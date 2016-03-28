package com.paularagones.moode.Fragments;

import android.support.v4.app.Fragment;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.paularagones.moode.Models.DbRequest;
import com.paularagones.moode.Models.SpinnerResult;
import com.paularagones.moode.Services.ChartService;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by Paul.Aragones on 3/15/2016.
 */
public class StatFragment {

    private HorizontalBarChart barChart;
    private EventBus eventBus;
//    private StatService statService;
    private DbRequest dbRequest;


    public static StatFragment newInstance(DbRequest dbRequest, List<SpinnerResult> results) {

        StatFragment statFragment = new StatFragment();
        statFragment.setDbRequest(dbRequest);

        return statFragment;
    }



    public DbRequest getDbRequest() {
        return dbRequest;
    }

    public void setDbRequest(DbRequest dbRequest) {
        this.dbRequest = dbRequest;
    }
}
