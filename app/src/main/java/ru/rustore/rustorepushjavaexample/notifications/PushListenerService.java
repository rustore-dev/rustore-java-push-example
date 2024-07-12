package ru.rustore.rustorepushjavaexample.notifications;

import android.util.Log;
import android.util.Pair;

import androidx.annotation.NonNull;

import java.util.List;

import ru.rustore.rustorepushjavaexample.R;
import ru.rustore.rustorepushjavaexample.notifications.wrapper.NotificationManagerWrapper;
import ru.rustore.rustorepushjavaexample.notifications.wrapper.model.AppNotification;
import ru.rustore.sdk.pushclient.messaging.exception.RuStorePushClientException;
import ru.rustore.sdk.pushclient.messaging.model.RemoteMessage;
import ru.rustore.sdk.pushclient.messaging.service.RuStoreMessagingService;

public class PushListenerService extends RuStoreMessagingService {

    private static final String LOG_TAG = "PushListenerService";
    private NotificationManagerWrapper notificationManagerWrapper = NotificationManagerWrapper.getInstance(this);

    @Override
    public void onNewToken(@NonNull String token) {
        Log.d(LOG_TAG, "onNewToken token = " + token);
    }

    @Override
    public void onDeletedMessages() {
        Log.d(LOG_TAG, "onDeletedMessages");
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);

        Pair<String, String> channelInfo = getChannelInfo();

        notificationManagerWrapper.showNotification(
                this,
                new AppNotification(
                        message.hashCode(),
                        message.getNotification() != null ? message.getNotification().getTitle() : null,
                        message.getNotification() != null ? message.getNotification().getBody() : null,
                        channelInfo.first,
                        channelInfo.second
                )
        );
    }


    @Override
    public void onError(@NonNull List<? extends RuStorePushClientException> errors) {
        for (RuStorePushClientException error : errors) {
            error.printStackTrace();
        }
    }

    private Pair<String, String> getChannelInfo(){
        String channelId = getString(R.string.notifications_data_push_channel_id);
        String channelName = getString(R.string.notifications_data_push_channel_name);
        return new Pair<>(channelId, channelName);
    }
}
