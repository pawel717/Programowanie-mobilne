package com.pm.lab4.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.PopupWindow
import com.pm.lab4.R
import com.pm.lab4.model.entities.ProjectGroup
import com.pm.lab4.model.entities.Student
import com.pm.lab4.model.manager.DataManager
import com.pm.lab4.model.manager.IDataManager
import kotlinx.android.synthetic.main.activity_student_list.*


class StudentListActivity : AppCompatActivity() {

    private lateinit var dataManager : IDataManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_list)

        dataManager = DataManager.getInstance(applicationContext)

        var students = dataManager.getAllStudents()

        val adapter = StudentsAdapter(applicationContext, students)
        students_list_view.adapter = adapter

        button_add.setOnClickListener {
            var popupView = LayoutInflater.from(this).inflate(R.layout.edit_popup,  null)

            val firstname_input = popupView.findViewById<EditText>(R.id.firstname_input)
            val lastname_input = popupView.findViewById<EditText>(R.id.lastname_input)

            var popupWindow = PopupWindow(popupView, LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT, true)

            popupWindow.showAtLocation(constraint_layout, Gravity.CENTER, 0, 0)

            popupView.findViewById<Button>(R.id.button_cancel).setOnClickListener {
                popupWindow.dismiss()
            }

            popupView.findViewById<Button>(R.id.button_save).setOnClickListener {
                val dataManager = DataManager.getInstance(applicationContext)

                val student = Student()
                student.firstName = firstname_input.text.toString()
                student.lastName = lastname_input.text.toString()

                dataManager.saveStudent(student)

                students = dataManager.getAllStudents()
                adapter.clear()
                adapter.addAll(students)
                adapter.notifyDataSetChanged()

                popupWindow.dismiss()
            }
        }

        Log.d("StudentListActivity", "onCreate")
    }
}
