package com.hfad.joke;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import androidx.core.app.NotificationCompat;
import android.util.Log;



public class DelayedMessageService extends IntentService {
    public static final String EXTRA_MESSAGE = DelayedMessageService.class.getSimpleName();
    public static final int NOTIFICATION_ID = 5453;

    public static final String NOTIFICATION_CHANNEL_ID = DelayedMessageService.class.getSimpleName();

    public DelayedMessageService() {
        super("DelayedMessageService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        synchronized (this) {
            try {
                wait(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

            String text = intent.getStringExtra(EXTRA_MESSAGE);
            showText(text);
        }

    private void showText(final String text) {
        Log.v("DelayedMessageService", "The message is: " + text);


        //Create a notification builder
        // Use NotificationCompat instead of Notification
        Notification.Builder builder =
                new Notification.Builder(this, NOTIFICATION_CHANNEL_ID)
                        .setSmallIcon(android.R.drawable.sym_def_app_icon)
                        .setContentTitle(getString(R.string.question))
                        .setContentText(text)
                        .setAutoCancel(true);

        //Create an action
        Intent actionIntent = new Intent(this, MainActivity.class);
        PendingIntent actionPendingIntent = PendingIntent.getActivity(this, 0, actionIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        builder.setContentIntent(actionPendingIntent);

        //Issue the notification

        NotificationManager notificationManager =
            (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }
}