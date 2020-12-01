package com.hms.demo.hmspushjava;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.huawei.hms.push.HmsMessageService;
import com.huawei.hms.push.RemoteMessage;

import java.util.Map;

public class MyHmsMessageService extends HmsMessageService {

    public static final String CHANNEL_ID="PUSH_NOTIFICATIONS";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        createNotificationChannel();
        Log.e("HMS","OnMessageReceived");
        Map<String,String> map=remoteMessage.getDataOfMap();
        for(String key: map.keySet()){
            Log.e("Data",key+" "+map.get(key));
        }
        String title=map.get("title");
        String message=map.get("message");
        if(title!=null&&message!=null){
            NotificationCompat.Builder builder= new NotificationCompat.Builder(this,CHANNEL_ID);
            builder.setContentTitle(title);
            builder.setContentText(message);
            builder.setSmallIcon(R.mipmap.ic_launcher);
            NotificationManager manager= getSystemService(NotificationManager.class);
            manager.notify(0,builder.build());
        }

    }

    private void createNotificationChannel() {
            CharSequence name = "Notifications";
            String description = "Push Notifications";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.e("Token",s);

    }
}
