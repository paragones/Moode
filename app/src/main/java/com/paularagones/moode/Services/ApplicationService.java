package com.paularagones.moode.Services;

import android.app.Application;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.paularagones.moode.Application.MooderApplication;
import com.paularagones.moode.Constants.Constants;
import com.paularagones.moode.Database.DBAdapter;
import com.paularagones.moode.Models.DbRequest;
import com.paularagones.moode.Models.DbRequestActivity;
import com.paularagones.moode.Models.DbRequestFeelings;
import com.paularagones.moode.Models.DbRequestLocation;
import com.paularagones.moode.Models.DbRequestPerson;
import com.paularagones.moode.Models.Result;
import com.paularagones.moode.Models.SpinnerResult;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Paul.Aragones on 3/11/2016.
 */
public class ApplicationService {

    private static final String LOG_TAG = ApplicationService.class.getSimpleName();
    private static ApplicationService instance;
    private static DBAdapter dbAdapter;
    private static EventBus eventBus;

    private DbRequest dbRequest;
    private MooderApplication application;


    private Subscription dbSubscriber;

    private Observable dbObservable;

    public static ApplicationService newInstance(MooderApplication application) {
        Log.e(LOG_TAG, "newInstance");

        if (instance == null) {
            ApplicationService applicationService = new ApplicationService();
            dbAdapter = DBAdapter.newInstance(application);
            eventBus = EventBus.getDefault();
            applicationService.setApplication(application);
            return applicationService;
        } else {
            return instance;
        }

    }

    public void getDateResult(final DbRequest dbRequest) {
        Log.e(LOG_TAG, "getDateResult");
        this.dbRequest = dbRequest;

        dbObservable = Observable.fromCallable(new Callable<List<Result>>() {
            @Override
            public List<Result> call() throws Exception {

                return dbAdapter.getDateResult(dbRequest);
            }
        });

        dbSubscriber = dbObservable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Result>>() {
                    @Override
                    public void onCompleted() {
                        Log.e(LOG_TAG, "onCompleted ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(LOG_TAG, "Error : ", e);
                    }

                    @Override
                    public void onNext(List<Result> results) {

                        if (dbRequest instanceof DbRequestFeelings) {
                            Log.e(LOG_TAG, "DbRequestFeelings");
                            application.setFeelingsResultChart(results);
                        } else if (dbRequest instanceof DbRequestActivity) {
                            Log.e(LOG_TAG, "DbRequestActivity");
                            application.setActivityResultChart(results);
                        } else if (dbRequest instanceof DbRequestLocation) {
                            Log.e(LOG_TAG, "DbRequestLocation");
                            application.setLocationResultChart(results);
                        } else if (dbRequest instanceof DbRequestPerson) {
                            Log.e(LOG_TAG, "DbRequestPerson");
                            application.setPersonResultChart(results);
                            startInit(results);
                        }



                    }
                });

    }

    public void getCategoryResult(final DbRequest dbRequest) {

        Log.e(LOG_TAG, "getCategoryResult");
        this.dbRequest = dbRequest;

        dbObservable = Observable.fromCallable(new Callable<List<SpinnerResult>>() {
            @Override
            public List<SpinnerResult> call() throws Exception {

                return dbAdapter.getCategoryResult(dbRequest);
            }
        });

        dbSubscriber = dbObservable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<SpinnerResult>>() {
                    @Override
                    public void onCompleted() {
                        Log.e(LOG_TAG, "onCompleted ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(LOG_TAG, "Error : ", e);
                    }

                    @Override
                    public void onNext(List<SpinnerResult> results) {

                        if (dbRequest instanceof DbRequestFeelings) {
                            Log.e(LOG_TAG, "DbRequestFeelings");
                            application.setFeelingsResultSpinner(results);
                        } else if (dbRequest instanceof DbRequestActivity) {
                            Log.e(LOG_TAG, "DbRequestActivity");
                            application.setActivityResultSpinner(results);
                        } else if (dbRequest instanceof DbRequestLocation) {
                            Log.e(LOG_TAG, "DbRequestLocation");
                            application.setLocationResultSpinner(results);
                        } else if (dbRequest instanceof DbRequestPerson) {
                            Log.e(LOG_TAG, "DbRequestPerson");
                            application.setPersonResultSpinner(results);
                            startStats(results);
                        }



                    }
                });


    }

    private void startInit(List<Result> results) {
        Log.e(LOG_TAG, "returnResults");

        eventBus.postSticky(results);
    }

    private void startStats(List<SpinnerResult> results) {
        Log.e(LOG_TAG, "returnResults");

        eventBus.postSticky(results);
    }

    public MooderApplication getApplication() {
        return application;
    }

    public void setApplication(MooderApplication application) {
        this.application = application;
    }

    public DbRequest getDbRequest() {
        return dbRequest;
    }

    public void setDbRequest(DbRequest dbRequest) {
        this.dbRequest = dbRequest;
    }

    public void closeDB() {
        dbAdapter.close();
    }


}
