package com.paularagones.moode.Listeners;

import android.view.View;
import android.view.ViewGroup;

import com.paularagones.moode.Adapters.StatusRecyclerAdapter;

/**
 * Created by Mae Anjanette Docena on 3/11/2016.
 */
public interface ItemClickListener {
    void onItemClick(View v, int position);

    boolean onItemLongClick(View v, int position);
}
