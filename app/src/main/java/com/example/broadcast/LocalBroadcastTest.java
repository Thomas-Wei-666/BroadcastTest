package com.example.broadcast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class LocalBroadcastTest extends AppCompatActivity {
    private LocalBroadcastReceiver localBroadcastReceiver;
    private LocalBroadcastManager localBroadcastManager;
    private Button send_localbroadcast_bt;
    private final String ACTION = "com.example.broadcast.LOCAL_MESSAGE";

    public static void startLocalBroadcastTest(Activity activity){
        Intent intent = new Intent(activity,LocalBroadcastTest.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        localBroadcastManager.unregisterReceiver(localBroadcastReceiver);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_broadcast_test);

        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        localBroadcastReceiver = new LocalBroadcastReceiver();

        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION);

        localBroadcastManager.registerReceiver(localBroadcastReceiver,filter);

        send_localbroadcast_bt = (Button)findViewById(R.id.send_localbroadcast_bt);
        send_localbroadcast_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_message = new Intent(ACTION);
                //intent_message.setComponent(new ComponentName(getPackageName(),getPackageName()+"."))
                // 动态广播接收器不需要在发送广播之前指定接收器位置！
                localBroadcastManager.sendBroadcast(intent_message);
            }
        });

    }
}
class LocalBroadcastReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"localbroadcast received",Toast.LENGTH_SHORT).show();
    }
}