package com.pm.lab4.activities

import android.content.Context
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.pm.lab4.R
import com.pm.lab4.model.entities.ProjectGroup
import com.pm.lab4.model.entities.Student
import com.pm.lab4.model.manager.DataManager
import android.widget.CheckedTextView



class ProjectGroupsAdapter(context : Context, projectGroups : List<ProjectGroup>, val student: Student)
    : ArrayAdapter<ProjectGroup>(context, 0, projectGroups) {


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView

        // if convertView is not being reused need to inflate view
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_project_group, parent, false)
        }

        // get the data object on current position
        val projectGroup = getItem(position)

        // use data object to populate views
        val checkedTextView = convertView!!.findViewById<CheckedTextView>(R.id.project_group_name)

        checkedTextView.setText(projectGroup.name)

        if(projectGroup.membersList.find { m -> m.id == student.id } != null)
            checkedTextView.isChecked = true
        else
            checkedTextView.isChecked = false

        Log.d("ProjectGroupsAdapter", "checked:"+checkedTextView.isChecked)

        checkedTextView.setOnClickListener{
            val dataManager = DataManager.getInstance(context)

            if(checkedTextView.isChecked) {
                checkedTextView.isChecked = false
                dataManager.deleteMembership(student, projectGroup)
            }
            else {
                checkedTextView.isChecked = true
                dataManager.saveMembership(student, projectGroup)
            }

        }

        // return the completed view to render on screen
        return convertView
    }
}