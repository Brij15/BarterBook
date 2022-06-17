package com.example.barterbooksapp;

import android.app.Service;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FirebasenotificationsService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO(developer): Handle FCM messages here
        Log.d("Firebase", "Message Notification Body: " + remoteMessage.getNotification().getBody());
    }
}
