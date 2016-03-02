package com.paularagones.moode.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.Editable;
import android.util.Log;

import com.paularagones.moode.Constants.Constants;
import com.paularagones.moode.Models.Result;
import com.paularagones.moode.Models.Result;
import com.paularagones.moode.Models.SpinnerResult;
import com.paularagones.moode.Models.Status;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Date;
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
        Cursor cursor = database.rawQuery("SELECT s.StatusID, s.DateStamp, p.PersonName, f.FeelingsDescription, l.LocationDescription, a.ActivityDescription, s.Notes FROM [Status] s,[Feelings] f,[Location] l, [Person] p, [Activity] a WHERE s.FeelingsID = f.FeelingsID AND s.LocationID = l.LocationID AND s.PersonID = p.PersonID AND s.ActivityID = a.ActivityID ORDER BY s.StatusID Desc", null);
        while (cursor.moveToNext()) {
            Status status = new Status();
            status.setStatusID(cursor.getInt(cursor.getColumnIndex("StatusID")));
            status.setDateStamp(cursor.getString(cursor.getColumnIndex("DateStamp")));
            status.setFeelings(cursor.getString(cursor.getColumnIndex("FeelingsDescription")));
            status.setLocation(cursor.getString(cursor.getColumnIndex("LocationDescription")));
            status.setPerson(cursor.getString(cursor.getColumnIndex("PersonName")));
            status.setActivity(cursor.getString(cursor.getColumnIndex("ActivityDescription")));
            status.setNotes(cursor.getString(cursor.getColumnIndex("Notes")));

            statusList.add(status);
//            Log.e(LOG_TAG, status.toString());
        }



        cursor.close();

        close();

        return statusList;
    }

    public List<Result> getFeelingsResultByDate() {
        database = getWritableDatabase();

        List<Result> results = new ArrayList<>();

        Cursor cursor = database.rawQuery("SELECT " +
                "f.FeelingsDescription, COUNT (FeelingsDescription) as NumberOfTimes " +
                "FROM [Status] s,[Feelings] f " +
                "WHERE s.FeelingsID = f.FeelingsID AND date('now','start of month') <= DateStamp " +
                "GROUP BY f.FeelingsDescription",null);

        while (cursor.moveToNext()) {
            Result result = new Result();
            result.setDescription(cursor.getString(cursor.getColumnIndex("FeelingsDescription")));
            result.setNumberOfTimes(cursor.getInt(cursor.getColumnIndex("NumberOfTimes")));
            results.add(result);

//            Log.e(LOG_TAG, result.toString());
        }

        cursor.close();

        close();

        return results;
    }

    public List<Result> getLocationResultByDate() {
        String tableName = String.format(
                "%s AS %s, %s AS %s",
                Constants.Database.TABLE_NAME_STATUS,
                Constants.Database.ALIAS_ONE,
                Constants.Database.TABLE_NAME_LOCATION,
                Constants.Database.ALIAS_TWO);

        String countColumn = String.format(
                "COUNT (%S) AS %s",
                Constants.Database.COLUMN_NAME_LOCATION_DESCRIPTION,
                Constants.Database.COLUMN_NAME_NUMBER_OF_TIMES
                );

        String[] columnNames = {
                Constants.Database.COLUMN_NAME_LOCATION_DESCRIPTION,
                countColumn};

        String selection = String.format(
                "%s.%s = %s.%s AND date('now','start of month') <= %s.%s",
                Constants.Database.ALIAS_ONE,
                Constants.Database.COLUMN_NAME_LOCATION_ID,
                Constants.Database.ALIAS_TWO,
                Constants.Database.COLUMN_NAME_LOCATION_ID,
                Constants.Database.ALIAS_ONE,
                Constants.Database.COLUMN_NAME_DATE_STAMP
                );

        return queryDateChart(tableName, columnNames, selection, Constants.Database.COLUMN_NAME_LOCATION_DESCRIPTION);
    }

    public List<Result> queryDateChart(String tableName, String[] columnName, String selection, String selectedGroupByColumn) {
        database = getWritableDatabase();

        List<Result> results = new ArrayList<>();

        Cursor cursor = database.query(tableName, columnName, selection, null, selectedGroupByColumn, null, null);

//        Cursor cursor = database.rawQuery("SELECT " +
//                "f.FeelingsDescription, COUNT (FeelingsDescription) as NumberOfTimes" +
//                "FROM [Status] s,[Feelings] f" +
//                "WHERE s.FeelingsID = f.FeelingsID AND date('now','start of month') <= DateStamp" +
//                "GROUP BY f.FeelingsDescription;",null);

        while (cursor.moveToNext()) {
            Result result = new Result();
            result.setDescription(cursor.getString(cursor.getColumnIndex(selectedGroupByColumn)));
            result.setNumberOfTimes(cursor.getInt(cursor.getColumnIndex(Constants.Database.COLUMN_NAME_NUMBER_OF_TIMES)));
            results.add(result);

//            Log.e(LOG_TAG, result.toString());
        }

        cursor.close();

        close();

        return results;
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

//            Log.e(LOG_TAG, result.toString());
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

//            Log.e(LOG_TAG, result.toString());

        }

        cursor.close();

        close();

        return results;
    }

    public List<SpinnerResult> getFeelingsList() {

        database = getWritableDatabase();

        List<SpinnerResult> results = new ArrayList<>();

        Cursor cursor = database.rawQuery("select FeelingsID, FeelingsDescription from Feelings ORDER BY FeelingsID",null);

        while (cursor.moveToNext()) {
            SpinnerResult result = new SpinnerResult();
            result.setID(cursor.getInt(cursor.getColumnIndex(Constants.Database.COLUMN_NAME_FEELINGS_ID)));
            result.setDescription(cursor.getString(cursor.getColumnIndex(Constants.Database.COLUMN_NAME_FEELINGS_DESCRIPTION)));
            results.add(result);
//            Log.e(LOG_TAG, result.toString());
        }

        cursor.close();

        close();

        return results;
    }

    public List<SpinnerResult> getPersonList() {

        database = getWritableDatabase();

        List<SpinnerResult> results = new ArrayList<>();

        Cursor cursor = database.rawQuery("select PersonID, PersonName from Person ORDER BY PersonID",null);

        while (cursor.moveToNext()) {
            SpinnerResult result = new SpinnerResult();
            result.setID(cursor.getInt(cursor.getColumnIndex(Constants.Database.COLUMN_NAME_PERSON_ID)));
            result.setDescription(cursor.getString(cursor.getColumnIndex(Constants.Database.COLUMN_NAME_PERSON_NAME)));
            results.add(result);
//            Log.e(LOG_TAG, result.toString());
        }

        cursor.close();

        close();

        return results;
    }

    public List<SpinnerResult> getActivityList() {

        database = getWritableDatabase();

        List<SpinnerResult> results = new ArrayList<>();

        Cursor cursor = database.rawQuery("select ActivityID, ActivityDescription from Activity ORDER BY ActivityID",null);

        while (cursor.moveToNext()) {
            SpinnerResult result = new SpinnerResult();
            result.setID(cursor.getInt(cursor.getColumnIndex(Constants.Database.COLUMN_NAME_ACTIVITY_ID)));
            result.setDescription(cursor.getString(cursor.getColumnIndex(Constants.Database.COLUMN_NAME_ACTIVITY_DESCRIPTION)));
            results.add(result);
//            Log.e(LOG_TAG, result.toString());
        }

        cursor.close();

        close();

        return results;
    }

    public List<SpinnerResult> getLocationList() {

        database = getWritableDatabase();

        List<SpinnerResult> results = new ArrayList<>();

        Cursor cursor = database.rawQuery("select LocationID, LocationDescription from Location ORDER BY LocationID",null);

        while (cursor.moveToNext()) {
            SpinnerResult result = new SpinnerResult();
            result.setID(cursor.getInt(cursor.getColumnIndex(Constants.Database.COLUMN_NAME_LOCATION_ID)));
            result.setDescription(cursor.getString(cursor.getColumnIndex(Constants.Database.COLUMN_NAME_LOCATION_DESCRIPTION)));
            results.add(result);
//            Log.e(LOG_TAG, result.toString());
        }

        cursor.close();

        close();

        return results;
    }

    public void addNewRecord(String tableName, String columnName, String text) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(columnName,text);

