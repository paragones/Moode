package com.paularagones.moode.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.paularagones.moode.Models.Status;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

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

    public List<Status> executeStatus() {
        List<Status> statusList = new ArrayList<>();

        database = getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT s.StatusID, p.PersonName, f.FeelingsDescription, l.LocationDescription FROM [Status] s,[Feelings] f,[Location] l, [Person] p WHERE s.FeelingsID = f.FeelingsID AND s.LocationID = l.LocationID AND s.PersonID = p.PersonID", null);
        while (cursor.moveToNext()) {
            Status status = new Status();
            status.setStatusID(cursor.getInt(cursor.getColumnIndex("StatusID")));
            status.setFeelings(cursor.getString(cursor.getColumnIndex("FeelingsDescription")));
            status.setLocation(cursor.getString(cursor.getColumnIndex("LocationID")));
            status.setPerson(cursor.getString(cursor.getColumnIndex("PersonID")));
            statusList.add(status);
            Log.e(LOG_TAG, status.toString());
        }



        cursor.close();

        close();

        return statusList;
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
