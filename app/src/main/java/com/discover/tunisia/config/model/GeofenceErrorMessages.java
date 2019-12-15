package com.discover.tunisia.config.model;

import android.content.Context;
import android.content.res.Resources;

import com.discover.tunisia.R;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.location.GeofenceStatusCodes;


/**
 * GeofenceElement error codes mapped to error messages.
 */
class GeofenceErrorMessages {
    /**
     * Prevents instantiation.
     */
    private GeofenceErrorMessages() {
        //do nothing
    }

    /**
     * Returns the error string for a geofencing exception.
     */
    public static String getErrorString(Context context, Exception e) {
        if (e instanceof ApiException) {
            return getErrorString(context, ((ApiException) e).getStatusCode());
        } else {
            return "unknown_geofence_error";
        }
    }

    /**
     * Returns the error string for a geofencing error code.
     */
    public static String getErrorString(Context context, int errorCode) {
        Resources mResources = context.getResources();
        switch (errorCode) {
            case GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE:

            case GeofenceStatusCodes.GEOFENCE_TOO_MANY_GEOFENCES:

            case GeofenceStatusCodes.GEOFENCE_TOO_MANY_PENDING_INTENTS:

            default:
                return "unknown_geofence_error";
        }
    }
}