//        Log.e(LOG_TAG, "tablename : " + tableName);
//        Log.e(LOG_TAG, "columnName : " + columnName);
//        Log.e(LOG_TAG, "text: " + text);

        database = getWritableDatabase();
        database.beginTransaction();

        try {
            database.insert(tableName, null, contentValues);
            database.setTransactionSuccessful();
            Log.e(LOG_TAG, "insertion successful");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            database.endTransaction();
        }

        close();

    }


    public void addNewStatus(Status status) {
        DateTime dateTime = new DateTime();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.Database.COLUMN_NAME_FEELINGS_ID,status.getFeelingsID());
        contentValues.put(Constants.Database.COLUMN_NAME_LOCATION_ID,status.getLocationID());
        contentValues.put(Constants.Database.COLUMN_NAME_ACTIVITY_ID, status.getActivityID());
        contentValues.put(Constants.Database.COLUMN_NAME_PERSON_ID, status.getPersonID());
        contentValues.put(Constants.Database.COLUMN_NAME_DATE_STAMP, dateTime.toString(Constants.Date.DATE_FORMAT));
        contentValues.put(Constants.Database.COLUMN_NAME_NOTES, status.getNotes());

//        Log.e(LOG_TAG, "status : " + status);

        database = getWritableDatabase();
        database.beginTransaction();

        try {
            database.insert(Constants.Database.TABLE_NAME_STATUS, null, contentValues);
            database.setTransactionSuccessful();
            Log.e(LOG_TAG, "insertion successful");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            database.endTransaction();
        }

        close();
    }
}
