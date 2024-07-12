package ru.rustore.rustorepushjavaexample.app;

import android.app.Application;
import android.util.Log;

import ru.rustore.rustorepushjavaexample.R;
import ru.rustore.rustorepushjavaexample.notifications.PushLogger;
import ru.rustore.rustorepushjavaexample.notifications.wrapper.NotificationManagerWrapper;
import ru.rustore.sdk.pushclient.RuStorePushClient;

public class App extends Application {
    public static final String LOG_TAG = "App";

    @Override
    public void onCreate() {
        super.onCreate();
        initPushes();
    }

    /*
        Инициализируем SDK и создаем канал для отображения пуш-уведомлений средствами SDK.
        Параметр projectId замените на свой
        Инструкция получения projectID
        https://www.rustore.ru/help/sdk/push-notifications/kotlin-java/6-0-0#initialization
     */

    private void initPushes() {
        createNotificationPushChannel();
        RuStorePushClient.INSTANCE.init(
                this,
                "your_push_project_id",
                new PushLogger("PushExampleLogger")
        );

        RuStorePushClient.INSTANCE.getToken()
            .addOnSuccessListener(result -> {
                Log.w(LOG_TAG, "getToken onSuccess = " + result);
            })
            .addOnFailureListener(throwable -> {
                Log.e(LOG_TAG, "getToken onFailure", throwable);
            });
    }

    /*
    Создаем канал для отображения пуш-уведомлений средствами SDK.
    channelId указанный в этом методе должен совпадать с тем, который указан в манифесте приложения
     */

    public void createNotificationPushChannel() {
        NotificationManagerWrapper.getInstance(this).createNotificationChannel(
                getString(R.string.notifications_notification_push_channel_id),
                getString(R.string.notifications_notification_push_channel_name)
        );
    }
}
