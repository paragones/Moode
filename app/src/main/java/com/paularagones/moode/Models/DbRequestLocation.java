package com.paularagones.moode.Models;

import com.paularagones.moode.Constants.Constants;

/**
 * Created by Paul.Aragones on 3/3/2016.
 */
public class DbRequestLocation extends DbRequest {

    public DbRequestLocation () {
        setTableID(Constants.Database.COLUMN_NAME_LOCATION_ID);
        setTableName(Constants.Database.TABLE_NAME_LOCATION);
        setColumnName(Constants.Database.COLUMN_NAME_LOCATION_DESCRIPTION);
    }
}
