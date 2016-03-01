package com.paularagones.moode.Models;

/**
 * Created by Paul.Aragones on 2/26/2016.
 */
public class Status {

    private int statusID;
    private String dateStamp;
    private String feelings;
    private String person;
    private String location;
    private String notes;
    private String activity;
    private int feelingsID;
    private int personID;
    private int locationID;
    private int activityID;

    public int getFeelingsID() {
        return feelingsID;
    }

    public void setFeelingsID(int feelingsID) {
        this.feelingsID = feelingsID;
    }

    public int getPersonID() {
        return personID;
    }

    public void setPersonID(int personID) {
        this.personID = personID;
    }

    public int getLocationID() {
        return locationID;
    }

    public void setLocationID(int locationID) {
        this.locationID = locationID;
    }

    public int getActivityID() {
        return activityID;
    }

    public void setActivityID(int activityID) {
        this.activityID = activityID;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getStatusID() {
        return statusID;
    }

    public void setStatusID(int statusID) {
        this.statusID = statusID;
    }

    public String getDateStamp() {
        return dateStamp;
    }

    public void setDateStamp(String dateStamp) {
        this.dateStamp = dateStamp;
    }

    public String getFeelings() {
        return feelings;
    }

    public void setFeelings(String feelings) {
        this.feelings = feelings;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Status{" +
                "statusID=" + statusID +
                ", dateStamp='" + dateStamp + '\'' +
                ", feelings='" + feelings + '\'' +
                ", person='" + person + '\'' +
                ", location='" + location + '\'' +
                ", notes='" + notes + '\'' +
                ", activity='" + activity + '\'' +
                ", feelingsID=" + feelingsID +
                ", personID=" + personID +
                ", locationID=" + locationID +
                ", activityID=" + activityID +
                '}';
    }
}
