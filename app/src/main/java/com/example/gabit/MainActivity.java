package com.example.gabit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.gabit.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationBarView;
import com.kakao.sdk.common.util.Utility;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private static final String PREF_NAME = "UserPrefs";
    private static final String KEY_USER_ID = "userId";
    private static final String KEY_USER_NICKNAME = "userName";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String keyHash = Utility.INSTANCE.getKeyHash(this);
        Log.e("Key", "keyHash: " + keyHash);

        //포스트맨 테스트 할 때 필요한 sharedPreference 설정
        SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USER_ID, "3");
        editor.putString(KEY_USER_NICKNAME, "조혜원");
        editor.apply();
        binding.bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;

                if (item.getItemId() == R.id.fragment_mission) {
                    selectedFragment = new MissionListFragment();
                } else if (item.getItemId() == R.id.fragment_calendar) {
                    selectedFragment = new Mission_Calendar();
                } else if (item.getItemId() == R.id.fragment_friends) {
                    selectedFragment = new MemberFragment();
                } else if (item.getItemId() == R.id.fragment_profile) {
                    selectedFragment = new ProfileFragment();
                }

                if (selectedFragment != null) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.main_container, selectedFragment)
                            .commit();
                }
                return true;
            }
        });

        if (savedInstanceState == null) {
            binding.bottomNavigationView.setSelectedItemId(R.id.fragment_mission);
        }
    }




}