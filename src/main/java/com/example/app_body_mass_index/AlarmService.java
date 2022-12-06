package com.example.app_body_mass_index;

import android.annotation.TargetApi;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

@TargetApi(Build.VERSION_CODES.CUPCAKE)
public class AlarmService extends IntentService {
    private NotificationManager alarmNotificationManager;

    public AlarmService() {
        super("AlarmService");
    }

    @Override
    public void onHandleIntent(Intent intent) {


        sendNotification( intent.getExtras().getString("ch"),intent.getExtras().getInt("etat"));
    }

    private void sendNotification(String msg, int etat) {
        Log.d("AlarmService", "Preparing to send notification...: " + msg);
        alarmNotificationManager = (NotificationManager) this
                .getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent contentIntent = null;
        if (etat==0) {
            contentIntent = PendingIntent.getActivity(this, 0,
                    new Intent(this, Afterverif.class), 0);
        }
        if (etat==1) {
            contentIntent = PendingIntent.getActivity(this, 0,
                    new Intent(this, Gestionimc.class), 0);
        }
        NotificationCompat.Builder alamNotificationBuilder = new NotificationCompat.Builder(
                this).setContentTitle("Alarm").setSmallIcon(R.mipmap.ic_launcher)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
                .setDefaults(Notification.DEFAULT_SOUND)

                .setContentText(msg);


        alamNotificationBuilder.setContentIntent(contentIntent);
        alarmNotificationManager.notify(1, alamNotificationBuilder.build());
        Log.d("AlarmService", "Notification sent.");
    }
}
