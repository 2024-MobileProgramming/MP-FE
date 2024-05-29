package com.example.gabit;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class MissionListFragment extends Fragment {
    private LinearLayout missionListLayout;
    private TextView tvUserName;
    private String userId;
    private MissionController missionController;

    public void setMissionController(MissionController missionController) {
        this.missionController = missionController;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_missionlist, container, false);
        missionListLayout = view.findViewById(R.id.MissionList);
        tvUserName = view.findViewById(R.id.tvUserName);

        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        userId = sharedPreferences.getString("userId", "");
        String userName = sharedPreferences.getString("userName", "");

        tvUserName.setText(userName);

        if (missionController == null) {
            missionController = new MissionController(new MissionService());
        }
        fetchMissions("1");

        return view;
    }

    void fetchMissions(String userId) {
        missionController.fetchMissions(userId, new MissionService.MissionCallback() {
            @Override
            public void onSuccess(List<Mission> missions) {
                populateGridWithMissions(missions);
            }

            @Override
            public void onError(String errorMessage) {
                Log.e("MissionListFragment", errorMessage);
            }
        });
    }

    private void populateGridWithMissions(List<Mission> missions) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        missionListLayout.removeAllViews();

        for (Mission mission : missions) {
            View missionView = inflater.inflate(R.layout.mission_item, missionListLayout, false);

            TextView tvTitle = missionView.findViewById(R.id.MissionItemTitle);
            TextView tvDescription = missionView.findViewById(R.id.MissionItemDescription);
            ImageView ivStatus = missionView.findViewById(R.id.Mission_Status);

            tvTitle.setText(mission.getMissionTitle());
            tvDescription.setText(mission.getMissionShortDescription());

            Log.d("MissonListFragment", "Mission Title: " + mission.getMissionTitle());
            Log.d("MissionListFragment", "Mission Description: " + mission.getMissionShortDescription());

            if (mission.isImageProofed()) {
                ivStatus.setImageResource(mission.getVerificatedCount() > 2 ? R.drawable.star_fill : R.drawable.star_empty);
            } else {
                ivStatus.setVisibility(View.GONE);
            }

            missionListLayout.addView(missionView);
        }
    }


    public static class MissionResponse {
        @SerializedName("status")
        public int status;

        @SerializedName("message")
        public String message;

        @SerializedName("data")
        public List<Mission> data;

        @NonNull
        @Override
        public String toString() {
            return "MissionResponse{" +
                    "status=" + status +
                    ", message='" + message + '\'' +
                    ", data=" + data +
                    '}';
        }
    }

    public static class Mission{
        @SerializedName("missionId")
        private int missionId;

        @SerializedName("nickname")
        private String nickname;

        @SerializedName("missionTitle")
        private String missionTitle;

        @SerializedName("missionShortDescription")
        private String missionShortDescription;

        @SerializedName("imageProofed")
        private boolean imageProofed;

        @SerializedName("verificatedCount")
        private int verificatedCount;

        @NonNull
        @Override
        public String toString() {
            return "Mission{" +
                    "missionId=" + missionId +
                    ", nickname='" + nickname + '\'' +
                    ", missionTitle='" + missionTitle + '\'' +
                    ", missionShortDescription='" + missionShortDescription + '\'' +
                    ", imageProofed=" + imageProofed +
                    ", verificatedCount=" + verificatedCount +
                    '}';
        }

        public int getMissionId() {
            return missionId;
        }

        public String getNickname() {
            return nickname;
        }

        public String getMissionTitle() {
            return missionTitle;
        }

        public String getMissionShortDescription() {
            return missionShortDescription;
        }

        public boolean isImageProofed() {
            return imageProofed;
        }

        public int getVerificatedCount() {
            return verificatedCount;
        }

    }
}
