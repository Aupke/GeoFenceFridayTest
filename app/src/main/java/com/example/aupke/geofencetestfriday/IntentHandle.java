package com.example.aupke.geofencetestfriday;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

/**
 * Created by Aupke on 17-11-2017.
 */

public class IntentHandle extends IntentService{
    public IntentHandle() {
        super("IntentHandle");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Bundle bundle = intent.getExtras();
        String title = bundle.getString("TITLE_VIEW");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel("default", "Default Notifications", NotificationManager.IMPORTANCE_DEFAULT);

            // Configure the notification channel.
            notificationChannel.setDescription("Channel description");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationChannel.enableVibration(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        Intent notificationClick = new Intent(this, DistanceActivity.class);
        notificationClick.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        int time = (int) (System.currentTimeMillis() % 100000000);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),time, notificationClick, 0);


        Notification notification = new NotificationCompat.Builder(this.getApplicationContext(), "default")
                .setContentTitle(title)
                .setContentText("Check out how to get to the Datalogi building!")
                .setTicker("You're near the Hovedbanegaard. Do you want to see how to get to the CS building?")
                .setSmallIcon(R.drawable.stat_sys_gps_on)
                .setAutoCancel(true)
                .setVibrate(new long[]{1500,0,1500,0})
                .setContentIntent(pendingIntent)
                .build();

        Log.d(MainActivity.TAG, "Notification created");

        NotificationManagerCompat manager = NotificationManagerCompat.from(getApplicationContext());
        manager.notify(1, notification);


    }
}
