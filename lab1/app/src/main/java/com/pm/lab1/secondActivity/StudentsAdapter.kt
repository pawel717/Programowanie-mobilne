package com.pm.lab1.secondActivity

import android.content.Context
import android.widget.ArrayAdapter
import com.pm.lab1.model.Student
import android.widget.TextView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pm.lab1.R

class StudentsAdapter(context : Context, students : List<Student>)
    : ArrayAdapter<Student>(context, 0, students) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView

        // if convertView is not being reused need to inflate view
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_student, parent, false)
        }

        // get the data object on current position
        val student = getItem(position)

        // use data object to populate views
        convertView!!.findViewById<TextView>(R.id.studentFirstName).setText(student.firstName)

        convertView!!.findViewById<TextView>(R.id.studentLastName).setText(student.lastName)

        // return the completed view to render on screen
        return convertView
    }

}