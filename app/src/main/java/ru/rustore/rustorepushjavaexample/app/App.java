package ru.rustore.rustorepushjavaexample.app;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;

import ru.rustore.rustorepushjavaexample.R;
import ru.rustore.rustorepushjavaexample.notifications.PushLogger;
import ru.rustore.rustorepushjavaexample.notifications.wrapper.NotificationManagerWrapper;
import ru.rustore.sdk.core.tasks.OnCompleteListener;
import ru.rustore.sdk.pushclient.RuStorePushClient;
import ru.rustore.sdk.pushclient.common.logger.Logger;

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
     */

    private void initPushes() {
        createNotificationPushChannel();
        RuStorePushClient.INSTANCE.init(
                this,
                "111111",
                new PushLogger("PushExampleLogger")
        );
        RuStorePushClient.INSTANCE.getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onSuccess(String result){
                Log.e(LOG_TAG, "getToken onSuccess = " + result);
            }
            @Override
            public void onFailure(@NonNull Throwable throwable){
                Log.e(LOG_TAG, "getToken onFailure", throwable);
            }
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
