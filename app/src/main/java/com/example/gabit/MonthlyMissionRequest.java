package com.example.gabit;

import com.google.gson.annotations.SerializedName;

public class MonthlyMissionRequest {
    @SerializedName("userId")
    private String userId;

    @SerializedName("year")
    private int year;

    @SerializedName("month")
    private int month;

    public MonthlyMissionRequest(String userId, int year, int month) {
        this.userId = userId;
        this.year = year;
        this.month = month;
    }

    // Getters and setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
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
