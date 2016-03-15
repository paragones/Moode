package com.paularagones.moode.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.paularagones.moode.Adapters.ViewPagerAdapter;
import com.paularagones.moode.Application.MooderApplication;
import com.paularagones.moode.Models.DbRequest;
import com.paularagones.moode.Models.DbRequestActivity;
import com.paularagones.moode.Models.DbRequestFeelings;
import com.paularagones.moode.Models.DbRequestLocation;
import com.paularagones.moode.Models.DbRequestPerson;
import com.paularagones.moode.Models.SpinnerResult;
import com.paularagones.moode.R;

import java.util.List;

/**
 * Created by Mae Anjanette Docena on 3/4/2016.
 */
public class StatsCategoryFragment extends Fragment implements View.OnClickListener {

    private TextView titleName;
    private Button leftButton;
    private Button rightButton;
    private ViewPager statsViewpager;

    private DbRequest dbRequest;
    private List<SpinnerResult> results;

    public static StatsCategoryFragment newInstance(DbRequest dbRequest) {
        StatsCategoryFragment statsFragment = new StatsCategoryFragment();
        statsFragment.setDbRequest(dbRequest);

        return statsFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        MooderApplication application = (MooderApplication) getActivity().getApplication();
        results = application.getCategoryStat(dbRequest);

        View view = inflater.inflate(R.layout.fragment_chart_child, null, false);

        leftButton = (Button) view.findViewById(R.id.left_button);
        rightButton = (Button) view.findViewById(R.id.right_button);
        titleName = (TextView) view.findViewById(R.id.title_name);
        statsViewpager = (ViewPager) view.findViewById(R.id.chart_viewpager);

        leftButton.setOnClickListener(this);
        rightButton.setOnClickListener(this);

        setupViewPager(statsViewpager);


        
        return view;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        viewPagerAdapter.addFragment(StatFragment.newInstance(dbRequest, results), getResources().getString(R.string.title_feelings));
        viewPagerAdapter.addFragment(StatFragment.newInstance(dbRequest, results), getResources().getString(R.string.title_activity));
        viewPagerAdapter.addFragment(StatFragment.newInstance(dbRequest, results), getResources().getString(R.string.title_location));
        viewPagerAdapter.addFragment(StatFragment.newInstance(dbRequest, results), getResources().getString(R.string.title_person));
        viewPager.setAdapter(viewPagerAdapter);

        viewPager.setCurrentItem(0, true);
        titleName.setText(viewPagerAdapter.getPageTitle(0));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.right_button:
                if(statsViewpager.getCurrentItem() < (statsViewpager.getAdapter().getCount()-1)) {
                    statsViewpager.setCurrentItem(statsViewpager.getCurrentItem() + 1, true);
                    rightButton.setEnabled(true);
                    leftButton.setEnabled(true);
                } else {
                    statsViewpager.setCurrentItem(statsViewpager.getLeft());
                }
//                Log.e(LOG_TAG, "getRight " + (statsViewpager.getAdapter().getCount()));
//                Log.e(LOG_TAG, "current item " + statsViewpager.getCurrentItem());
//                Log.e(LOG_TAG, "title " + statsViewpager.getAdapter().getPageTitle(statsViewpager.getCurrentItem()));

                break;
            case R.id.left_button:
                if (statsViewpager.getCurrentItem() > statsViewpager.getLeft()) {
                    statsViewpager.setCurrentItem(statsViewpager.getCurrentItem() - 1, true);
                    leftButton.setEnabled(true);
                    rightButton.setEnabled(true);
                } else {
                    statsViewpager.setCurrentItem(statsViewpager.getRight());
                }
//                Log.e(LOG_TAG, "getLeft " + (statsViewpager.getLeft()));
//                Log.e(LOG_TAG, "current item " + statsViewpager.getCurrentItem());
//                Log.e(LOG_TAG, "title " + statsViewpager.getAdapter().getPageTitle(statsViewpager.getCurrentItem()));
                break;
        }

        titleName.setText(statsViewpager.getAdapter().getPageTitle(statsViewpager.getCurrentItem()));
    }

    public DbRequest getDbRequest() {
        return dbRequest;
    }

    public void setDbRequest(DbRequest dbRequest) {
        this.dbRequest = dbRequest;
    }



}