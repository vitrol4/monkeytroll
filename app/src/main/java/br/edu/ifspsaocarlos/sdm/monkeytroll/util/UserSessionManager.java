package br.edu.ifspsaocarlos.sdm.monkeytroll.util;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

import br.edu.ifspsaocarlos.sdm.monkeytroll.LoginActivity;

/**
 * Created by victor on 22/06/15.
 */
public class UserSessionManager {

    public static final String KEY_EMAIL = "email";
    public static final String KEY_TOKEN = "token";
    public static final String KEY_PARTY_ID = "partyId";
    private static final String PREFER_NAME = MkTrollConstants.PREFER;
    private static final String IS_USER_LOGIN = "IsUserLoggedIn";

    int PRIVATE_MODE = 0;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context context;

    public UserSessionManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
        editor = pref.edit();
        editor.apply();
    }

    public void createPartyId(String partyId) {
        editor.putString(KEY_PARTY_ID, partyId);
        editor.commit();
    }

    public String getPartyId() {
        return pref.getString(KEY_PARTY_ID, null);
    }

    public void createUserLoginSession(String email, String token) {
        editor.putBoolean(IS_USER_LOGIN, true);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_TOKEN, token);
        editor.commit();
    }

    public boolean checkLogin() {
        if (!this.isUserLoggedIn()) {
            Intent i = new Intent(context, LoginActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
            return true;
        }
        return false;
    }

    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<>();
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
        user.put(KEY_TOKEN, pref.getString(KEY_TOKEN, null));
        return user;
    }

    public void logoutUser() {
        editor.clear();
        editor.commit();

        Intent i = new Intent(context, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);// Closing all the Activities
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);// Add new Flag to start new Activity
        context.startActivity(i);
    }

    // Check for login
    public boolean isUserLoggedIn() {
        return pref.getBoolean(IS_USER_LOGIN, false);
    }

}