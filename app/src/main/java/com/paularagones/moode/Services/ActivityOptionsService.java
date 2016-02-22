package com.paularagones.moode.Services;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;

import com.paularagones.moode.Activities.DateActivity;
import com.paularagones.moode.Activities.MoodActivity;
import com.paularagones.moode.Activities.StatusListActivity;
import com.paularagones.moode.Dialog.HelpDialog;
import com.paularagones.moode.R;

/**
 * Created by Paul.Aragones on 2/17/2016.
 */
public class ActivityOptionsService {

    public void openActivity(Activity activity,int id) {
        Intent mainIntent;

        switch (id) {


            case R.id.status_list_activity :
//                if (this != StatusListActivity.class) compare current activity to target activity
                mainIntent = new Intent(activity, StatusListActivity.class);
                activity.startActivity(mainIntent);
                break;
            case R.id.date_activity :
                mainIntent = new Intent(activity, DateActivity.class);
                activity.startActivity(mainIntent);
                break;
            case R.id.mood_chart_activity :
                mainIntent = new Intent(activity, MoodActivity.class);
                activity.startActivity(mainIntent);
                break;
            case R.id.help_activity :
                HelpDialog helpDialog = HelpDialog.newInstance();
                FragmentManager fm = activity.getFragmentManager();
                helpDialog.show(fm, HelpDialog.class.getSimpleName());
                break;
        }
    }
}
