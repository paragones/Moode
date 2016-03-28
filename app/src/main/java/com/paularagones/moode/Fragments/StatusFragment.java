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
import com.paularagones.moode.Services.StatusService;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * Created by Mae Anjanette Docena on 3/4/2016.
 */
public class StatusFragment extends Fragment {

    public static StatusRecyclerAdapter mAdapter;

    private View view;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;

    private final String TAG = "Moode-StatusFragment";

    private EventBus eventBus;

    public StatusFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



//        Log.e(TAG, "StatusFragment onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


//        Log.e(TAG, "StatusFragment onCreateView");

        view = inflater.inflate(R.layout.status_list, null);
        LinearLayout linearLayout = (LinearLayout) view;
        mRecyclerView = (RecyclerView) linearLayout.findViewById(R.id.status_recycler);
        ((ViewGroup) mRecyclerView.getParent()).removeView(mRecyclerView);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        return mRecyclerView;
    }

    @Override
    public void onStart() {
//        Log.e(TAG, "onStart");
        super.onStart();

        eventBus = EventBus.getDefault();
        eventBus.register(this);

        StatusService statusService = StatusService.newInstance(getContext());
        statusService.getStatusList(); //Async Task to call onReturnAdapter once finished
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onReturnList(List<Status> statuses) {
//        Log.e(LOG_TAG, "onReturnList");

        if (statuses.get(1) instanceof Status) {

            mAdapter = new StatusRecyclerAdapter(statuses, getResources());
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.addItemDecoration(new StickyRecyclerHeadersDecoration(mAdapter));
            eventBus.removeStickyEvent(statuses);

        }


    }


    @Override
    public void onStop() {
//        Log.e(LOG_TAG, "onStop");
        eventBus.unregister(this);
        super.onStop();
    }

}
