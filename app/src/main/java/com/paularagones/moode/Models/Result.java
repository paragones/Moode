package com.paularagones.moode.Models;

/**
 * Created by Paul.Aragones on 2/29/2016.
 */
public class Result {

    private String description;
    private int numberOfTimes;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNumberOfTimes() {
        return numberOfTimes;
    }

    public void setNumberOfTimes(int numberOfTimes) {
        this.numberOfTimes = numberOfTimes;
    }

    @Override
    public String toString() {
        return "Result{" +
                "description='" + description + '\'' +
                ", numberOfTimes=" + numberOfTimes +
                '}';
    }
}
