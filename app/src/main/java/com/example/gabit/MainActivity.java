package com.example.gabit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.gabit.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationBarView;
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

        binding.bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;

                if (item.getItemId() == R.id.fragment_mission) {
                    selectedFragment = new MissionListFragment();
                } else if (item.getItemId() == R.id.fragment_calendar) {
                    selectedFragment = new Mission_Calendar();
                } else if (item.getItemId() == R.id.fragment_friends) {
                    selectedFragment = new FriendsFragment();
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