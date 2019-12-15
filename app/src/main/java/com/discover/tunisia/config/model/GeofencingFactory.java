package com.discover.tunisia.config.model;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;


import com.discover.tunisia.config.Preference;
import com.discover.tunisia.config.Utils;
import com.discover.tunisia.services.RetrofitServiceFacotry;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class GeofencingFactory {

    private final List<Geofence> mGeofenceList = new ArrayList<>();
    private final Context context;
    private PendingIntent mGeofencePendingIntent;
    private GeofencingClient mGeofencingClient;

    public GeofencingFactory(Context context) {
        this.context = context;
    }

    public void initializeGeofence() {

            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            Call<ResponseBody> call = RetrofitServiceFacotry.getServiceApiClient().getGeofence();
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(@NonNull Call<ResponseBody> call, @NonNull retrofit2.Response<ResponseBody> response) {

                    List<GeofenceElement> offreBonPlans = new ArrayList<>();
                    int code = response.code();
                    if (code == 200) {
                        try {
                            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                return;
                            }

                            assert response.body() != null;
                            JSONObject jsonObject = new JSONObject(response.body().string());
                            JSONArray jsonArray = jsonObject.optJSONArray("data");
                            if (jsonArray != null && jsonArray.length() > 0) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    GeofenceElement plan = Utils.getGsonInstance().fromJson(jsonArray.get(i).toString(), GeofenceElement.class);
                                    offreBonPlans.add(plan);
                                    try {

                                        mGeofenceList.add(new Geofence.Builder()
                                                .setRequestId(plan.getId())

                                                .setCircularRegion(
                                                        Double.valueOf(plan.getLat()),
                                                        Double.valueOf(plan.getLon()),
                                                        Integer.valueOf(plan.getRadius())
                                                )
                                                .setExpirationDuration(Geofence.NEVER_EXPIRE)
                                                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER |
                                                        Geofence.GEOFENCE_TRANSITION_EXIT)
                                                .build());

                                    } catch (NumberFormatException e) {

                                    }
                                }

                                Preference.saveGeofences(context, offreBonPlans);
                                mGeofencingClient = LocationServices.getGeofencingClient(context);
                                mGeofencingClient.addGeofences(getGeofencingRequest(), getGeofencePendingIntent())
                                        .addOnSuccessListener((AppCompatActivity) context, new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                //do nothing
                                            }
                                        }).addOnFailureListener((AppCompatActivity) context, new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                    }
                                });
                            }

                        } catch (Exception e) {

                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {


                }
            });

    }

    private GeofencingRequest getGeofencingRequest() {
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
        builder.addGeofences(mGeofenceList);
        return builder.build();
    }

    private PendingIntent getGeofencePendingIntent() {
        // Reuse the PendingIntent if we already have it.
        if (mGeofencePendingIntent != null) {
            return mGeofencePendingIntent;
        }
        Intent intent = new Intent(context, GeofenceBroadcastReceiver.class);
        mGeofencePendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.
                FLAG_UPDATE_CURRENT);
        return mGeofencePendingIntent;
    }
}
