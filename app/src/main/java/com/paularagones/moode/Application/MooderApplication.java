package com.paularagones.moode.Application;

import android.app.Application;
import android.util.Log;

import com.paularagones.moode.Models.DbRequest;
import com.paularagones.moode.Models.DbRequestActivity;
import com.paularagones.moode.Models.DbRequestFeelings;
import com.paularagones.moode.Models.DbRequestLocation;
import com.paularagones.moode.Models.DbRequestPerson;
import com.paularagones.moode.Models.Result;
import com.paularagones.moode.Models.SpinnerResult;
import com.paularagones.moode.Services.ApplicationService;
import com.paularagones.moode.Services.ChartService;

import net.danlew.android.joda.JodaTimeAndroid;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * Created by Paul.Aragones on 2/29/2016.
 */
public class MooderApplication extends Application {

    private static final String LOG_TAG = MooderApplication.class.getSimpleName();
    private List<Result> locationResultChart;
    private List<Result> feelingsResultChart;
    private List<Result> activityResultChart;
    private List<Result> personResultChart;

    private List<SpinnerResult> feelingsResultSpinner;
    private List<SpinnerResult> locationResultSpinner;
    private List<SpinnerResult> activityResultSpinner;
    private List<SpinnerResult> personResultSpinner;

    private ApplicationService applicationService;

    private EventBus eventBus;

    @Override
    public void onCreate() {
        super.onCreate();

        Log.e(LOG_TAG, "onCreate");

        JodaTimeAndroid.init(this);
    }

    public void loadDb() {

        eventBus = EventBus.getDefault();
        applicationService = ApplicationService.newInstance(this);

        applicationService.getDateResult(new DbRequestFeelings());
        applicationService.getDateResult(new DbRequestActivity());
        applicationService.getDateResult(new DbRequestLocation());
        applicationService.getDateResult(new DbRequestPerson());

        applicationService.getCategoryResult(new DbRequestFeelings());
        applicationService.getCategoryResult(new DbRequestActivity());
        applicationService.getCategoryResult(new DbRequestLocation());
        applicationService.getCategoryResult(new DbRequestPerson());

    }

    public List<Result> getResultChart(DbRequest dbRequest) {
        if (dbRequest instanceof DbRequestFeelings) {
            return getFeelingsResultChart();
        } else if (dbRequest instanceof DbRequestActivity) {
            return getActivityResultChart();
        } else if (dbRequest instanceof DbRequestLocation) {
            return getLocationResultChart();
        } else {
            return getPersonResultChart();
        }
    }

    public List<SpinnerResult> getCategoryStat (DbRequest dbRequest) {
        if (dbRequest instanceof DbRequestFeelings) {
            return getFeelingsResultSpinner();
        } else if (dbRequest instanceof DbRequestActivity) {
            return getActivityResultSpinner();
        } else if (dbRequest instanceof DbRequestLocation) {
            return getLocationResultSpinner();
        } else {
            return getPersonResultSpinner();
        }
    }


    public List<Result> getLocationResultChart() {
        return locationResultChart;
    }

    public void setLocationResultChart(List<Result> locationResultChart) {
        this.locationResultChart = locationResultChart;
    }

    public List<Result> getFeelingsResultChart() {
        return feelingsResultChart;
    }

    public void setFeelingsResultChart(List<Result> feelingsResultChart) {
        this.feelingsResultChart = feelingsResultChart;
    }

    public List<Result> getActivityResultChart() {
        return activityResultChart;
    }

    public void setActivityResultChart(List<Result> activityResultChart) {
        this.activityResultChart = activityResultChart;
    }

    public List<Result> getPersonResultChart() {
        return personResultChart;
    }

    public void setPersonResultChart(List<Result> personResultChart) {
        this.personResultChart = personResultChart;
    }

    public List<SpinnerResult> getFeelingsResultSpinner() {
        return feelingsResultSpinner;
    }

    public void setFeelingsResultSpinner(List<SpinnerResult> feelingsResultSpinner) {
        this.feelingsResultSpinner = feelingsResultSpinner;
    }

    public List<SpinnerResult> getLocationResultSpinner() {
        return locationResultSpinner;
    }

    public void setLocationResultSpinner(List<SpinnerResult> locationResultSpinner) {
        this.locationResultSpinner = locationResultSpinner;
    }

    public List<SpinnerResult> getActivityResultSpinner() {
        return activityResultSpinner;
    }

    public void setActivityResultSpinner(List<SpinnerResult> activityResultSpinner) {
        this.activityResultSpinner = activityResultSpinner;
    }

    public List<SpinnerResult> getPersonResultSpinner() {
        return personResultSpinner;
    }

    public void setPersonResultSpinner(List<SpinnerResult> personResultSpinner) {
        this.personResultSpinner = personResultSpinner;
    }

    public void closeDB() {
        applicationService.closeDB();
    }
}
