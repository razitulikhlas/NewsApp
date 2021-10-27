package com.example.newsapp.login.apilogin;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Calendar;
import java.util.Date;

public class SessionManager {
    public static final String SESSION_PREFERENCE = "com.example.newsapp.SessionManager.SESSION_PREFERENCE";
    public static final String SESSION_TOKEN = "com.example.newsapp.SessionManager.SESSION_TOKEN";
    public static final String SESSION_EXPIRY_TIME = "com.example.newsapp.SessionManager.SESSION_EXPIRY_TIME";
    public static final String SESSION_USER = "com.example.newsapp.SessionManager.SESSION_USER";
    public static final String SESSION_DATA = "com.example.newsapp.SessionManager.SESSION_DATA";
    public static final String TOKEN_USER = "com.example.newsapp.SessionManager.TOKEN";

    private static SessionManager INSTANCE;
    public static SessionManager getInstance(){
        if (INSTANCE == null){
            INSTANCE = new SessionManager();
        }
        return INSTANCE;
    }

    public void startUserSession(Context context, int expiredIn){
        Calendar calendar = Calendar.getInstance();
        Date userLoggedTime = calendar.getTime();
        calendar.setTime(userLoggedTime);
        calendar.add(Calendar.SECOND, expiredIn);
        Date expiryTime = calendar.getTime();
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(SESSION_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(SESSION_EXPIRY_TIME, expiryTime.getTime());
        editor.apply();
    }

    public boolean isSessionActive(Context context, Date currentTime){
        Date sessionExpiresAt = new Date(getExpiryDateFromPreference(context));
        return !currentTime.after(sessionExpiresAt);
    }

    private long getExpiryDateFromPreference(Context context){
        return context.getSharedPreferences(SESSION_PREFERENCE, Context.MODE_PRIVATE)
                .getLong(SESSION_EXPIRY_TIME, 0);
    }

    public String getUser(Context context){
        return context.getSharedPreferences(SESSION_PREFERENCE, Context.MODE_PRIVATE)
                .getString(SESSION_USER, "");
    }


    public void setUser(Context context, String user){
        SharedPreferences.Editor editor =
                context.getSharedPreferences(SESSION_PREFERENCE, Context.MODE_PRIVATE).edit();
        editor.putString(SESSION_USER, user);
        editor.apply();
    }


    public boolean getflagdata(Context context){
        return context.getSharedPreferences(SESSION_PREFERENCE, Context.MODE_PRIVATE)
                .getBoolean(SESSION_DATA, false);
    }

    public void setflagdata(Context context, boolean flagdata){
        SharedPreferences.Editor editor =
                context.getSharedPreferences(SESSION_PREFERENCE, Context.MODE_PRIVATE).edit();
        editor.putBoolean(SESSION_DATA, flagdata);
        editor.apply();
    }


    public void storeUserToken(Context context, String token){
        SharedPreferences.Editor editor =
                context.getSharedPreferences(SESSION_PREFERENCE, Context.MODE_PRIVATE).edit();
        editor.putString(SESSION_TOKEN, token);
        editor.apply();
    }

    public String getUserToken(Context context){
        return context.getSharedPreferences(SESSION_PREFERENCE, Context.MODE_PRIVATE)
                .getString(SESSION_TOKEN, "");
    }

    public void endUserSession(Context context){
        clearStoredData(context);
    }

    private void clearStoredData(Context context){
        SharedPreferences.Editor editor =
                context.getSharedPreferences(SESSION_PREFERENCE, Context.MODE_PRIVATE).edit();
        editor.clear();
        editor.apply();
    }
}