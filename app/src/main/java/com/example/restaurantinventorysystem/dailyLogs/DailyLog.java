package com.example.restaurantinventorysystem.dailyLogs;

import java.io.Serializable;

public class DailyLog implements Serializable {


    private String updatedItemName;
    private String updateType;
    private int amountUpdated;
    private double amountSpent;

    //Needed for firebase
    public DailyLog(){

    }

    public DailyLog(String updatedItemName, String updateType, int amountUpdated, double amountSpent) {
        this.updatedItemName = updatedItemName;
        this.updateType = updateType;
        this.amountUpdated = amountUpdated;
        this.amountSpent = amountSpent;
    }

    public String getUpdatedItemName() {
        return updatedItemName;
    }

    public String getUpdateType() {
        return updateType;
    }

    public int getAmountUpdated() {
        return amountUpdated;
    }

    public double getAmountSpent() {
        return amountSpent;
    }



}
