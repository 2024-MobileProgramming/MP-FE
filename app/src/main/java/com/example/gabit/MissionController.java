package com.example.gabit;

import android.util.Log;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MissionController {
    private MissionService missionService;

    public MissionController(MissionService missionService) {
        this.missionService = missionService;
    }

    public void fetchMissions(String userId, final MissionService.MissionCallback callback) {
        missionService.getMissions(userId, new MissionService.MissionCallback() {
            @Override
            public void onSuccess(List<MissionListFragment.Mission> missions) {
                callback.onSuccess(missions);
            }

            @Override
            public void onError(String errorMessage) {
                callback.onError(errorMessage);
                Log.e("MissionController", errorMessage);
            }
        });
    }
}
