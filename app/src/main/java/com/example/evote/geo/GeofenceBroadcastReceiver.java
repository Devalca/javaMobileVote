package com.example.evote.geo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.evote.MapsActivity;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

import java.util.List;

public class GeofenceBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = "GeofenceBroadcastReceiv";

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        NotificationHelper notificationHelper = new NotificationHelper(context);

        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);

        if (geofencingEvent.hasError()) {
            Toast.makeText(context, "Terjadi kesalahan dengan koneksi atau lokasi service pada hp anda", Toast.LENGTH_SHORT).show();
            return;
        }

        List<Geofence> geofenceList = geofencingEvent.getTriggeringGeofences();
        for (Geofence geofence: geofenceList) {
            Log.d(TAG, "onReceive: " + geofence.getRequestId());
        }
        int transitionType = geofencingEvent.getGeofenceTransition();

        switch (transitionType) {
            case Geofence.GEOFENCE_TRANSITION_ENTER:
                System.out.println("masuk");
                break;
            case Geofence.GEOFENCE_TRANSITION_DWELL:
                System.out.println("diam");
                break;
            case Geofence.GEOFENCE_TRANSITION_EXIT:
                Toast.makeText(context, "Terima Kasih Telah Memberikan Suara Anda", Toast.LENGTH_SHORT).show();
                notificationHelper.sendHighPriorityNotification("Terima Kasih Telah Memberikan Suara Anda", "", MapsActivity.class);
                break;
        }

    }
}