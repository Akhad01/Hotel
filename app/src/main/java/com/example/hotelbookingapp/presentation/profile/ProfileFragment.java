package com.example.hotelbookingapp.presentation.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.hotelbookingapp.databinding.FragmentProfileBinding;
import com.example.hotelbookingapp.helper.AuthManager;
import com.example.hotelbookingapp.presentation.auth.AuthenticationActivity;
import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ProfileFragment extends Fragment {
    @Inject
    AuthManager authManager;
    @Inject
    FirebaseAuth firebaseAuth;
    private FragmentProfileBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        binding.buttonLogout.setOnClickListener(v -> {
            firebaseAuth.signOut();
            authManager.removeUser();
            requireActivity().startActivity(
                    new Intent(requireContext(), AuthenticationActivity.class)
            );
            requireActivity().finish();
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
