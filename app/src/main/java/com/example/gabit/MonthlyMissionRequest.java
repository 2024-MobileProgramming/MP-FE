package com.example.gabit;

import com.google.gson.annotations.SerializedName;

public class MonthlyMissionRequest {
    @SerializedName("userId")
    private int userId;

    @SerializedName("year")
    private int year;

    @SerializedName("month")
    private int month;

    // Getters and setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }
}
