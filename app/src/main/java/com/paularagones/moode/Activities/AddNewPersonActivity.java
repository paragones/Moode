package com.paularagones.moode.Activities;

import android.os.Bundle;

import com.paularagones.moode.Constants.Constants;

/**
 * Created by Paul.Aragones on 3/1/2016.
 */
public class AddNewPersonActivity extends AddNewRecordActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTableName(Constants.Database.TABLE_NAME_PERSON);
        setColumnName(Constants.Database.COLUMN_NAME_PERSON_NAME);
        setButtonName(Constants.Database.TABLE_NAME_PERSON);
    }
}
