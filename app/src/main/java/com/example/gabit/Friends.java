package com.example.gabit;

public class Friends {
    private String userNickname;
    private int userId;

    public Friends(
            String userNickname,
            int userId) {
        this.userNickname = userNickname;
        this.userId = userId;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public int getuserId() {
        return userId;
    }
}
