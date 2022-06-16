package com.example.sales.ultils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharePref {
    private static SharePref instance = null;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private SharePref(Context context) {
        sharedPreferences = context.getSharedPreferences(AppConstant.KEY_FILE_CACHE, Context.MODE_PRIVATE);
    }

    public static SharePref getInstance(Context context) {
        if (instance == null) {
            instance = new SharePref(context);
        }
        return instance;
    }

    public void setToken(String token) {
        editor = sharedPreferences.edit();
        editor.putString(AppConstant.KEY_TOKEN, "");
        editor.commit();
    }

    public String getToken() {
        return sharedPreferences.getString(AppConstant.KEY_TOKEN, "");
    }
}
