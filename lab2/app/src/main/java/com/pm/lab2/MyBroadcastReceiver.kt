package com.pm.lab2

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class MyBroadcastReceiver : BroadcastReceiver() {

    public override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("MyBroadcastReceiver", "onReceive")
        Toast.makeText(context, "notification recived", Toast.LENGTH_SHORT).show()
    }

}