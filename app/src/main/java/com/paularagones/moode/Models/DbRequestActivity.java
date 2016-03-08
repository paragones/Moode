package com.paularagones.moode.Models;

import com.paularagones.moode.Constants.Constants;

/**
 * Created by Paul.Aragones on 3/3/2016.
 */
public class DbRequestActivity extends DbRequest {

    public DbRequestActivity() {
        setTableID(Constants.Database.COLUMN_NAME_ACTIVITY_ID);
        setTableName(Constants.Database.TABLE_NAME_ACTIVITY);
        setColumnName(Constants.Database.COLUMN_NAME_ACTIVITY_DESCRIPTION);
    }
}
