package com.example.gabit;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MissionService {

    private ApiService apiService;

    public MissionService(ApiService apiService) {
        this.apiService = apiService;
    }

    public MissionService() {
        this.apiService = ApiClient.getApiService();
    }

    public void getMissions(String userId, final MissionCallback callback) {
        Call<MissionListFragment.MissionResponse> call = apiService.getMissions(userId);
        call.enqueue(new Callback<MissionListFragment.MissionResponse>() {
            @Override
            public void onResponse(@NonNull Call<MissionListFragment.MissionResponse> call, Response<MissionListFragment.MissionResponse> response) {
                Log.e("MissionService", "Response : " + response);
                Log.e("MissionService", "Response is successful? : " + response.isSuccessful());
                Log.e("MissionService", "Response body : " + response.body());
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("MissionService", "Response : " + response.body().toString());
                    callback.onSuccess(response.body().data);
                } else {
                    Log.e("MissionService", "Response unsuccesful or empty: " + response.toString());
                    callback.onError("Response unsuccessful or empty");
                }
            }

            @Override
            public void onFailure(Call<MissionListFragment.MissionResponse> call, Throwable t) {
                Log.e("MissonService", "API call failed: "+t.getMessage());
                callback.onError("API call failed: " + t.getMessage());
            }
        });
    }

    public void getOneMission(String ownerId, int missionId, String viewerId, MissionDetailCallback callback) {
        apiService.getOneMission(ownerId, missionId, viewerId).enqueue(new Callback<OneMissionActivity.MissionDetailResponse>() {
            @Override
            public void onResponse(@NonNull Call<OneMissionActivity.MissionDetailResponse> call, @NonNull Response<OneMissionActivity.MissionDetailResponse> response) {
                Log.e("OneMissionService", "Response : " + response);
                Log.e("OneMissionService", "Response is successful? : " + response.isSuccessful());
                Log.e("OneMissionService", "Response body : " + response.body());
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess((OneMissionActivity.MissionDetail) response.body().getData());
                } else {
                    callback.onError("Failed to get mission details");
                }
            }

            @Override
            public void onFailure(@NonNull Call<OneMissionActivity.MissionDetailResponse> call, @NonNull Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    public void verificateMission(VerificationRequest request, final ApiCallback callback) {
        Call<ApiResponse> call = apiService.verificateMission(request);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess();
                } else {
                    callback.onError("Response unsuccessful");
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                callback.onError("API call failed: " + t.getMessage());
            }
        });
    }

    public void getMonthlyMissions(MonthlyMissionRequest request, final MonthlyMissionCallback callback) {
        Call<MonthlyMissionResponse> call = apiService.getMonthlyMissions(request);
        call.enqueue(new Callback<MonthlyMissionResponse>() {
            @Override
            public void onResponse(Call<MonthlyMissionResponse> call, Response<MonthlyMissionResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body().getData());
                } else {
                    callback.onError("Response unsuccessful or empty");
                }
            }

            @Override
            public void onFailure(Call<MonthlyMissionResponse> call, Throwable t) {
                callback.onError("API call failed: " + t.getMessage());
            }
        });
    }

    public void proofMission(ProofRequest request, final MissionDetailResponseCallback callback) {
        Call<OneMissionActivity.MissionDetailResponse> call = apiService.proofMission(request);
        call.enqueue(new Callback<OneMissionActivity.MissionDetailResponse>() {
            @Override
            public void onResponse(@NonNull Call<OneMissionActivity.MissionDetailResponse> call, Response<OneMissionActivity.MissionDetailResponse> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess();
                } else {
                    callback.onError("Response unsuccessful");
                }
            }

            @Override
            public void onFailure(@NonNull Call<OneMissionActivity.MissionDetailResponse> call, @NonNull Throwable t) {
                callback.onError("API call failed: " + t.getMessage());
            }
        });
    }

    public interface MissionCallback {
        void onSuccess(List<MissionListFragment.Mission> missions);
        void onError(String errorMessage);
    }

    public interface MissionDetailCallback {
        void onSuccess(OneMissionActivity.MissionDetail missionDetail);
        void onError(String errorMessage);
    }

    public interface MissionDetailResponseCallback {
        void onSuccess();
        void onError(String errorMessage);
    }

    public interface ApiCallback {
        void onSuccess();
        void onError(String errorMessage);
    }

    public interface MonthlyMissionCallback {
        void onSuccess(List<Integer> monthlyMissions);
        void onError(String errorMessage);
    }
}
