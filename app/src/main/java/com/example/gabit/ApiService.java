package com.example.gabit;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
    @POST("/login")
    Call<LoginActivity.UserResponse> createUser(@Body LoginActivity.UserRequest userRequest);

    @GET("/mission/{userId}")
    Call<MissionListFragment.MissionResponse> getMissions(@Path("userId") String userId);

    @GET("/mission/{ownerId}/{missionId}/{viewerId}")
    Call<OneMissionActivity.MissionDetailResponse> getOneMission(@Path("ownerId") String ownerId, @Path("missionId") int missionId, @Path("viewerId") String viewerId);

    @POST("/mission/verificate")
    Call<ApiResponse> verificateMission(@Body VerificationRequest request);

    @GET("/user/memberList/{userId}")
    Call<MemberFragment.MemberResponse> getMemberList(@Path("userId") String userId);

    @GET("/mission/monthly")
    Call<MonthlyMissionResponse> getMonthlyMissions(@Body MonthlyMissionRequest request);

    @POST("/mission/proof")
    Call<OneMissionActivity.MissionDetailResponse> proofMission(@Body ProofRequest request);
}
