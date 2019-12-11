package com.discover.tunisia.config;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.discover.tunisia.login.entities.User;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;

public class Preference {
    public static User getCurrentCompte(Context context) {
        try {
            if (context != null) {
                SharedPreferences preferences = PreferenceManager
                        .getDefaultSharedPreferences(context);
                return  Utils.getGsonInstance().fromJson(preferences.getString(Constante.USER, null), User.class);

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static void saveInPreferences(Context context, User user) {
        try {
            if (context != null) {

                SharedPreferences preferences = PreferenceManager
                        .getDefaultSharedPreferences(context);
                SharedPreferences.Editor editor = preferences.edit();
                try {
                    editor.remove(Constante.USER);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    editor.putString(Constante.USER, Utils.getGsonInstance().toJson(user));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                editor.apply();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());        }
    }

    public static void logout(Context context) {

        try {
            if (FacebookSdk.isInitialized()) {
                LoginManager.getInstance().logOut();
            }
            SharedPreferences preferences = PreferenceManager
                    .getDefaultSharedPreferences(context);
            preferences.edit().remove(Constante.USER).apply();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


}
