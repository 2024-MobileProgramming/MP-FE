package com.example.gabit;

import com.google.gson.annotations.SerializedName;

public class ProofRequest {
    @SerializedName("userId")
    private int userId;

    @SerializedName("missionId")
    private int missionId;

    @SerializedName("image")
    private String image;

    // 생성자 추가
    public ProofRequest(int userId, int missionId, String image) {
        this.userId = userId;
        this.missionId = missionId;
        this.image = image;
    }

    // Getters and setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getMissionId() {
        return missionId;
    }

    public void setMissionId(int missionId) {
        this.missionId = missionId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}