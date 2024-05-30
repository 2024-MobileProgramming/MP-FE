package com.example.gabit;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;


public class OneMissionActivity extends AppCompatActivity {
    private TextView missionTitle;
    private TextView missionDescription;
    private ImageView missionImage;
    private ImageView missionStatus;
    private String userId;
    private String viewerId;
    private int missionId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onemission);

        missionTitle = findViewById(R.id.MissionTitle);
        missionDescription = findViewById(R.id.MissionDescription);
        missionImage = findViewById(R.id.MissionImage);
        missionStatus = findViewById(R.id.MissionStatus);

        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        userId = sharedPreferences.getString("userId", "");

        viewerId = getIntent().getStringExtra("userId");
        missionId = getIntent().getIntExtra("missionId", -1);

        fetchMissionDetails(userId, missionId, viewerId);
    }

    private void fetchMissionDetails(String userId, int missionId, String viewerId) {
        MissionService missionService = new MissionService();
        missionService.getOneMission(userId, missionId, viewerId, new MissionService.MissionDetailCallback() {
            @Override
            public void onSuccess(MissionDetail missionDetail) {
                updateUI(missionDetail);
            }

            @Override
            public void onError(String errorMessage) {
                Log.e("OneMissionActivity", errorMessage);
            }
        });
    }

    private void updateUI(MissionDetail missionDetail) {
        missionTitle.setText(missionDetail.getMissionTitle());
        missionDescription.setText(missionDetail.getMissionDescription());
    }

    public static class MissionDetailResponse {
        private int status;
        private String message;
        private  MissionDetail data;

        public int getStatus() {
            return status;
        }

        public String getMessage() {
            return message;
        }

        public MissionDetail getData() {
            return data;
        }
    }

    public static class MissionDetail {
        private int missionId;
        private String image;
        private int verificationNumber;
        private String missionTitle;
        private String missionDescription;
        private String url;
        private boolean isVerificatedByViewer;

        public int getMissionId() {
            return missionId;
        }

        public String getImage() {
            return image;
        }

        public int getVerificationNumber() {
            return verificationNumber;
        }

        public String getMissionTitle() {
            return missionTitle;
        }

        public String getMissionDescription() {
            return missionDescription;
        }

        public String getUrl() {
            return url;
        }

        public boolean isVerificatedByViewer() {
            return isVerificatedByViewer;
        }
    }
}
