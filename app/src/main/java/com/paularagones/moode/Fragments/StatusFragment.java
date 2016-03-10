package com.paularagones.moode.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.paularagones.moode.Adapters.StatusRecyclerAdapter;
import com.paularagones.moode.Database.DBAdapter;
import com.paularagones.moode.Models.Status;
import com.paularagones.moode.R;

import java.util.List;

/**
 * Created by Mae Anjanette Docena on 3/4/2016.
 */
public class StatusFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private final String TAG = "Moode-StatusFragment";

    public StatusFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "StatusFragment onCreate");
    }
/*
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.status_list, container, false);
    }*/

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

        mAdapter = new StatusRecyclerAdapter(statuses);
        mRecyclerView.setAdapter(mAdapter);

        return mRecyclerView;
    }
}
