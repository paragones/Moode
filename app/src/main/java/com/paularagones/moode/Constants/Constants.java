package com.paularagones.moode.Constants;

/**
 * Created by Paul.Aragones on 3/1/2016.
 */
public class Constants {

    public class Database {

        public static final String DATABASE_NAME = "Mooder.db";
        public static final int DATABASE_VERSION = 1;

        public static final String TABLE_NAME_ACTIVITY = "Activity";
        public static final String TABLE_NAME_FEELINGS = "Feelings";
        public static final String TABLE_NAME_LOCATION = "Location";
        public static final String TABLE_NAME_PERSON = "Person";
        public static final String TABLE_NAME_STATUS = "Status";

        public static final String COLUMN_NAME_ACTIVITY_ID = "ActivityID";
        public static final String COLUMN_NAME_ACTIVITY_DESCRIPTION = "ActivityDescription";
        public static final String COLUMN_NAME_FEELINGS_ID = "FeelingsID";
        public static final String COLUMN_NAME_FEELINGS_DESCRIPTION = "FeelingsDescription";
        public static final String COLUMN_NAME_LOCATION_ID = "LocationID";
        public static final String COLUMN_NAME_LOCATION_DESCRIPTION = "LocationDescription";
        public static final String COLUMN_NAME_PERSON_ID = "PersonID";
        public static final String COLUMN_NAME_PERSON_NAME = "PersonName";
        public static final String COLUMN_NAME_STATUS_ID = "StatusID";
        public static final String COLUMN_NAME_DATE_STAMP = "DateStamp";
        public static final String COLUMN_NAME_NOTES = "Notes";
        public static final String COLUMN_NAME_NUMBER_OF_TIMES = "NumberOfTimes";

        public static final String ALIAS_ONE = "a";
        public static final String ALIAS_TWO = "b";
        public static final String ALIAS_THREE = "c";
        public static final String ALIAS_FOUR = "d";
        public static final String ALIAS_FIVE = "e";

    }

    public class Date {
        public static final String DATE_FORMAT = "YYYY-MM-DD HH:MM:SS";

    }

}
