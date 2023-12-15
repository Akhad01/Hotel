package com.example.hotelbookingapp.presentation.auth;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hotelbookingapp.databinding.ActivityAuthenticationBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AuthenticationActivity extends AppCompatActivity {
    private ActivityAuthenticationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAuthenticationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
