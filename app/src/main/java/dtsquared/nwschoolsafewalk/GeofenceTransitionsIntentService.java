package dtsquared.nwschoolsafewalk;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofenceStatusCodes;
import com.google.android.gms.location.GeofencingEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel on 2017-03-11.
 */

public class GeofenceTransitionsIntentService extends IntentService {
    public static final int GEOFENCE_NOTIFICATION_ID = 0;

    /**
     * Creates an IntentService. Invoked by your subclass's constructor.
     *
     */
    public GeofenceTransitionsIntentService() {
        super(GeofenceTransitionsIntentService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);

        if (geofencingEvent.hasError()) {
            String errorMessage = getErrorString(geofencingEvent.getErrorCode());
            Log.e("", errorMessage);
            return;
        }

        // Retrieve GeofenceTrasition
        int geoFenceTransition = geofencingEvent.getGeofenceTransition();
        // Check transition type
        if (geoFenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER) {
            List<Geofence> triggeringGeofences = geofencingEvent.getTriggeringGeofences();
            String geofenceTransitionDetails = getGeofenceTrasitionDetails(geoFenceTransition,
                    triggeringGeofences);
            sendNotification( geofenceTransitionDetails );
        }
    }

    // Create a message with Geofences received
    private String getGeofenceTrasitionDetails(int geoFenceTransition,
                                               List<Geofence> triggeringGeofences) {
        // get the ID of each geofence triggered
        ArrayList<String> triggeringGeofencesList = new ArrayList<>();
        for ( Geofence geofence : triggeringGeofences ) {
            triggeringGeofencesList.add( geofence.getRequestId() );
        }

        if ( geoFenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER ) {
            return TextUtils.join( ", ", triggeringGeofencesList);
        }

            //status = "Entering ";
        //else if ( geoFenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT )
            //status = "Exiting ";
        return TextUtils.join( ", ", triggeringGeofencesList);
    }

    // Send a notification
    private void sendNotification( String msg ) {
        Log.i("", "sendNotification: " + msg );

        // Intent to start the main Activity
        Intent notificationIntent = Maps.makeNotificationIntent(getApplicationContext(), msg);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(Maps.class);
        stackBuilder.addNextIntent(notificationIntent);
        PendingIntent notificationPendingIntent = stackBuilder.getPendingIntent(0,
                PendingIntent.FLAG_UPDATE_CURRENT);

        // Creating and sending Notification
        NotificationManager notificatioMng =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificatioMng.notify(GEOFENCE_NOTIFICATION_ID,
                createNotification(msg, notificationPendingIntent));
    }

    // Create a notification
    private Notification createNotification(String msg, PendingIntent notificationPendingIntent) {
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
        notificationBuilder
                .setSmallIcon(R.drawable.walklogo)
                //.setColor(Color.WHITE)
                .setContentTitle(msg)
                .setContentText("You have arrived!")
                .setContentIntent(notificationPendingIntent)
                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE |
                        Notification.DEFAULT_SOUND)
                .setAutoCancel(true);
        return notificationBuilder.build();
    }

    // Handle errors
    private static String getErrorString(int errorCode) {
        switch (errorCode) {
            case GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE:
                return "GeoFence not available";
            case GeofenceStatusCodes.GEOFENCE_TOO_MANY_GEOFENCES:
                return "Too many GeoFences";
            case GeofenceStatusCodes.GEOFENCE_TOO_MANY_PENDING_INTENTS:
                return "Too many pending intents";
            default:
                return "Unknown error.";
        }
    }
}
