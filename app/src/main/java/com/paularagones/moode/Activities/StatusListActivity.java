package com.paularagones.moode.Activities;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.paularagones.moode.Adapters.RowStatusListAdapter;
import com.paularagones.moode.Database.DBAdapter;
import com.paularagones.moode.Models.Status;
import com.paularagones.moode.R;
import com.paularagones.moode.Services.ActivityOptionsService;

import java.util.List;
import java.util.concurrent.Callable;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class StatusListActivity extends AppCompatActivity {

    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        setContentView(R.layout.activity_status_list);
        final ListView statuslistViewer = (ListView) findViewById(R.id.status_list_view);


        ///TODO DB shouldn't be called here create an async task for this
        final DBAdapter dbAdapter = DBAdapter.newInstance(this);
//        final List<Status> statusList = dbAdapter.getStatusList();

        Observable<List<Status>> dbStatusListObservable = Observable.fromCallable(new Callable<List<Status>>() {
            @Override
            public List<Status> call() throws Exception {
                return dbAdapter.getStatusList();
            }
        });

        Subscription dbStatusListSubscriber = dbStatusListObservable
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
                        RowStatusListAdapter statusListAdapter = new RowStatusListAdapter(activity, statuses);
                        statuslistViewer.setAdapter(statusListAdapter);
                    }
                });


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
