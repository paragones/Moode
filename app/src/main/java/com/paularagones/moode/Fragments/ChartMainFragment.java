package com.paularagones.moode.Fragments;

import android.support.v4.app.Fragment;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.support.v4.view.ViewPager;

import com.paularagones.moode.Adapters.ViewPagerAdapter;
import com.paularagones.moode.Models.DbRequestActivity;
import com.paularagones.moode.Models.DbRequestFeelings;
import com.paularagones.moode.Models.DbRequestLocation;
import com.paularagones.moode.Models.DbRequestPerson;
import com.paularagones.moode.R;

public class ChartMainFragment extends Fragment implements View.OnClickListener {

    private static final String LOG_TAG = ChartMainFragment.class.getSimpleName();
    private TextView titleName;
    private Button leftButton;
    private Button rightButton;
    private ViewPager chartViewpager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chart_main, null, false);

        leftButton = (Button) view.findViewById(R.id.left_button);
        rightButton = (Button) view.findViewById(R.id.right_button);
        titleName = (TextView) view.findViewById(R.id.title_name);
        chartViewpager = (ViewPager) view.findViewById(R.id.chart_viewpager);

        leftButton.setOnClickListener(this);
        rightButton.setOnClickListener(this);

        setupViewPager(chartViewpager);

        return view;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        viewPagerAdapter.addFragment(ChartFragment.newInstance(new DbRequestFeelings()), getResources().getString(R.string.title_feelings));
        viewPagerAdapter.addFragment(ChartFragment.newInstance(new DbRequestActivity()), getResources().getString(R.string.title_activity));
        viewPagerAdapter.addFragment(ChartFragment.newInstance(new DbRequestLocation()), getResources().getString(R.string.title_location));
        viewPagerAdapter.addFragment(ChartFragment.newInstance(new DbRequestPerson()), getResources().getString(R.string.title_person));
        viewPager.setAdapter(viewPagerAdapter);

        viewPager.setCurrentItem(0, true);
        titleName.setText(viewPagerAdapter.getPageTitle(0));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.right_button:
                if(chartViewpager.getCurrentItem() < (chartViewpager.getAdapter().getCount()-1)) {
                    chartViewpager.setCurrentItem(chartViewpager.getCurrentItem() + 1, true);
                    rightButton.setEnabled(true);
                    leftButton.setEnabled(true);
                } else {
                    chartViewpager.setCurrentItem(chartViewpager.getLeft());
                }
//                Log.e(LOG_TAG, "getRight " + (chartViewpager.getAdapter().getCount()));
//                Log.e(LOG_TAG, "current item " + chartViewpager.getCurrentItem());
//                Log.e(LOG_TAG, "title " + chartViewpager.getAdapter().getPageTitle(chartViewpager.getCurrentItem()));

                break;
            case R.id.left_button:
                if (chartViewpager.getCurrentItem() > chartViewpager.getLeft()) {
                    chartViewpager.setCurrentItem(chartViewpager.getCurrentItem() - 1, true);
                    leftButton.setEnabled(true);
                    rightButton.setEnabled(true);
                } else {
                    chartViewpager.setCurrentItem(chartViewpager.getRight());
                }
//                Log.e(LOG_TAG, "getLeft " + (chartViewpager.getLeft()));
//                Log.e(LOG_TAG, "current item " + chartViewpager.getCurrentItem());
//                Log.e(LOG_TAG, "title " + chartViewpager.getAdapter().getPageTitle(chartViewpager.getCurrentItem()));
                break;
        }

        titleName.setText(chartViewpager.getAdapter().getPageTitle(chartViewpager.getCurrentItem()));
    }
}
