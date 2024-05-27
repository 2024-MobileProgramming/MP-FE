package com.example.gabit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.example.gabit.databinding.ActivityMainBinding;
import com.kakao.sdk.common.KakaoSdk;
import com.kakao.sdk.common.util.Utility;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String keyHash = Utility.INSTANCE.getKeyHash(this);
        Log.e("Key", "keyHash: " + keyHash);



//        if (savedInstanceState == null) {
//            binding.bottomNavigationView.selectedItemdId = R.id.fragment_mission
//        }
    }



    
}