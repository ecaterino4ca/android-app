package edu.ubb.movie_app.moviebile.FirebaseServices;

import android.util.Log;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import edu.ubb.movie_app.moviebile.MainMovieListActivity;

/**
 * Created by ecaterina on 1/13/18.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    String TAG = "MessagingService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Toast.makeText(MyFirebaseMessagingService.this, remoteMessage.getData().toString(), Toast.LENGTH_SHORT).show();
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        }
        if (remoteMessage.getNotification() != null) {
            Toast.makeText(MyFirebaseMessagingService.this, remoteMessage.getNotification().getBody(), Toast.LENGTH_SHORT).show();
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

    }
}
