package com.example.app.Retrofit;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceHandler {
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private static final String PREF_EMAIL = "email";

    public PreferenceHandler(Context context) {
        pref = context.getSharedPreferences("AppPref", Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public void setEmail(String email) {
        editor.putString(PREF_EMAIL, email);
        editor.apply();
    }

    public String getEmail() {
        return pref.getString(PREF_EMAIL, "");
    }
}