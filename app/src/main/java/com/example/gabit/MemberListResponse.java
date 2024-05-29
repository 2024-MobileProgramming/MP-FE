package com.example.gabit;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class MemberListResponse {
    @SerializedName("status")
    private int status;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private List<Member> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Member> getData() {
        return data;
    }

    public void setData(List<Member> data) {
        this.data = data;
    }

    public static class Member {
        @SerializedName("userId")
        private int userId;

        @SerializedName("userName")
        private String userName;

        // Getters and setters
        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }
    }
}
