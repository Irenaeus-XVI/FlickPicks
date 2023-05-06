package com.example.app;


import static com.example.app.Adapters.MovieAdapter.EXTRA_IS_FAVORITE;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.graphics.Color;
import android.os.PersistableBundle;

import androidx.core.app.NotificationCompat;

import java.security.cert.CertPathBuilder;

public class NotificationJobService extends JobService {

    private static final String EXTRA_IS_FAVORITE = "is_favorite";
    NotificationManager mNotifyManager;

    // Notification channel ID.
    private static final String PRIMARY_CHANNEL_ID =
            "primary_notification_channel";


    /**
     * Creates a Notification channel, for OREO and higher.
     */
    public void createNotificationChannel() {

        // Define notification manager object.
        mNotifyManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        // Notification channels are only available in OREO and higher.
        // So, add a check on the SDK version.
        if (android.os.Build.VERSION.SDK_INT >=
                android.os.Build.VERSION_CODES.O) {

            // Create the NotificationChannel with all the parameters.
            NotificationChannel notificationChannel = new NotificationChannel
                    (PRIMARY_CHANNEL_ID,
                            "Job Service notification",
                            NotificationManager.IMPORTANCE_HIGH);

            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription
                    ("Notifications from Job Service");
            mNotifyManager.createNotificationChannel(notificationChannel);
        }
    }


    @Override
    public boolean onStartJob(JobParameters jobParameters) {

        // Create the notification channel.
        createNotificationChannel();

        // Set up the notification content intent to launch the app when clicked.
        PendingIntent contentPendingIntent = PendingIntent.getActivity
                (this, 0, new Intent(this, MainActivity.class),
                        PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        PersistableBundle extras = jobParameters.getExtras();
        NotificationCompat.Builder builder = null;

        if (extras != null) {
            boolean isFavorite = extras.getBoolean(EXTRA_IS_FAVORITE);
            if (isFavorite) {
                // Build the notification with all of the parameters.
                builder = new NotificationCompat
                        .Builder(this, PRIMARY_CHANNEL_ID)
                        .setContentTitle("FlickPicks")
                        .setContentText("Added to favourites!")
                        .setContentIntent(contentPendingIntent)
                        .setSmallIcon(R.drawable.ic_baseline_movie_24)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setDefaults(NotificationCompat.DEFAULT_ALL)
                        .setAutoCancel(true);

                mNotifyManager.notify(0, builder.build());
            } else {
                builder = new NotificationCompat.Builder
                        (this, PRIMARY_CHANNEL_ID)
                        .setContentTitle("FlickPicks")
                        .setContentText("Removed from favourites!")
                        .setContentIntent(contentPendingIntent)
                        .setSmallIcon(R.drawable.ic_baseline_movie_24)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setDefaults(NotificationCompat.DEFAULT_ALL)
                        .setAutoCancel(true);

                mNotifyManager.notify(0, builder.build());
            }
        }


        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }
}
