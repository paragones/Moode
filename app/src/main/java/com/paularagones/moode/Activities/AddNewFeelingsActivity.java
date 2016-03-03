package com.paularagones.moode.Activities;

import android.os.Bundle;

import com.paularagones.moode.Constants.Constants;

/**
 * Created by Paul.Aragones on 3/1/2016.
 */
public class AddNewFeelingsActivity extends AddNewAbstract {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTableName(Constants.Database.TABLE_NAME_FEELINGS);
        setColumnName(Constants.Database.COLUMN_NAME_FEELINGS_DESCRIPTION);
        setButtonName(Constants.Database.TABLE_NAME_FEELINGS);

    }
}
