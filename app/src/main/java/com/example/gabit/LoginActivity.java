package com.example.gabit;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.common.KakaoSdk;
import com.kakao.sdk.user.UserApiClient;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final String PREF_NAME = "UserPrefs";
    private static final String KEY_USER_ID = "userId";
    private static final String KEY_USER_NICKNAME = "userName";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // 카카오 SDK 초기화
        String kakaoAppKey = BuildConfig.KAKAO_API_KEY;
        KakaoSdk.init(this, kakaoAppKey);

        ImageButton loginKakaoButton = findViewById(R.id.btn_kakaoLogin);
        loginKakaoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startKaKaoLogin();
            }
        });
    }

    private void startKaKaoLogin() {
        Function2<OAuthToken, Throwable, Unit> callback = new Function2<OAuthToken, Throwable, Unit>() {
            @Override
            public Unit invoke(OAuthToken oAuthToken, Throwable throwable) {
                if (oAuthToken != null) {
                    // 로그인 성공
                    Log.d("KakaoLogin", "Login success");
                    navigateToMainActivity();
                } else {
                    // 로그인 실패
                    Log.e("KakaoLogin", "Login failed", throwable);
                }
                return null;
            }
        };

        // 카카오톡 설치 여부에 따라 로그인 방식 결정
        if (UserApiClient.getInstance().isKakaoTalkLoginAvailable(this)) {
            UserApiClient.getInstance().loginWithKakaoTalk(this, callback);
        } else {
            UserApiClient.getInstance().loginWithKakaoAccount(this, callback);
        }
    }

    private void fetchUserInfo() {
        UserApiClient.getInstance().me((user, error) -> {
            if (error != null) {
                Log.e(TAG, "사용자 정보 요청 실패", error);
            } else if (user != null) {
                assert user.getKakaoAccount() != null;
                assert user.getKakaoAccount().getProfile() != null;
                String nickname = user.getKakaoAccount().getProfile().getNickname();
                String email = user.getKakaoAccount().getEmail();

                Log.i(TAG, "닉네임 : " + nickname);
                Log.i(TAG, "이메일: " + email);

                sendUserInfoToBackend(nickname, email);
            }
            return null;
        });
    }

    private void sendUserInfoToBackend(String nickname, String email) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.POSTMAN_SERVER_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        UserRequest userRequest = new UserRequest(nickname, email);
        Call<UserResponse> call = apiService.createUser(userRequest);

        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(@NonNull Call<UserResponse> call, @NonNull Response<UserResponse> response) {
                if (response.isSuccessful()) {
                    UserResponse userResponse = response.body();
                    if (userResponse != null) {
                        Log.i(TAG, "유저 ID: " + userResponse.getUserId());
                        saveUserIdToLocal(userResponse.getUserId(), nickname);
                    }
                } else {
                    Log.e(TAG, "서버 응답 오류: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserResponse> call, @NonNull Throwable t) {
                Log.e(TAG, "유저 정보 전송 실패", t);
            }
        });

    }

    private void saveUserIdToLocal(String userId, String nickname) {
        SharedPreferences sharedPref = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= sharedPref.edit();
        editor.putString(KEY_USER_ID, userId);
        editor.putString(KEY_USER_NICKNAME, nickname);
        editor.apply();

        Toast.makeText(this, "유저 ID 저장 완료: " + userId, Toast.LENGTH_SHORT).show();

        navigateToMainActivity();
    }

    private void navigateToMainActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public static class UserRequest {
        private String nickname;
        private String email;

        public UserRequest(String nickname, String email) {
            this.nickname = nickname;
            this.email = email;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

    }

    public static class UserResponse {
        private final String userId;

        public UserResponse(String userId) {
            this.userId = userId;
        }

        public String getUserId() {
            return userId;
        }

    }
}

