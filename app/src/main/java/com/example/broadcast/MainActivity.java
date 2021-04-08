package com.example.broadcast;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
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
    //private BootComplete bootComplete;
    //private MyBroadcastReceiver myBroadcastReceiver;
    private Button broadcast_bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        networkChangeReceiver = new NetworkChangeReceiver();
        //myBroadcastReceiver = new MyBroadcastReceiver();
        //bootComplete = new BootComplete();
        registerReceiver(networkChangeReceiver,intentFilter);

        broadcast_bt = (Button)findViewById(R.id.broadcast_bt);
        broadcast_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent broadcastIntent = new Intent("com.example.broadcasttest.MY_MESSAGE");
                sendBroadcast(broadcastIntent);
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