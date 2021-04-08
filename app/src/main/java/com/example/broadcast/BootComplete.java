package com.example.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class BootComplete extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent startAppIntent = new Intent(context,MainActivity.class);
        context.startActivity(intent);
        Toast.makeText(context,"Boot Has Completed",Toast.LENGTH_LONG).show();
    }
}