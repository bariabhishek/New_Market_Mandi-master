package com.freshbrigade.market.NotificationPackage;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.freshbrigade.market.R;
import com.freshbrigade.market.Vender_Update_price;

public class AlarmReciver1 extends BroadcastReceiver
{
    private static final String Channel_ID="ChannelID";
    private static final String Chanel_Name="ChanelName";
    private static final String Channel_Description="Chanel_Desc";
    NotificationManager nm;

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager= null;
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel=new NotificationChannel(Channel_ID,Chanel_Name, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(Channel_Description);
            notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
        Intent repeatingIntent=new Intent(context, Vender_Update_price.class);
        repeatingIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent=PendingIntent.getActivity(context,100,repeatingIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder=new NotificationCompat.Builder(context)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentTitle("Hello")
                .setContentText("jfgerfjgeojgfbljergbjlwgbve")
                .setAutoCancel(true);
        NotificationManagerCompat notificationManagerCompat=NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(1,builder.build());
    }
}
