package ru.vkinquiry.fcm;

import android.annotation.SuppressLint;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;

import org.jetbrains.annotations.NotNull;

@SuppressLint("Registered")
public class MyFirebaseInstanceIdService extends FirebaseMessagingService {
    @Override
    public void onNewToken(@NotNull String s) {
        super.onNewToken(s);
        Log.d("NEW_TOKEN", s);
    }

    // If you want to send messages to this application instance or
    // manage this apps subscriptions on the server side, send the
    // Instance ID token to your app server.
    // sendRegistrationToServer(refreshedToken);
}
