package com.paularagones.moode.Services;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;

import com.paularagones.moode.Activities.AddNewActivity;
import com.paularagones.moode.Activities.AddNewFeelingsActivity;
import com.paularagones.moode.Activities.AddNewLocationActivity;
import com.paularagones.moode.Activities.AddNewPersonActivity;
import com.paularagones.moode.Activities.AddNewStatusActivity;
import com.paularagones.moode.Activities.DateActivity;
import com.paularagones.moode.Activities.DateActivity2;
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
            case R.id.add_new_status_list_activity :
                mainIntent = new Intent(activity, AddNewStatusActivity.class);
                activity.startActivity(mainIntent);
                break;
            case R.id.add_new_status_feelings_activity :
                mainIntent = new Intent(activity, AddNewFeelingsActivity.class);
                activity.startActivity(mainIntent);
                break;
            case R.id.add_new_status_location_activity :
                mainIntent = new Intent(activity, AddNewLocationActivity.class);
                activity.startActivity(mainIntent);
                break;
            case R.id.add_new_activity :
                mainIntent = new Intent(activity, AddNewActivity.class);
                activity.startActivity(mainIntent);
                break;
            case R.id.add_new_status_person_activity :
                mainIntent = new Intent(activity, AddNewPersonActivity.class);
                activity.startActivity(mainIntent);
                break;
            case R.id.date_activity :
                mainIntent = new Intent(activity, DateActivity2.class);
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
