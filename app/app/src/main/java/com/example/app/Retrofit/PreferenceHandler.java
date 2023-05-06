package com.example.app.Retrofit;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceHandler {
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private static final String PREF_EMAIL = "none";
    private static final boolean PREF_LOGGED_IN = false;

    public PreferenceHandler(Context context) {
        pref = context.getSharedPreferences("AppPref", Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public void setEmail(String email) {
        editor.putString(PREF_EMAIL, email);
        editor.apply();
    }

    public void setLoggedIn(boolean loggedIn) {
        editor.putBoolean("loggedIn", loggedIn);
        editor.apply();
    }
    public boolean getLoggedIn() {
        return pref.getBoolean("loggedIn", false);
    }

    public String getEmail() {
        return pref.getString(PREF_EMAIL, "");
    }
}
