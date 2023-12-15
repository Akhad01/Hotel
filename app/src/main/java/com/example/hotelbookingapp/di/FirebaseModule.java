package com.example.hotelbookingapp.di;// FirebaseModule.java

import android.content.Context;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import javax.inject.Singleton;

@Module
@InstallIn(SingletonComponent.class)
public class FirebaseModule {

    @Provides
    @Singleton
    public static FirebaseAuth provideFirebaseAuth(@ApplicationContext Context context) {
        // Ensure FirebaseApp is initialized
        if (FirebaseApp.getApps(context).isEmpty()) {
            FirebaseApp.initializeApp(context);
        }
        return FirebaseAuth.getInstance();
    }

    @Provides
    @Singleton
    public static FirebaseFirestore provideFirestore(@ApplicationContext Context context) {
        // Ensure FirebaseApp is initialized
        if (FirebaseApp.getApps(context).isEmpty()) {
            FirebaseApp.initializeApp(context);
        }
        return FirebaseFirestore.getInstance();
    }
}
