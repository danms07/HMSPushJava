package com.hms.demo.hmspushjava;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.huawei.hms.push.HmsMessageService;
import com.huawei.hms.push.RemoteMessage;

import java.util.Map;

public class MyHmsMessageService extends HmsMessageService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.e("HMS","OnMessageReceived");
        Map<String,String> map=remoteMessage.getDataOfMap();
        Bundle b= new Bundle();
        for(String key: map.keySet()){
            b.putString(key,map.get(key));
        }
        Intent i=new Intent(this,TargetActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        b.putString("origin","onMessageReceived");
        i.putExtras(b);
        startActivity(i);
    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.e("Token",s);

    }
}
