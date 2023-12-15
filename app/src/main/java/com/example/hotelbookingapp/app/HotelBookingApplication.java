package com.example.hotelbookingapp.app;

import android.app.Application;

import com.google.firebase.FirebaseApp;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class HotelBookingApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        // Initialize FirebaseApp
        FirebaseApp.initializeApp(this);
    }

}
