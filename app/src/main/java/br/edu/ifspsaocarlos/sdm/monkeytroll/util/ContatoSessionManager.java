package br.edu.ifspsaocarlos.sdm.monkeytroll.util;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

import br.edu.ifspsaocarlos.sdm.monkeytroll.LoginActivity;

/**
 * Created by victor on 22/06/15.
 */
public class ContatoSessionManager {

    public static final String KEY_ID = "id";
    public static final String KEY_APELIDO = "apelido";
    public static final String KEY_NOME_COMPLETO = "nome_completo";
    private static final String PREFER_NAME = MkTrollConstants.PREFER;
    private static final String IS_USER_LOGIN = "IsUserLoggedIn";

    int PRIVATE_MODE = 0;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context context;

    public ContatoSessionManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
        editor = pref.edit();
        editor.apply();
    }

    public void createUserLoginSession(String id, String apelido, String nomeCompleto) {
        editor.putBoolean(IS_USER_LOGIN, true);
        editor.putString(KEY_ID, id);
        editor.putString(KEY_APELIDO, apelido);
        editor.putString(KEY_NOME_COMPLETO, nomeCompleto);
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
        user.put(KEY_ID, pref.getString(KEY_ID, null));
        user.put(KEY_APELIDO, pref.getString(KEY_APELIDO, null));
        user.put(KEY_NOME_COMPLETO, pref.getString(KEY_NOME_COMPLETO, null));
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