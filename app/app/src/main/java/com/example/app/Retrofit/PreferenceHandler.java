package com.example.app.Retrofit;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceHandler {
    private static final String PREF_EMAIL = "current_email";
    private final SharedPreferences pref;
    private final SharedPreferences.Editor editor;

    public PreferenceHandler(Context context) {
        pref = context.getSharedPreferences("AppPref", Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public String getEmail() {
        return pref.getString(PREF_EMAIL, "none");
    }

    public void setEmail(String email) {
        editor.putString(PREF_EMAIL, email);
        editor.apply();
    }
}
