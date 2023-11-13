package ru.rustore.rustorepushjavaexample.notifications.wrapper;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationChannelCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import ru.rustore.rustorepushjavaexample.notifications.wrapper.model.AppNotification;

public class NotificationManagerWrapper {
    private final NotificationManagerCompat notificationManager;

    private NotificationManagerWrapper(NotificationManagerCompat notificationManager) {
        this.notificationManager = notificationManager;
    }

    public void createNotificationChannel(String channelId, String channelName){
        NotificationChannelCompat.Builder builder =
                new NotificationChannelCompat.Builder(channelId, NotificationManagerCompat.IMPORTANCE_DEFAULT);
        builder.setName(channelName);
        notificationManager.createNotificationChannel(builder.build());
    }

    public void showNotification(Context context, AppNotification data) {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context, data.getChannelId())
                        .setContentTitle(data.getTitle())
                        .setContentText(data.getMessage());

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        if (notificationManager.getNotificationChannel(data.getChannelId()) == null) {
            createNotificationChannel(data.getChannelId(), data.getChannelName());
        }

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
        ) != PackageManager.PERMISSION_DENIED) {
            return;
        }

        notificationManager.notify(data.getId(), builder.build());
    }

    public static NotificationManagerWrapper getInstance(Context context) {
        if (instance == null) {
            instance = new NotificationManagerWrapper(NotificationManagerCompat.from(context));
        }
        return instance;
    }

    private static NotificationManagerWrapper instance;
}
