package com.paularagones.moode.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.util.Log;

import com.paularagones.moode.Constants.Constants;
import com.paularagones.moode.Models.DbRequest;
import com.paularagones.moode.Models.Result;
import com.paularagones.moode.Models.SpinnerResult;
import com.paularagones.moode.Models.Status;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Paul.Aragones on 2/24/2016.
 */
public class DBAdapter extends SQLiteAssetHelper {

    private static final String LOG_TAG = DBAdapter.class.getSimpleName();
    private SQLiteDatabase database;

    private static DBAdapter instance;


    private DBAdapter(Context context) {
        super(context, Constants.Database.DATABASE_NAME, null, Constants.Database.DATABASE_VERSION);
    }

    public static DBAdapter newInstance(Context context) {

        if (instance == null) {
            return new DBAdapter(context);
        } else {
            return instance;

        }

    }

    public List<Status> getStatusList() {
        List<Status> statusList = new ArrayList<>();

        database = getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT s.StatusID, s.FeelingsID, s.PersonID, s.LocationID, s.ActivityID, s.DateStamp, p.PersonName, f.FeelingsDescription, l.LocationDescription, a.ActivityDescription, s.Notes FROM [Status] s,[Feelings] f,[Location] l, [Person] p, [Activity] a WHERE s.FeelingsID = f.FeelingsID AND s.LocationID = l.LocationID AND s.PersonID = p.PersonID AND s.ActivityID = a.ActivityID ORDER BY s.StatusID Desc", null);
        while (cursor.moveToNext()) {
            Status status = new Status();
            status.setStatusID(cursor.getInt(cursor.getColumnIndex("StatusID")));
            status.setDateStamp(cursor.getString(cursor.getColumnIndex("DateStamp")));
            status.setFeelings(cursor.getString(cursor.getColumnIndex("FeelingsDescription")));
            status.setLocation(cursor.getString(cursor.getColumnIndex("LocationDescription")));
            status.setPerson(cursor.getString(cursor.getColumnIndex("PersonName")));
            status.setActivity(cursor.getString(cursor.getColumnIndex("ActivityDescription")));
            status.setNotes(cursor.getString(cursor.getColumnIndex("Notes")));
            status.setFeelingsID(cursor.getInt(cursor.getColumnIndex("FeelingsID")));
            status.setActivityID(cursor.getInt(cursor.getColumnIndex("ActivityID")));
            status.setPersonID(cursor.getInt(cursor.getColumnIndex("PersonID")));
            status.setLocationID(cursor.getInt(cursor.getColumnIndex("LocationID")));

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

    public List<Result> getDateResult(DbRequest dbRequest) {

        String selection = String.format(
                "%s.%s = %s.%s AND date('now','start of month') <= %s.%s",
                Constants.Database.ALIAS_ONE,
                dbRequest.getTableID(),
                Constants.Database.ALIAS_TWO,
                dbRequest.getTableID(),
                Constants.Database.ALIAS_ONE,
                Constants.Database.COLUMN_NAME_DATE_STAMP
        );

        return prepareTablesAndColumnNames(selection, dbRequest);
    }

    public List<Result> getResultList(String resultTableID, int ID, DbRequest dbRequest) {

        String selection = String.format(
                "%s.%s = %s.%s AND %s.%s = %s",
                Constants.Database.ALIAS_ONE,
                dbRequest.getTableID(),
                Constants.Database.ALIAS_TWO,
                dbRequest.getTableID(),
                Constants.Database.ALIAS_ONE,
                resultTableID,
                ID
        );

        return prepareTablesAndColumnNames(selection, dbRequest);
    }


    public List<Result> prepareTablesAndColumnNames(String selection, DbRequest dbRequest) {
        String tableName = prepareTableName(dbRequest);

        String countColumn = prepareCountColumn(dbRequest);

        String[] columnNames = prepareColumnNames(dbRequest, countColumn);

        return queryResult(tableName, columnNames, selection, dbRequest.getColumnName());
    }

    @NonNull
    private String[] prepareColumnNames(DbRequest dbRequest, String countColumn) {
        return new String[]{
                    dbRequest.getColumnName(),
                    countColumn};
    }

    private String prepareCountColumn(DbRequest dbRequest) {
        return String.format(
                    "COUNT (%S) AS %s",
                    dbRequest.getColumnName(),
                    Constants.Database.COLUMN_NAME_NUMBER_OF_TIMES
                    );
    }

    private String prepareTableName(DbRequest dbRequest) {
        return String.format(
                    "%s AS %s, %s AS %s",
                    Constants.Database.TABLE_NAME_STATUS,
                    Constants.Database.ALIAS_ONE,
                    dbRequest.getTableName(),
                    Constants.Database.ALIAS_TWO);
    }

    public List<Result> queryResult(String tableNames, String[] columnName, String selection, String selectedGroupByColumn) {
        database = getReadableDatabase();

        List<Result> results = new ArrayList<>();

        Cursor cursor = database.query(tableNames, columnName, selection, null, selectedGroupByColumn, null, selectedGroupByColumn);

        while (cursor.moveToNext()) {
            Result result = new Result();
            result.setDescription(cursor.getString(cursor.getColumnIndex(selectedGroupByColumn)));
            result.setNumberOfTimes(cursor.getInt(cursor.getColumnIndex(Constants.Database.COLUMN_NAME_NUMBER_OF_TIMES)));
            results.add(result);

        }

        cursor.close();

        close();

        return results;
    }


    public List<SpinnerResult> getListResult(DbRequest dbRequest) {
        database = getWritableDatabase();

        List<SpinnerResult> results = new ArrayList<>();

        String[] columnNames = {
                dbRequest.getTableID(),
                dbRequest.getColumnName(),
                };

        Cursor cursor = database.query(dbRequest.getTableName(), columnNames, null, null, null, null, dbRequest.getTableID());

        while (cursor.moveToNext()) {
            SpinnerResult result = new SpinnerResult();
            result.setID(cursor.getInt(cursor.getColumnIndex(dbRequest.getTableID())));
            result.setDescription(cursor.getString(cursor.getColumnIndex(dbRequest.getColumnName())));
            results.add(result);
        }

        cursor.close();

        close();

        return results;
    }

    public List<SpinnerResult> getFeelingsList() {

        database = getWritableDatabase();

        List<SpinnerResult> results = new ArrayList<>();

        Cursor cursor = database.rawQuery("select FeelingsID, FeelingsDescription from Feelings ORDER BY FeelingsID", null);

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

    public void deleteStatus(Status status) {
        String whereClause = String.format("%s = %s", Constants.Database.COLUMN_NAME_STATUS_ID, status.getStatusID());

        database = getWritableDatabase();
        database.beginTransaction();

        try {
            database.delete(Constants.Database.TABLE_NAME_STATUS, whereClause, null);
            database.setTransactionSuccessful();
            Log.e(LOG_TAG, "deletion successful");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            database.endTransaction();
        }

        close();
    }

    public void updateStatus(Status status) {
        DateTime dateTime = new DateTime();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.Database.COLUMN_NAME_FEELINGS_ID,status.getFeelingsID());
        contentValues.put(Constants.Database.COLUMN_NAME_LOCATION_ID,status.getLocationID());
        contentValues.put(Constants.Database.COLUMN_NAME_ACTIVITY_ID, status.getActivityID());
        contentValues.put(Constants.Database.COLUMN_NAME_PERSON_ID, status.getPersonID());
        contentValues.put(Constants.Database.COLUMN_NAME_DATE_STAMP, dateTime.toString(Constants.Date.DATE_FORMAT));
        contentValues.put(Constants.Database.COLUMN_NAME_NOTES, status.getNotes());

        String whereClause = String.format("%s = %s", Constants.Database.COLUMN_NAME_STATUS_ID, status.getStatusID());

        Log.e(LOG_TAG, "update " + status.toString());

        database = getWritableDatabase();
        database.beginTransaction();

        try {
            database.update(Constants.Database.TABLE_NAME_STATUS, contentValues, whereClause, null);
            database.setTransactionSuccessful();
            Log.e(LOG_TAG, "update successful");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            database.endTransaction();
        }

        close();
    }
}
