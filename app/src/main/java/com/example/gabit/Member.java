package com.example.gabit;

public class Member {
    private String userName;
    private int userId;

    public Member(
            String userName,
            int userId) {
        this.userName = userName;
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public int getuserId() {
        return userId;
    }
}
