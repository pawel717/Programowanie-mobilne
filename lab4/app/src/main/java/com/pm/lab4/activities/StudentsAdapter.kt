package com.pm.lab4.activities

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.pm.lab4.R
import com.pm.lab4.R.id.groups_list_view
import com.pm.lab4.model.entities.Student
import com.pm.lab4.model.manager.DataManager
import kotlinx.android.synthetic.main.activity_student_list.*

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

        convertView.findViewById<TextView>(R.id.studentLastName).setText(student.lastName)

        convertView.setOnLongClickListener {
            var popupView = LayoutInflater.from(parent.context).inflate(R.layout.delete_popup,  null)

            val button_yes = popupView.findViewById<Button>(R.id.button_yes)
            val button_no = popupView.findViewById<Button>(R.id.button_no)

            var popupWindow = PopupWindow(popupView, LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT, true)

            popupWindow.showAtLocation(parent, Gravity.CENTER, 0, 0)

            button_no.setOnClickListener {
                popupWindow.dismiss()
            }

            button_yes.setOnClickListener {
                val dataManager = DataManager.getInstance(context)
                dataManager.deleteStudent(student)
                val students = dataManager.getAllStudents()
                clear()
                addAll(students)
                notifyDataSetChanged()
                popupWindow.dismiss()
            }

            true
        }

        convertView.setOnClickListener {
            var popupView = LayoutInflater.from(parent.context).inflate(R.layout.groups_list_popup,  null)

            var groupsListView = popupView.findViewById<ListView>(R.id.groups_list_view)

            val dataManager = DataManager.getInstance(context)
            val allProjectGroups = dataManager.getAllProjectGroups()

            val adapter = ProjectGroupsAdapter(parent.context, allProjectGroups, student)
            groupsListView.adapter = adapter

            var popupWindow = PopupWindow(popupView, LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT, true)

            popupWindow.showAtLocation(parent, Gravity.CENTER, 0, 0)

            val buttonOk = popupView!!.findViewById<Button>(R.id.button_ok)

            buttonOk.setOnClickListener {
                popupWindow.dismiss()
            }
        }

        convertView.findViewById<Button>(R.id.button_edit).setOnClickListener {
            var popupView = LayoutInflater.from(parent.context).inflate(R.layout.edit_popup,  null)

            val firstname_input = popupView.findViewById<EditText>(R.id.firstname_input)
            val lastname_input = popupView.findViewById<EditText>(R.id.lastname_input)

            var popupWindow = PopupWindow(popupView, LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT, true)

            popupWindow.showAtLocation(parent, Gravity.CENTER, 0, 0)

            firstname_input.setText(student.firstName)
            lastname_input.setText(student.lastName)

            popupView.findViewById<Button>(R.id.button_cancel).setOnClickListener {
                popupWindow.dismiss()
            }

            popupView.findViewById<Button>(R.id.button_save).setOnClickListener {
                val dataManager = DataManager.getInstance(context)

                student.firstName = firstname_input.text.toString()
                student.lastName = lastname_input.text.toString()

                dataManager.updateStudent(student)

                popupWindow.dismiss()
            }


        }

        // return the completed view to render on screen
        return convertView
    }

}