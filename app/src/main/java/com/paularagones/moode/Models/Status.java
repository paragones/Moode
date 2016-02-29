package com.paularagones.moode.Models;

/**
 * Created by Paul.Aragones on 2/26/2016.
 */
public class Status {

    private String DateStamp;
    private int FeelingsID;
    private int StatusID;
    private int PersonID;
    private int LocationID;

    public String getDateStamp() {
        return DateStamp;
    }

    public void setDateStamp(String dateStamp) {
        DateStamp = dateStamp;
    }

    public int getFeelingsID() {
        return FeelingsID;
    }

    public void setFeelingsID(int feelingsID) {
        FeelingsID = feelingsID;
    }

    public int getStatusID() {
        return StatusID;
    }

    public void setStatusID(int statusID) {
        StatusID = statusID;
    }

    public int getPersonID() {
        return PersonID;
    }

    public void setPersonID(int personID) {
        PersonID = personID;
    }

    public int getLocationID() {
        return LocationID;
    }

    public void setLocationID(int locationID) {
        LocationID = locationID;
    }
}
