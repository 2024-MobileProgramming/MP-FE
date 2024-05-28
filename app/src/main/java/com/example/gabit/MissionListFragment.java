package com.example.gabit;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class MissionListFragment extends Fragment {
    private LinearLayout missionListLayout;
    private TextView tvUserName;
    private List<Mission> missions;
    private String userId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_missionlist, container, false);
        missionListLayout = view.findViewById(R.id.MissionList);
        tvUserName = view.findViewById(R.id.tvUserName);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        userId = sharedPreferences.getString("userId", "");
        String userName = sharedPreferences.getString("userName", "");

        userName="@혜원";
        tvUserName.setText(userName);

        missions = fetchMissionFromServer(userId);
        populateGridWithMissions();

        return view;
    }

    private List<Mission> fetchMissionFromServer(String userId) {
        List<Mission> missionList = new ArrayList<>();
        // 미션 리스트 추가 예시
        missionList.add(new Mission(1, "Mission 1", "Description 1 Description 1 Description 1 Description 1 Description 1 Description 1 Description 1 ", true, 3));
        missionList.add(new Mission(2, "Mission 2", "Description 2", true, 1));
        missionList.add(new Mission(3, "Mission 3", "Description 3", false, 0));
        missionList.add(new Mission(4, "물 1L 마시기", "물 마시는 게 좋아요 아랐죠? 물 마시는 게 좋아요 아랐죠? 물 마시는 게 좋아요 아랐죠? 물 마시는 게 좋아요 아랐죠? 물 마시는 게 좋아요 아랐죠? 물 마시는 게 좋아요 아랐죠? 물 마시는 게 좋아요 아랐죠? 물 마시는 게 좋아요 아랐죠? 물 마시는 게 좋아요 아랐죠? 물 마시는 게 좋아요 아랐죠? 물 마시는 게 좋아요 아랐죠? 물 마시는 게 좋아요 아랐죠? 물 마시는 게 좋아요 아랐죠? 물 마시는 게 좋아요 아랐죠? 물 마시는 게 좋아요 아랐죠? 물 마시는 게 좋아요 아랐죠? 물 마시는 게 좋아요 아랐죠? 물 마시는 게 좋아요 아랐죠? 물 마시는 게 좋아요 아랐죠? 물 마시는 게 좋아요 아랐죠? 물 마시는 게 좋아요 아랐죠? 물 마시는 게 좋아요 아랐죠? 물 마시는 게 좋아요 아랐죠? 물 마시는 게 좋아요 아랐죠? 물 마시는 게 좋아요 아랐죠? 물 마시는 게 좋아요 아랐죠? 물 마시는 게 좋아요 아랐죠? 물 마시는 게 좋아요 아랐죠? 물 마시는 게 좋아요 아랐죠? 물 마시는 게 좋아요 아랐죠? 물 마시는 게 좋아요 아랐죠? 물 마시는 게 좋아요 아랐죠? ", true, 5));
        missionList.add(new Mission(5, "Mission 5", "워떤디요 ㅋㅋ 개쩔죠? 워떤디요 ㅋㅋ 개쩔죠? 워떤디요 ㅋㅋ 개쩔죠? 워떤디요 ㅋㅋ 개쩔죠? 워떤디요 ㅋㅋ 개쩔죠? 워떤디요 ㅋㅋ 개쩔죠? 워떤디요 ㅋㅋ 개쩔죠? 워떤디요 ㅋㅋ 개쩔죠? 워떤디요 ㅋㅋ 개쩔죠? 워떤디요 ㅋㅋ 개쩔죠? 워떤디요 ㅋㅋ 개쩔죠? 워떤디요 ㅋㅋ 개쩔죠? 워떤디요 ㅋㅋ 개쩔죠? 워떤디요 ㅋㅋ 개쩔죠? 워떤디요 ㅋㅋ 개쩔죠? 워떤디요 ㅋㅋ 개쩔죠? 워떤디요 ㅋㅋ 개쩔죠? 워떤디요 ㅋㅋ 개쩔죠? 워떤디요 ㅋㅋ 개쩔죠? 워떤디요 ㅋㅋ 개쩔죠? 워떤디요 ㅋㅋ 개쩔죠? 워떤디요 ㅋㅋ 개쩔죠? 워떤디요 ㅋㅋ 개쩔죠? 워떤디요 ㅋㅋ 개쩔죠? 워떤디요 ㅋㅋ 개쩔죠? 워떤디요 ㅋㅋ 개쩔죠? 워떤디요 ㅋㅋ 개쩔죠? 워떤디요 ㅋㅋ 개쩔죠? 워떤디요 ㅋㅋ 개쩔죠? 워떤디요 ㅋㅋ 개쩔죠? 워떤디요 ㅋㅋ 개쩔죠? 워떤디요 ㅋㅋ 개쩔죠? 워떤디요 ㅋㅋ 개쩔죠? 워떤디요 ㅋㅋ 개쩔죠? 워떤디요 ㅋㅋ 개쩔죠? 워떤디요 ㅋㅋ 개쩔죠? 워떤디요 ㅋㅋ 개쩔죠?", false, 2));
        return missionList;
    }

    private void populateGridWithMissions() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        for (Mission mission : missions) {
            View missionView = inflater.inflate(R.layout.mission_item, missionListLayout, false);

            TextView tvTitle = missionView.findViewById(R.id.MissionItemTitle);
            TextView tvDescription = missionView.findViewById(R.id.MissionItemDescription);
            ImageView ivStatus = missionView.findViewById(R.id.Mission_Status);

            tvTitle.setText(mission.getMissionTitle());
            tvDescription.setText(mission.getMissionShortDescription());

            if (mission.getImageProofed()) {
                ivStatus.setImageResource(mission.getVerificatedCount() > 2 ? R.drawable.star_fill : R.drawable.star_empty);
            } else {
                ivStatus.setVisibility(View.GONE);
            }

            missionListLayout.addView(missionView);
        }
    }
}
