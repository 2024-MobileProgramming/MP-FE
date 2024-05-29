package com.example.gabit;

import com.kakao.sdk.auth.model.OAuthToken;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;

public class KaKaoLogin {
    private


    Function2<OAuthToken, Throwable, Unit> callback = new Function2<OAuthToken, Throwable, Unit>() {
        @Override
        public Unit invoke(OAuthToken oAuthToken, Throwable throwable) {
            return null;
        }
    };

}
