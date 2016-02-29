package com.paularagones.moode.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.Editable;
import android.util.Log;

import com.paularagones.moode.Models.Result;
import com.paularagones.moode.Models.Result;
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

    public List<Status> getStatusList() {
        List<Status> statusList = new ArrayList<>();

        database = getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT s.StatusID, p.PersonName, f.FeelingsDescription, l.LocationDescription, a.ActivityDescription FROM [Status] s,[Feelings] f,[Location] l, [Person] p, [Activity] a WHERE s.FeelingsID = f.FeelingsID AND s.LocationID = l.LocationID AND s.PersonID = p.PersonID AND s.ActivityID = a.Activity.ID", null);
        while (cursor.moveToNext()) {
            Status status = new Status();
            status.setStatusID(cursor.getInt(cursor.getColumnIndex("StatusID")));
            status.setFeelings(cursor.getString(cursor.getColumnIndex("FeelingsDescription")));
            status.setLocation(cursor.getString(cursor.getColumnIndex("LocationDescription")));
            status.setPerson(cursor.getString(cursor.getColumnIndex("PersonName")));
            status.setActivity(cursor.getString(cursor.getColumnIndex("ActivityDescription")));
            statusList.add(status);
            Log.e(LOG_TAG, status.toString());
        }



        cursor.close();

        close();

        return statusList;
    }

    public List<Result> getFeelingsResultByPerson() {
        database = getWritableDatabase();

        List<Result> results = new ArrayList<>();

        Cursor cursor = database.rawQuery("Select LocationDescription, COUNT (LocationDescription) as NumberOfTimes " +
                "FROM " +
                "    [Status] s, [Feelings] f, [Location] l " +
                "WHERE " +
                "     s.FeelingsID = f.FeelingsID AND " +
                "     s.LocationID = l.LocationID AND" +
                "     f.FeelingsID = 1 " +
                "GROUP BY " +
                "    LocationDescription ",null);

        while (cursor.moveToNext()) {
            Result result = new Result();
            result.setDescription(cursor.getString(cursor.getColumnIndex("LocationDescription")));
            result.setNumberOfTimes(cursor.getInt(cursor.getColumnIndex("NumberOfTimes")));
            results.add(result);

            Log.e(LOG_TAG, result.toString());
        }

        cursor.close();

        close();

        return results;
    }

    public List<Result> getFeelingsResultByLocation() {
        database = getWritableDatabase();

        List<Result> results = new ArrayList<>();

        Cursor cursor = database.rawQuery("Select PersonName, COUNT (PersonName) as NumberOfTimes " +
                "FROM " +
                "    [Status] s, [Feelings] f, [Person] p " +
                "WHERE " +
                "     s.FeelingsID = f.FeelingsID AND " +
                "     s.LocationID = p.PersonID AND" +
                "     f.FeelingsID = 1 " +
                "GROUP BY " +
                "    PersonName ",null);

        while (cursor.moveToNext()) {
            Result result = new Result();
            result.setDescription(cursor.getString(cursor.getColumnIndex("PersonName")));
            result.setNumberOfTimes(cursor.getInt(cursor.getColumnIndex("NumberOfTimes")));
            results.add(result);

            Log.e(LOG_TAG, result.toString());

        }

        cursor.close();

        close();

        return results;
    }

    public void addNewSomething(String tableName, String columnName, String text) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(columnName,text);

        database.beginTransaction();

        try {
            database.insert(tableName, null, contentValues);
            database.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            database.endTransaction();
        }

    }
}
