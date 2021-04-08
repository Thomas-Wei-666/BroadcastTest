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
    private IntentFilter intentFilter;
    private NetworkChangeReceiver networkChangeReceiver;
    private Button broadcast_bt;
    private Button start_act2_bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        networkChangeReceiver = new NetworkChangeReceiver();
        registerReceiver(networkChangeReceiver,intentFilter);

        broadcast_bt = (Button)findViewById(R.id.broadcast_bt);
        start_act2_bt = (Button)findViewById(R.id.start_act2);
        broadcast_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent broadcastIntent = new Intent(Intent.ACTION_EDIT);
                broadcastIntent.setComponent(new ComponentName(getPackageName(),getPackageName()+".MyBroadcastReceiver"));
                //Android8.0以上版本发送广播的时候需要加上setComponent....，指定接收器的包名和完整路径名....
                MainActivity.this.sendBroadcast(broadcastIntent);
            }
        });
        start_act2_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocalBroadcastTest.startLocalBroadcastTest(MainActivity.this);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(networkChangeReceiver);
    }

    class NetworkChangeReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if(networkInfo != null&&networkInfo.isAvailable()){
                Toast.makeText(context,"network is now available",Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(context,"network is now broken",Toast.LENGTH_SHORT).show();
            }
        }
    }
}