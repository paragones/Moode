package com.paularagones.moode.Fragments;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.paularagones.moode.Adapters.StatusRecyclerAdapter;
import com.paularagones.moode.Adapters.StatusRecyclerAdapter.StatusViewHolder;
import com.paularagones.moode.Database.DBAdapter;
import com.paularagones.moode.Listeners.ItemClickListener;
import com.paularagones.moode.Models.Status;
import com.paularagones.moode.R;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

import java.util.List;

/**
 * Created by Mae Anjanette Docena on 3/4/2016.
 */
public class StatusFragment extends Fragment {

    public static StatusRecyclerAdapter mAdapter;

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;

    private final String TAG = "Moode-StatusFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d(TAG, "StatusFragment onCreateView");

        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.status_list, null);
        mRecyclerView = (RecyclerView)layout.findViewById(R.id.status_recycler);

        ((ViewGroup)mRecyclerView.getParent()).removeView(mRecyclerView);

        ///TODO DB shouldn't be called here create an async task for this
        DBAdapter dbAdapter = DBAdapter.newInstance(getContext());
        List<Status> statuses = dbAdapter.getStatusList();

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new StatusRecyclerAdapter(getContext(), statuses, getResources());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new StickyRecyclerHeadersDecoration(mAdapter));

        return mRecyclerView;
    }
}
