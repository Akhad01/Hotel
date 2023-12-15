package com.example.hotelbookingapp.presentation.auth;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.hotelbookingapp.MainActivity;
import com.example.hotelbookingapp.R;
import com.example.hotelbookingapp.databinding.FragmentLoginBinding;
import com.example.hotelbookingapp.helper.AuthManager;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LoginFragment extends Fragment {
    @Inject
    FirebaseAuth firebaseAuth;
    @Inject
    AuthManager authManager;
    private FragmentLoginBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        binding.textViewSignup.setOnClickListener(v ->
                NavHostFragment.findNavController(this).navigate(R.id.nav_to_signup));

        binding.buttonLogin.setOnClickListener(v -> login());
    }

    private void login() {
        String email = binding.editTextLoginEmail.getText().toString();
        String password = binding.editTextLoginPassword.getText().toString();

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        authManager.saveUser(email);
                        Toast.makeText(requireContext(), "Logged in", Toast.LENGTH_LONG).show();
                        requireActivity().startActivity(new Intent(requireContext(), MainActivity.class));
                        requireActivity().finish();
                    } else {
                        Toast.makeText(requireContext(), Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
