package com.pm.lab1.secondActivity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.pm.lab1.R
import com.pm.lab1.model.Student
import kotlinx.android.synthetic.main.activity_second.*


class SecondActivity : AppCompatActivity() {

    // list containing Student objects
    var studentsList  = mutableListOf<Student>(
            Student("Jan", "Kowalski"),
            Student("Adam", "Miauczyński"),
            Student("Anna", "Nowakowska"),
            Student("Tomasz", "Drabek"),
            Student("Natalia", "Przybysz")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        // create custom Adapter for students
        val studentsAdapter = StudentsAdapter(this, studentsList)

        // set ListView adapter
        studentsListView.adapter = studentsAdapter
    }
}
