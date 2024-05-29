package com.example.gabit;

import com.google.gson.annotations.SerializedName;

public class VerificationRequest {
    @SerializedName("verificaterUserId")
    private int verificaterUserId;

    @SerializedName("verificatedUserId")
    private int verificatedUserId;

    @SerializedName("missionId")
    private int missionId;

    // Getters and setters
    public int getVerificaterUserId() {
        return verificaterUserId;
    }

    public void setVerificaterUserId(int verificaterUserId) {
        this.verificaterUserId = verificaterUserId;
    }

    public int getVerificatedUserId() {
        return verificatedUserId;
    }

    public void setVerificatedUserId(int verificatedUserId) {
        this.verificatedUserId = verificatedUserId;
    }

    public int getMissionId() {
        return missionId;
    }

    public void setMissionId(int missionId) {
        this.missionId = missionId;
    }
}
