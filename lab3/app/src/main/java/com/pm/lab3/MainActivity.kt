package com.pm.lab3

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private var secondService : SecondService? = null
    private var isBounded : Boolean = false

    private val serviceConnection : ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            val binder = service as SecondService.MyBinder
            secondService = binder.getService()
            isBounded = true
        }

        override fun onServiceDisconnected(name: ComponentName) {
            isBounded = false
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // bind button actions
        button_list_activity.setOnClickListener {
            Intent(this, SecondActivity::class.java).also { intent ->
                startActivity(intent)
            }
        }

        button_get_counter.setOnClickListener {
            secondService?.let { secondService ->
                val count = secondService.getCount()
                Toast.makeText(applicationContext, count.toString(), Toast.LENGTH_SHORT).show()
            }
        }

        Intent(this, FirstService::class.java).also { intent ->
            startService(intent)
        }

    }

    override fun onStart() {
        super.onStart()
        Intent(this, SecondService::class.java).also { intent ->
            bindService(intent, serviceConnection, BIND_AUTO_CREATE)
        }
    }

    override fun onStop() {
        super.onStop()
        if(isBounded){
            unbindService(serviceConnection)
            isBounded = false
        }
    }
}
