package com.paularagones.moode.Models;

/**
 * Created by Paul.Aragones on 2/29/2016.
 */
public class SpinnerResult {

    private String description;
    private int ID;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    @Override
    public String toString() {
        return "SpinnerResult{" +
                "description='" + description + '\'' +
                ", ID=" + ID +
                '}';
    }
}
