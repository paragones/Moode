package com.paularagones.moode.Activities;

/**
 * Created by Mae Anjanette Docena on 3/4/2016.
 */
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import com.paularagones.moode.Adapters.ViewPagerAdapter;
import com.paularagones.moode.Application.MooderApplication;
import com.paularagones.moode.Fragments.ChartMainFragment;
import com.paularagones.moode.Models.Result;
import com.paularagones.moode.R;
import com.paularagones.moode.Fragments.StatusFragment;
import com.paularagones.moode.Fragments.StatsCategoryFragment;
import com.paularagones.moode.Services.ActivityOptionsService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    public static String PACKAGE_NAME;

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private EventBus eventBus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        MooderApplication application = (MooderApplication) getApplication();

        PACKAGE_NAME = getPackageName();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.title_app));
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);

        eventBus = EventBus.getDefault();
        eventBus.register(this);

        application.loadDb();
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onInit(List<Result> results) {
        Log.e(LOG_TAG, "init");
        init();
        eventBus.unregister(this);
    }

    private void init() {

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_status_list, menu);
        return true;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new StatusFragment(), getResources().getString(R.string.title_tab_status));
        adapter.addFragment(new ChartMainFragment(), getResources().getString(R.string.title_tab_chart));
        adapter.addFragment(new StatsCategoryFragment(), getResources().getString(R.string.title_tab_stats));
        viewPager.setAdapter(adapter);
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

    @Override
    public void onStop() {
        eventBus.unregister(this);
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(LOG_TAG, "onDestroy");
        MooderApplication application = (MooderApplication) getApplication();
        application.closeDB();
    }
}
