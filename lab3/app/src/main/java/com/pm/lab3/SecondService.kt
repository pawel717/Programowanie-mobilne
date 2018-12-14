package com.pm.lab3

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import android.widget.Toast
import java.util.*

class SecondService : Service() {

    private var timerTask: TimerTask? = null
    private var timer: Timer? = null
    private var count : Int = 0

    private val binder : IBinder = MyBinder()

    override fun onCreate() {
        super.onCreate()
        timer = Timer()
    }

    override fun onBind(intent: Intent): IBinder {
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(applicationContext, "Your bound service has been started", Toast.LENGTH_SHORT)
                    .show()
        }

        // Schedule timer task (toast displaying) every 4 seconds
        timerTask = object : TimerTask() {
            override fun run() {
                count++
                Handler(Looper.getMainLooper()).post {
                    Toast.makeText(applicationContext, "Your bound service is still running", Toast.LENGTH_SHORT)
                            .show()
                }
                Log.d("SecondService", "task")
            }
        }
        timer!!.scheduleAtFixedRate(timerTask, 4 * 1000, 4 * 1000);

        return binder
    }

    override fun onDestroy() {
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(applicationContext, "Bound service has been stopped", Toast.LENGTH_SHORT)
                    .show()
        }
        // Stop timerTask and timer
        timerTask!!.cancel()
        timer!!.purge()
        super.onDestroy()
    }

    fun getCount() : Int {
        return count
    }

    inner class MyBinder() : Binder() {
        fun getService(): SecondService {
            return this@SecondService
        }
    }
}
