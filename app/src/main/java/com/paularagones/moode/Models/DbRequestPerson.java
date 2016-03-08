package com.paularagones.moode.Models;

import com.paularagones.moode.Constants.Constants;

/**
 * Created by Paul.Aragones on 3/3/2016.
 */
public class DbRequestPerson extends DbRequest {

    public DbRequestPerson() {
        setTableID(Constants.Database.COLUMN_NAME_PERSON_ID);
        setTableName(Constants.Database.TABLE_NAME_PERSON);
        setColumnName(Constants.Database.COLUMN_NAME_PERSON_NAME);
    }
}
