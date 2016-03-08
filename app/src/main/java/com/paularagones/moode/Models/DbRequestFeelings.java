package com.paularagones.moode.Models;

import com.paularagones.moode.Constants.Constants;

/**
 * Created by Paul.Aragones on 3/3/2016.
 */
public class DbRequestFeelings extends DbRequest {

    public DbRequestFeelings() {
        setTableID(Constants.Database.COLUMN_NAME_FEELINGS_ID);
        setTableName(Constants.Database.TABLE_NAME_FEELINGS);
        setColumnName(Constants.Database.COLUMN_NAME_FEELINGS_DESCRIPTION);
    }
}
