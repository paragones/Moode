package com.paularagones.moode.Fragments;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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

    private View view;
    private RecyclerView mRecyclerView;
    private StatusRecyclerAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private int maxHeight = 0;
    private int minHeight = 0;

    private final String LOG_TAG = StatusFragment.class.getSimpleName();

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

            mAdapter = new StatusRecyclerAdapter(statuses, getResources(), new ItemClickListener() {
                @Override
                public void onItemClick(View v, int position) {
                    Log.i(LOG_TAG, "onClick position = " + position);
                    toggleStatusCardView(v);
                }

                @Override
                public boolean onItemLongClick(View v, int position) {
                    Log.i(LOG_TAG, "onLongClick position = " + position);
                    return true;
                }
            });

            mRecyclerView.setAdapter(mAdapter);
            eventBus.removeStickyEvent(statuses);

        }


    }

    public void toggleStatusCardView(final View v) {
        StatusRecyclerAdapter.StatusViewHolder statusViewHolder = new StatusRecyclerAdapter.StatusViewHolder(v);

        maxHeight = (int) (mRecyclerView.getHeight() * 0.5);

        if (statusViewHolder.crdStatus.getTag().equals("collapsed")) {
            // expand cardview
            statusViewHolder.crdStatus.setTag("expanded");
            minHeight = v.getHeight();
            ValueAnimator anim = ValueAnimator.ofInt(v.getHeight(), maxHeight);
            anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    int val = (Integer) valueAnimator.getAnimatedValue();
                    ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
                    layoutParams.height = val;
                    v.setLayoutParams(layoutParams);
                }
            });
            anim.start();
        } else if (statusViewHolder.crdStatus.getTag().equals("expanded")) {
            // collapse cardview
            statusViewHolder.crdStatus.setTag("collapsed");
            ValueAnimator anim = ValueAnimator.ofInt(maxHeight, minHeight);
            anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    int val = (Integer) valueAnimator.getAnimatedValue();
                    ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
                    layoutParams.height = val;
                    v.setLayoutParams(layoutParams);
                }
            });
            anim.start();
        }
    }

    @Override
    public void onStop() {
//        Log.e(LOG_TAG, "onStop");
        eventBus.unregister(this);
        super.onStop();
    }

}
