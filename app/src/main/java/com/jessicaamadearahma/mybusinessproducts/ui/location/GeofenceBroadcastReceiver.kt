package com.jessicaamadearahma.mybusinessproducts.ui.location

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofenceStatusCodes
import com.google.android.gms.location.GeofencingEvent
import com.jessicaamadearahma.mybusinessproducts.R

class GeofenceBroadcastReceiver : BroadcastReceiver(){
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == ACTION_GEOFENCE_EVENT) {

            val geofenceEvent = intent.let { GeofencingEvent.fromIntent(it) }
            if (geofenceEvent != null) {
                if (geofenceEvent.hasError()) {
                    val errorMessage = GeofenceStatusCodes.getStatusCodeString(geofenceEvent.errorCode)
                    Log.e(BROADCAST, errorMessage)
                    context?.let { showNotification(it, errorMessage) }
                    return
                }
            }

            val geoTransitionType = geofenceEvent?.geofenceTransition
            if (geoTransitionType == Geofence.GEOFENCE_TRANSITION_ENTER || geoTransitionType == Geofence.GEOFENCE_TRANSITION_DWELL) {
                val geofenceTransitionString =
                    when (geoTransitionType) {
                        Geofence.GEOFENCE_TRANSITION_ENTER -> context?.getString(R.string.transition_enter_notif)
                        Geofence.GEOFENCE_TRANSITION_DWELL -> context?.getString(R.string.transition_dwell_notif)
                        else -> context?.getString(R.string.area_invalid)
                    }

                Log.i(BROADCAST, "$geofenceTransitionString")
                if (context != null) { showNotification(context, "$geofenceTransitionString") }
            } else {
                val errorMessage = context?.getString(R.string.invalid_transition_type)+" $geoTransitionType"
                Log.e(BROADCAST, errorMessage)
                context?.let { showNotification(it, errorMessage) }
            }
        }
    }
    private fun showNotification(context: Context, geofenceTransitionTitle: String) {
        val mNotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val bigPictureStyle = NotificationCompat.BigPictureStyle()
            .bigPicture(BitmapFactory.decodeResource(context.resources, R.drawable.picture_notification))
            .setBigContentTitle(geofenceTransitionTitle)

        val mBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setStyle(bigPictureStyle)
            .setContentText(context.getString(R.string.content_notif))
            .setSmallIcon(R.drawable.ic_notification)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
            mBuilder.setChannelId(CHANNEL_ID)
            mNotificationManager.createNotificationChannel(channel)
        }

        val notification = mBuilder.build()
        mNotificationManager.notify(NOTIFICATION_ID, notification)
    }

    companion object {
        private const val BROADCAST = "broadcast"
        const val ACTION_GEOFENCE_EVENT = "geofence_event"
        private const val CHANNEL_ID = "1"
        private const val CHANNEL_NAME = "geofence_channel"
        private const val NOTIFICATION_ID = 1
    }

}