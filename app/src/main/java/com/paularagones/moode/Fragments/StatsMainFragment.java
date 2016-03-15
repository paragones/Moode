package com.paularagones.moode.Fragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.paularagones.moode.Adapters.ViewPagerAdapter;
import com.paularagones.moode.Models.DbRequestActivity;
import com.paularagones.moode.Models.DbRequestFeelings;
import com.paularagones.moode.Models.DbRequestLocation;
import com.paularagones.moode.Models.DbRequestPerson;
import com.paularagones.moode.R;

/**
 * Created by Mae Anjanette Docena on 3/4/2016.
 */
public class StatsMainFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    public StatsMainFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_stats_main, null, false);

        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        tabLayout = (TabLayout) view.findViewById(R.id.tabs);

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        viewPagerAdapter.addFragment(StatsCategoryFragment.newInstance(new DbRequestFeelings()), getResources().getString(R.string.title_feelings));
        viewPagerAdapter.addFragment(StatsCategoryFragment.newInstance(new DbRequestActivity()), getResources().getString(R.string.title_activity));
        viewPagerAdapter.addFragment(StatsCategoryFragment.newInstance(new DbRequestLocation()), getResources().getString(R.string.title_location));
        viewPagerAdapter.addFragment(StatsCategoryFragment.newInstance(new DbRequestPerson()), getResources().getString(R.string.title_person));
        viewPager.setAdapter(viewPagerAdapter);
    }
}