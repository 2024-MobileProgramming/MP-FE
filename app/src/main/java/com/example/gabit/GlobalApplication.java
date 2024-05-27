package com.example.gabit;

import android.app.Application;
import com.kakao.sdk.common.KakaoSdk;


public class GlobalApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // 카카오 SDK 초기화
        String kakaoAppKey = getString(Integer.parseInt(BuildConfig.KAKAO_API_KEY));
        KakaoSdk.init(this, kakaoAppKey);
        }
}
