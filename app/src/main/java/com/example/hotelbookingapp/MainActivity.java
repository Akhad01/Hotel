package com.example.hotelbookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.hotelbookingapp.databinding.ActivityMainBinding;
import com.example.hotelbookingapp.helper.AuthManager;
import com.example.hotelbookingapp.presentation.auth.AuthenticationActivity;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {
    @Inject
    FirebaseAuth firebaseAuth;
    @Inject
    AuthManager authManager;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (FirebaseApp.getApps(this).isEmpty()) {
            FirebaseApp.initializeApp(this);
        }
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).hide();

        if (!authManager.isLoggedIn()) {
            startActivity(new Intent(this, AuthenticationActivity.class));
            finish();
        }

        setBottomNavBar();
    }

    private void setBottomNavBar() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top-level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navHome, R.id.navHistory, R.id.navFavorite, R.id.navProfile
        ).build();

        Set<Integer> topLevelDestinations = new HashSet<>();
        topLevelDestinations.add(R.id.navHome);
        topLevelDestinations.add(R.id.navHistory);
        topLevelDestinations.add(R.id.navFavorite);
        topLevelDestinations.add(R.id.navProfile);

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                if (topLevelDestinations.contains(destination.getId())) {
                    binding.navView.setVisibility(View.VISIBLE);
                } else {
                    binding.navView.setVisibility(View.GONE);
                }
            }
        });


        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
