package com.paularagones.moode.Listeners;

import android.view.View;

/**
 * Created by Mae Anjanette Docena on 3/11/2016.
 */
public interface ItemClickListener {
    void onItemClick(View v, int position);

    boolean onItemLongClick(View v, int position);
}
