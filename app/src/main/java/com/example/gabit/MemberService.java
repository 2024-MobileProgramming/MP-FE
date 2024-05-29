package com.example.gabit;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MemberService {

    private ApiService apiService;
    public MemberService(ApiService apiService) {
        this.apiService = apiService;
    }

    public MemberService() {
        this.apiService = ApiClient.getApiService();
    }

    public void getMember(String userId, final MemberCallback callback) {
        Call<MemberFragment.MemberResponse> call = apiService.getMemberList(userId);
        call.enqueue(new Callback<MemberFragment.MemberResponse>() {
            @Override
            public void onResponse(@NonNull Call<MemberFragment.MemberResponse> call, @NonNull Response<MemberFragment.MemberResponse> response) {
                Log.e("MemberService", "Response : " + response);
                Log.e("MemberService", "Response is successful? : " + response.isSuccessful());
                Log.e("MemberService", "Response body : " + response.body());
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("MemberService", "Response : " + response.body().toString());
                    callback.onSuccess(response.body().data);
                } else {
                    Log.e("MemberService", "Response unsuccesful or empty: " + response.toString());
                    callback.onError("Response unsuccessful or empty");
                }
            }

            @Override
            public void onFailure(Call<MemberFragment.MemberResponse> call, Throwable t) {
                Log.e("MemeberService", "API call failed: " + t.getMessage());
                callback.onError("API call failed: " + t.getMessage());
            }
        });
    }

    public interface MemberCallback {
        void onSuccess(List<MemberFragment.Member> members);
        void onError(String errorMessage);
    }

}
