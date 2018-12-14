package com.pm.lab3

import android.app.Notification
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import java.util.*

class FirstService : Service() {

    private var timerTask: TimerTask? = null
    private var timer: Timer? = null
    private lateinit var toast: Toast

    override fun onCreate() {
        super.onCreate()
        timer = Timer()
        toast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        // Move process to foreground with just empty notification
        // It makes that proceess will remain running longer when low memory
        startForeground(1, Notification())

        toast.setText("Your service has been started")
        toast.show()

        // Schedule timer task (toast displaying) every 20 seconds
        timerTask = object : TimerTask() {
            override fun run() {
                toast.setText("Your service is still running")
                toast.show()
                Log.d("FirstService", "task")
            }
        }
        timer!!.scheduleAtFixedRate(timerTask, 20 * 1000, 20 * 1000);

        // Do not recreate service when it is killed
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }
}
