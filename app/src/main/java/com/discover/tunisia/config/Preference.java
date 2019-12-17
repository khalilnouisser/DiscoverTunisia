package com.discover.tunisia.config;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.discover.tunisia.config.model.GeofenceElement;
import com.discover.tunisia.login.entities.User;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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


    public static User getCurrentLangue(Context context) {
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
            System.out.println(e.getMessage());
        }
    }

    public static void saveViewTuto(Context context,boolean hasView)
    {
        try {
            if (context != null) {

                SharedPreferences preferences = PreferenceManager
                        .getDefaultSharedPreferences(context);
                SharedPreferences.Editor editor = preferences.edit();
                try {
                    editor.putBoolean("tuto", hasView);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                editor.apply();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static boolean hasViewTuto(Context context) {
        try {
            if (context != null) {
                SharedPreferences preferences = PreferenceManager
                        .getDefaultSharedPreferences(context);
                return  preferences.getBoolean("tuto", false);

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
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

    public static void saveGeofences(Context context, List<GeofenceElement> geofenceElements) {
        try {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            SharedPreferences preferences = PreferenceManager
                    .getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = preferences.edit();
            Gson gson = Utils.getGsonInstance();
            editor.remove("plan_geofences");
            editor.remove("geofence_create_date");
            editor.putString("plan_geofences", gson.toJson(geofenceElements));
            editor.putString("geofence_create_date", sdf.format(new Date()));
            editor.apply();
        } catch (Exception ignored) {

        }
    }

    public static GeofenceElement getGeofenceById(Context context, String requestId) {
        try {
            List<GeofenceElement> businessList = getGeofences(context);
            assert businessList != null;
            for (GeofenceElement plan : businessList) {
                if (plan.getId().equals(requestId))
                    return plan;
            }
        } catch (Exception ignored) {

        }
        return null;
    }

    public static ArrayList<GeofenceElement> getGeofences(Context context) {
        try {
            SharedPreferences preferences = PreferenceManager
                    .getDefaultSharedPreferences(context);
            Gson gson = Utils.getGsonInstance();
            Type type = new TypeToken<List<GeofenceElement>>() {
                //do nothing
            }.getType();
            String jsonList = preferences.getString("plan_geofences", null);
            if (jsonList != null && !jsonList.equals(""))
                if (jsonList.substring(0, 1).equals("\""))
                    jsonList = jsonList.substring(1, jsonList.length() - 1);
            List<GeofenceElement> list;
            try {
                list = gson.fromJson(preferences.getString("plan_geofences", null), type);
            } catch (Exception e) {

                return null;
            }
            return (ArrayList<GeofenceElement>) list;
        } catch (Exception e) {

            return null;
        }

    }


}
