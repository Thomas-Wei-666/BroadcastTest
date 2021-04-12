package com.example.broadcast;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.sql.Connection;

public class MainActivity extends AppCompatActivity {
    private NetworkChangeReceiver networkChangeReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        networkChangeReceiver = new NetworkChangeReceiver();
        registerReceiver(networkChangeReceiver,intentFilter);

        Button broadcastBt = (Button)findViewById(R.id.broadcast_bt);
        Button startAct2Bt = (Button)findViewById(R.id.start_act2);
        broadcastBt.setOnClickListener(v -> {
            Intent broadcastIntent = new Intent(Intent.ACTION_EDIT);
            broadcastIntent.setComponent(new ComponentName(getPackageName(), getPackageName() + ".MyBroadcastReceiver"));
            //Android8.0以上版本发送广播的时候需要加上setComponent....，指定接收器的包名和完整路径名....
            MainActivity.this.sendBroadcast(broadcastIntent);
        });
        startAct2Bt.setOnClickListener(v -> LocalBroadcastTest.startLocalBroadcastTest(MainActivity.this));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(networkChangeReceiver);
    }

    class NetworkChangeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isAvailable()) {
                Toast.makeText(context, "network is now available", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "network is now broken", Toast.LENGTH_SHORT).show();
            }
        }
    }
}