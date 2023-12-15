package com.example.hotelbookingapp.helper;

import android.content.Context;
import android.content.SharedPreferences;

public class AuthManager {

    private final Context context;

    public AuthManager(Context context) {
        this.context = context;
    }

    public boolean isLoggedIn() {
        String authToken = getUser();
        return !authToken.isEmpty();
    }

    private String getUser() {
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        sharedPreferences.getString(Constants.USER_KEY, "");
        return sharedPreferences.getString(Constants.USER_KEY, "");
    }

    public void saveUser(String email) {
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(Constants.USER_KEY, email).apply();
    }

    public void removeUser() {
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().remove(Constants.USER_KEY).apply();
    }
}
