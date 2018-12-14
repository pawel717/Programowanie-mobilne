package com.pm.lab1.mainActivity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.pm.lab1.R
import com.pm.lab1.secondActivity.SecondActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // bindings
        button_start_new_activity2.setOnClickListener(ButtonOnClickListener())
    }

    fun startNewActivityOnClick(view : View) {
        val intent = Intent(this, SecondActivity::class.java)

        startActivity(intent)
    }


}
