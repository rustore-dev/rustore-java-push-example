package ru.rustore.rustorepushjavaexample.notifications;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import ru.rustore.sdk.pushclient.common.logger.Logger;


public class PushLogger implements Logger {
    private final String tag;
    public PushLogger(String tag){
        this.tag = tag;
    }
    @Override
    public void debug(@NonNull String message, Throwable throwable){
        Log.d(tag, message, throwable);
    }
    @Override
    public void error(@NonNull String message, Throwable throwable){
        Log.e(tag, message, throwable);
    }
    @Override
    public void info(@NonNull String message, @Nullable Throwable throwable){
        Log.i(tag, message, throwable);
    }
    @Override
    public void verbose(@NonNull String message, @Nullable Throwable throwable){
        Log.v(tag, message, throwable);
    }
    @Override
    public void warn(@NonNull String message, @Nullable Throwable throwable){
        Log.w(tag, message, throwable);
    }
    @NonNull
    @Override
    public Logger createLogger(@NonNull String newTag){
        String combinedTag = (tag != null) ? tag + ":" + newTag : newTag;
        return new PushLogger(combinedTag);
    }

    @NonNull
    @Override
    public Logger createLogger(@NonNull Object o) {
        String combinedTag = (tag != null) ? tag + ":" + o : (String) o;
        return new PushLogger(combinedTag);
    }
}
