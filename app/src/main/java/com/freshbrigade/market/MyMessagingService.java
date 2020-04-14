package com.freshbrigade.market;

import android.content.SharedPreferences;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        showNotification(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody());
    }


    @Override
    public void onNewToken(String token) {
        Log.e("id123", "Refreshed token: " + token);

         SharedPreferences sharedPreferences;
         SharedPreferences.Editor editor;
        sharedPreferences = getApplicationContext().getSharedPreferences("fcm",MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString("fcmID",token);
        editor.commit();

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
       // sendRegistrationToServer(token);
    }
    public void showNotification(String title,String messsage){
       // Bitmap icon = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.logo);
        NotificationCompat.Builder builder=new NotificationCompat.Builder(this,"FreshBrigade")
                .setContentTitle(title)
                .setSmallIcon(R.drawable.log)
               // .setLargeIcon(icon)
                .setContentText(messsage);

        NotificationManagerCompat managerCompat=NotificationManagerCompat.from(this);
        managerCompat.notify(999,builder.build());
    }
}
