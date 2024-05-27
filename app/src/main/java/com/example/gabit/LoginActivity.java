package com.example.gabit;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.common.KakaoSdk;
import com.kakao.sdk.user.UserApiClient;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;

public class LoginActivity extends AppCompatActivity {

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

    private void navigateToMainActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}

