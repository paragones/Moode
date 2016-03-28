package com.paularagones.moode.Services;

import android.content.Context;
import android.util.Log;

import com.paularagones.moode.Adapters.StatusRecyclerAdapter;
import com.paularagones.moode.Database.DBAdapter;
import com.paularagones.moode.Models.Status;

import org.greenrobot.eventbus.EventBus;

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
public class StatusService {

    private static final String LOG_TAG = StatusService.class.getSimpleName();
    private static StatusService instance;
    private static EventBus eventBus;
    private static DBAdapter dbAdapter;

    private Subscription dbSubscriber;

    private Observable dbObservable;

    public static StatusService newInstance(Context context) {

        if (instance == null) {
            eventBus = EventBus.getDefault();
            dbAdapter = DBAdapter.newInstance(context);
            return instance = new StatusService();
        } else {
            return instance;
        }

    }

    public void getStatusList () {
//        Log.e(LOG_TAG, "getStatusList");
        dbObservable = Observable.fromCallable(new Callable<List<Status>>() {
            @Override
            public List<Status> call() throws Exception {
                return dbAdapter.getStatusList();
            }
        });

        dbSubscriber = dbObservable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Status>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<Status> statuses) {
                        returnList(statuses);
                    }
                });

    }

    private void returnList(List<Status> statuses) {
//        Log.e(LOG_TAG, "returnList");
        eventBus.postSticky(statuses);
    }

}
