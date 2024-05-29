package com.example.gabit;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
    @GET("/mission/{userId}")
    Call<MissionListFragment.MissionResponse> getMissions(@Path("userId") String userId);

    @GET("/mission/{ownerId}/{viewerId}")
    Call<MissionListFragment.MissionResponse> getOneMissions(@Path("ownerId") String ownerId, @Path("viewerId") String viewerId);

    @POST("/mission/verificate")
    Call<ApiResponse> verificateMission(@Body VerificationRequest request);

    @GET("/user/memberList/{userId}")
    Call<MemberListResponse> getMemberList(@Path("userId") String userId);

    @GET("/mission/monthly")
    Call<MonthlyMissionResponse> getMonthlyMissions(@Body MonthlyMissionRequest request);

    @POST("/mission/proof")
    Call<ApiResponse> proofMission(@Body ProofRequest request);
}
