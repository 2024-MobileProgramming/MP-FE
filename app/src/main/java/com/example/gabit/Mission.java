package com.example.gabit;

public class Mission {
    private int missionId;
    private String nickname;
    private String missionTitle;
    private String missionShortDescription;
    private  boolean imageProofed;
    private int verificatedCount;

    public Mission(
            int missionId,
            String nickname,
            String missionTitle,
            String missionShortDescription,
            boolean imageProofed,
            int verificatedCount) {
        this.missionId = missionId;
        this.nickname = nickname;
        this.missionTitle = missionTitle;
        this.missionShortDescription = missionShortDescription;
        this.imageProofed = imageProofed;
        this.verificatedCount = verificatedCount;
    }

    public int getMissionId() {
        return missionId;
    }

    public String getNickname() { return nickname; }

    public String getMissionTitle() {
        return missionTitle;
    }

    public String getMissionShortDescription() {
        return missionShortDescription;
    }

    public boolean getImageProofed() {
        return imageProofed;
    }

    public int getVerificatedCount() {
        return verificatedCount;
    }

}
