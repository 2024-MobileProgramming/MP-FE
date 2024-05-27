package com.example.gabit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.gabit.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
//        if (savedInstanceState == null) {
//            binding.bottomNavigationView.selectedItemdId = R.id.fragment_mission
//        }
    }

    
}