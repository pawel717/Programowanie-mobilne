package com.pm.lab2

import android.content.Context
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_first.*

class FirstActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)

        // initialize sharedPreferences, MODE_PRIVATE - only this application have access to preferences
        sharedPreferences = getSharedPreferences("myPref", MODE_PRIVATE)

        // set text in text_input
        text_input.setText(sharedPreferences.getString("myValue", ""))

        // by default first fragment is in frame_layout
        supportFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, FirstFragment())
                .commit()

        // BINDINGS
        button.setOnClickListener {
            // place first fragment in frame_layout
            supportFragmentManager.beginTransaction()
                    .replace(R.id.frame_layout, FirstFragment())
                    .commit()
        }

        button2.setOnClickListener {
            // place second fragment in frame layout
            supportFragmentManager.beginTransaction()
                    .replace(R.id.frame_layout, SecondFragment())
                    .commit()
        }

        save_button.setOnClickListener {
            // save text_input to shared preferences
            sharedPreferences.edit()
                    .putString("myValue", text_input.text.toString())
                    .commit()

            Toast.makeText(applicationContext, R.string.save_toast, Toast.LENGTH_SHORT).show()
        }
    }

}