package com.paularagones.moode.Services;

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
import com.paularagones.moode.Constants.Constants;
import com.paularagones.moode.Database.DBAdapter;
import com.paularagones.moode.Models.DbRequest;
import com.paularagones.moode.Models.DbRequestFeelings;
import com.paularagones.moode.Models.Result;
import com.paularagones.moode.Models.Status;

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
public class ChartService {

    private static final String LOG_TAG = ChartService.class.getSimpleName();
    private static ChartService instance;
    private static EventBus eventBus;
    private static DBAdapter dbAdapter;
    private static DbRequest dbRequest;

    private Subscription dbSubscriber;

    private Observable dbObservable;

    public static ChartService newInstance(Context context) {
        Log.e(LOG_TAG, "newInstance");
        if (instance == null) {
            eventBus = EventBus.getDefault();
            dbAdapter = DBAdapter.newInstance(context);


            return instance = new ChartService();
        } else {
            return instance;
        }

    }

    public void getDateResult(final DbRequest dbRequest) {
        Log.e(LOG_TAG, "getDateResult");
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

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<Result> results) {
//                        returnResults(results);
                    }
                });

    }

    private void returnResults(List<Result> results) {
        Log.e(LOG_TAG, "returnResults");

        eventBus.postSticky(results);
    }



    public void setupPieChart(List<Result> results, PieChart pieChart) {
        pieChart.setUsePercentValues(true);
        pieChart.setDescription("");
        pieChart.setExtraOffsets(5, 10, 5, 5);

        pieChart.setDragDecelerationFrictionCoef(0.95f);

        pieChart.setCenterText(generateCenterSpannableText());

        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);

        pieChart.setTransparentCircleColor(Color.WHITE);
        pieChart.setTransparentCircleAlpha(110);

        pieChart.setHoleRadius(58f);
        pieChart.setTransparentCircleRadius(61f);

        pieChart.setDrawCenterText(true);

        pieChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        pieChart.setRotationEnabled(true);
        pieChart.setHighlightPerTapEnabled(true);

        pieChart.setData(getData(results));

        pieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);

        Legend l = pieChart.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

    }

    private PieData getData(List<Result> results) {
        ArrayList<Entry> yVals1 = new ArrayList<Entry>();
        ArrayList<String> xVals = new ArrayList<String>();
        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int i = 0; i < results.size(); i++) {
            yVals1.add(new Entry(results.get(i).getNumberOfTimes(),i));
            xVals.add(results.get(i).getDescription());
        }

        PieDataSet dataSet = new PieDataSet(yVals1, "Results");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(Constants.Colors.GET_COLORS());

        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);

        return data;
    }

    private CharSequence generateCenterSpannableText() {

        SpannableString s = new SpannableString("MPAndroidChart\ndeveloped by Philipp Jahoda");
        s.setSpan(new RelativeSizeSpan(1.7f), 0, 14, 0);
        s.setSpan(new StyleSpan(Typeface.NORMAL), 14, s.length() - 15, 0);
        s.setSpan(new ForegroundColorSpan(Color.GRAY), 14, s.length() - 15, 0);
        s.setSpan(new RelativeSizeSpan(.8f), 14, s.length() - 15, 0);
        s.setSpan(new StyleSpan(Typeface.ITALIC), s.length() - 14, s.length(), 0);
        s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length() - 14, s.length(), 0);

        return s;
    }
}
