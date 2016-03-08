package com.paularagones.moode.Activities;

import android.os.Bundle;

import com.paularagones.moode.Constants.Constants;

/**
 * Created by Paul.Aragones on 3/1/2016.
 */
public class AddNewActivity extends AddNewRecordActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTableName(Constants.Database.TABLE_NAME_ACTIVITY);
        setColumnName(Constants.Database.COLUMN_NAME_ACTIVITY_DESCRIPTION);
        setButtonName(Constants.Database.TABLE_NAME_ACTIVITY);
    }
}
