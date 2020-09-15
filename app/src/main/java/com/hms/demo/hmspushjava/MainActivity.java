package com.hms.demo.hmspushjava;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;

import com.huawei.hmf.tasks.OnCompleteListener;
import com.huawei.hmf.tasks.Task;
import com.huawei.hms.push.HmsMessaging;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    public static final String SPORTS_SUBSCRIPTION = "Sports";
    public static final String WEATHER_SUBSCRIPTION = "Weather";
    public static final String TECH_SUBSCRIPTION = "Technology";
    public static final String TAG="MainActivity";

    SwitchCompat sports;
    SwitchCompat weather;
    SwitchCompat tech;

    SharedPreferences preferences;
    AlertDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showLoadingDialog();
        sports = findViewById(R.id.sports);
        weather = findViewById(R.id.weather);
        tech = findViewById(R.id.tech);
        preferences = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
        loadSubscriptionsState();
        sports.setOnCheckedChangeListener(this);
        weather.setOnCheckedChangeListener(this);
        tech.setOnCheckedChangeListener(this);
    }

    public void showLoadingDialog(){
        loadingDialog=LoadingDialog.createDialog(this);
        loadingDialog.show();
    }

    public void hideLoadingDialog(){
        if(loadingDialog!=null&&loadingDialog.isShowing()){
            loadingDialog.dismiss();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void loadSubscriptionsState() {
        sports.setOnCheckedChangeListener(null);
        weather.setOnCheckedChangeListener(null);
        tech.setOnCheckedChangeListener(null);
        sports.setChecked(preferences.getBoolean(SPORTS_SUBSCRIPTION, false));
        weather.setChecked(preferences.getBoolean(WEATHER_SUBSCRIPTION, false));
        tech.setChecked(preferences.getBoolean(TECH_SUBSCRIPTION, false));
        hideLoadingDialog();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        showLoadingDialog();
        switch (buttonView.getId()) {
            case R.id.sports:
                changeSubscriptionState(SPORTS_SUBSCRIPTION, isChecked);
                break;

            case R.id.weather:
                changeSubscriptionState(WEATHER_SUBSCRIPTION, isChecked);
                break;

            case R.id.tech:
                changeSubscriptionState(TECH_SUBSCRIPTION, isChecked);
                break;
        }
    }

    public void changeSubscriptionState(String topic, boolean subscription) {
        if(subscription){
            //subscribe
            subscribe(topic);
        }
        else{
            //unsibscribe
            unsubscribe(topic);
        }
    }

    public void subscribe(final String topic) {
        try {
            HmsMessaging.getInstance(this).subscribe(topic)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.i(TAG, "subscribe Complete");
                                saveSubscriptionState(topic,true);
                            } else {
                                Log.e(TAG, "subscribe failed: ret=" + task.getException().getMessage());
                                loadSubscriptionsState();
                            }
                        }
                    });
        } catch (Exception e) {
            Log.e(TAG, "subscribe failed: exception=" + e.getMessage());
        }
    }

    public void unsubscribe(final String topic) {
        try {
            HmsMessaging.getInstance(this).unsubscribe(topic)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.i(TAG, "unsubscribe Complete");
                                saveSubscriptionState(topic,false);
                            } else {
                                Log.e(TAG, "unsubscribe failed: ret=" + task.getException().getMessage());
                                loadSubscriptionsState();
                            }
                        }
                    });
        } catch (Exception e) {
            Log.e(TAG, "unsubscribe failed: exception=" + e.getMessage());
        }
    }

    private void saveSubscriptionState(String topic, boolean b) {
        SharedPreferences.Editor editor=preferences.edit();
        editor.putBoolean(topic,b);
        editor.apply();
        hideLoadingDialog();
    }
}