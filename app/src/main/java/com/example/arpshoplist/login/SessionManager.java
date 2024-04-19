package com.example.arpshoplist.login;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class SessionManager {

    private static final String PREF_NAME = "UserSession";
    private static final String IS_LOGIN = "IsLoggedIn";
    private static final String KEY_USERNAME = "Username";
    private static final String KEY_USERID = "UserId";

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public void createLoginSession(String username, int userId) {
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_USERNAME, username);
        editor.putInt(KEY_USERID, userId);
        editor.commit();
        Log.d("SessionManager", "Login session created: Username=" + username + ", UserID=" + userId);
    }

    public void logoutUser() {
        editor.clear();
        editor.commit();
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }

    public int getUserId() {
        return pref.getInt(KEY_USERID, -1);
    }

    public String getUsername() {
        return pref.getString(KEY_USERNAME, null);
    }
}
