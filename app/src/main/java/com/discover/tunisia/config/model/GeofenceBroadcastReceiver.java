package com.discover.tunisia.config.model;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;


import com.discover.tunisia.R;
import com.discover.tunisia.config.Preference;
import com.discover.tunisia.discover.MainActivity;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;


import java.util.List;
import java.util.Random;

public class GeofenceBroadcastReceiver extends BroadcastReceiver {

    private static final String CHANNEL_ID = "channel_01";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("herre", "herre");
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        if (geofencingEvent.hasError()) {
            String errorMessage = GeofenceErrorMessages.getErrorString(context,
                    geofencingEvent.getErrorCode());
            Log.e("error", errorMessage);
            return;
        }

        int geofenceTransition = geofencingEvent.getGeofenceTransition();
        if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER ||
                geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT) {
            List<Geofence> triggeringGeofences = geofencingEvent.getTriggeringGeofences();
            GeofenceElement plan = getFirstPlan(context, triggeringGeofences);
            if (plan != null)
                sendNotification(plan, context);

        } else {
            // Log the error.
            Log.e("error", "error geofence");
        }
    }

    private GeofenceElement getFirstPlan(Context context, List<Geofence> triggeringGeofences) {

        if (triggeringGeofences != null && !triggeringGeofences.isEmpty()) {
            Geofence firstGeofence;
            try {
                Random randomGenerator = new Random();
                int index = randomGenerator.nextInt(triggeringGeofences.size());
                firstGeofence = triggeringGeofences.get(index);
            } catch (Exception e) {
                firstGeofence = triggeringGeofences.get(0);
            }
            return Preference.getGeofenceById(context, firstGeofence.getRequestId());
        } else return null;
    }



    /**
     * Posts a notification in the notification bar when a transition is detected.
     * If the user clicks the notification, control goes to the MainActivity.
     */
    private void sendNotification(GeofenceElement plan, Context context) {


        // Get an instance of the Notification manager
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Android O requires a Notification Channel.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = context.getString(R.string.app_name);
            // Create the channel for the notification
            NotificationChannel mChannel =
                    new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT);

            // Set the Notification Channel for the Notification Manager.
            assert mNotificationManager != null;
            mNotificationManager.createNotificationChannel(mChannel);
        }

        // Create an explicit content Intent that starts the main Activity.
        Intent notificationIntent = new Intent(context, MainActivity.class);
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // Construct a task stack.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);

        // Add the main Activity to the task stack as the parent.
        stackBuilder.addParentStack(MainActivity.class);

        // Push the content Intent onto the stack.
        stackBuilder.addNextIntent(notificationIntent);

        // Get a PendingIntent containing the entire back stack.
        PendingIntent notificationPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        // Get a notification builder that's compatible with platform versions >= 4
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID);

        // Define the notification settings.
        try {
            builder.setSmallIcon(R.drawable.ic_launcher_background)
                    .setContentTitle(plan.getTitle())
                    .setContentText(plan.getDescription())
                    .setContentIntent(notificationPendingIntent);
        } catch (Exception e) {

        }

        // Set the Channel ID for Android O.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId(CHANNEL_ID); // Channel ID
        }

        // Dismiss notification once the user touches it.
        builder.setAutoCancel(true);

        // Issue the notification
        assert mNotificationManager != null;
        mNotificationManager.notify(0, builder.build());

    }


}