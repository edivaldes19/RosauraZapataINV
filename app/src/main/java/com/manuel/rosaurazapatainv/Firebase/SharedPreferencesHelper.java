package com.manuel.rosaurazapatainv.Firebase;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesHelper {
    private Context context;
    private SharedPreferences sharePreferences;

    public SharedPreferencesHelper(Context context, String file) {
        this.context = context;
        this.sharePreferences = context.getSharedPreferences(file, Context.MODE_PRIVATE);
    }

    public void write(String key, String valor) {
        SharedPreferences.Editor editor = sharePreferences.edit();
        editor.putString(key, valor);
        editor.apply();
    }

    public String read(String key) {
        return sharePreferences.getString(key, null);
    }

    public void Clear() {
        SharedPreferences.Editor editor = sharePreferences.edit();
        editor.clear();
        editor.apply();
    }
}