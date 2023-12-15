package com.example.hotelbookingapp.presentation.auth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.hotelbookingapp.R;
import com.example.hotelbookingapp.databinding.FragmentSignUpBinding;
import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SignUpFragment extends Fragment {
    @Inject
    FirebaseAuth firebaseAuth;
    private FragmentSignUpBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSignUpBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        binding.buttonSignup.setOnClickListener(v -> signup());
    }

    private void signup() {
        String email = binding.editTextSignupEmail.getText().toString();
        String password = binding.editTextSignupPassword.getText().toString();
        String rePassword = binding.editTextSignupRepPassword.getText().toString();

        if (!password.equals(rePassword)) {
            binding.editTextSignupPassword.setError("Check password");
            binding.editTextSignupRepPassword.setError("Check password");
        } else {
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(requireContext(), "Account created", Toast.LENGTH_LONG).show();
                            NavHostFragment.findNavController(this).navigate(R.id.nav_to_login);
                        } else {
                            Toast.makeText(requireContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
