package com.paularagones.moode.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by Paul.Aragones on 2/24/2016.
 */
public class DBAdapter extends SQLiteAssetHelper {

    private static final String LOG_TAG = DBAdapter.class.getSimpleName();
    private SQLiteDatabase database;
    private static final String DATABASE_NAME = "Mooder.db";
    private static final int DATABASE_VERSION = 1;

    public DBAdapter(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void executeStatus() {
        database = getWritableDatabase();


        Cursor cursor = database.rawQuery("SELECT * FROM [Status] s,[Feelings] f,[Location] l WHERE s.FeelingsID = f.FeelingsID AND s.LocationID = l.LocationID",null);
        while (cursor.moveToNext()) {
            Log.e(LOG_TAG, "Status ID : " + cursor.getInt(cursor.getColumnIndex("StatusID")));
            Log.e(LOG_TAG, "FeelingsID : " + cursor.getInt(cursor.getColumnIndex("FeelingsID")));
            Log.e(LOG_TAG, "LocationID : " + cursor.getInt(cursor.getColumnIndex("LocationID")));
            Log.e(LOG_TAG, "PersonID : " + cursor.getInt(cursor.getColumnIndex("PersonID")));
        }

        cursor.close();

        close();
    }

    public void executeFeelingsLocation() {
        database = getWritableDatabase();


        Cursor cursor = database.rawQuery("Select FeelingsDescription, LocationDescription, COUNT (LocationDescription) as NumberOfTimes " +
                "FROM " +
                "    [Status] s, [Feelings] f, [Location] l " +
                "WHERE " +
                "     s.FeelingsID = f.FeelingsID AND " +
                "     s.LocationID = l.LocationID AND" +
                "     f.FeelingsID = 1 " +
                "GROUP BY " +
                "    LocationDescription ",null);
        
        while (cursor.moveToNext()) {
            Log.e(LOG_TAG, "FeelingsDescription : " + cursor.getString(cursor.getColumnIndex("FeelingsDescription")));
            Log.e(LOG_TAG, "LocationDescription : " + cursor.getString(cursor.getColumnIndex("LocationDescription")));
            Log.e(LOG_TAG, "NumberOfTimes : " + cursor.getInt(cursor.getColumnIndex("NumberOfTimes")));
        }

        cursor.close();

        close();
    }
}
