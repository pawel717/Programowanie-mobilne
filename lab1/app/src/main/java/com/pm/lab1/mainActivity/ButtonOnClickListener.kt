package com.pm.lab1.mainActivity

import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.view.View
import com.pm.lab1.secondActivity.SecondActivity

class ButtonOnClickListener : View.OnClickListener {

    override fun onClick(v: View?) {
        val intent = Intent(v!!.context, SecondActivity::class.java)

        startActivity(v.context, intent, null)
    }

}