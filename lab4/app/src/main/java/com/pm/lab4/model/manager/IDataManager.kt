package com.pm.lab4.model.manager

import com.pm.lab4.model.entities.ProjectGroup
import com.pm.lab4.model.entities.Student

interface IDataManager {
    fun getAllStudents() : ArrayList<Student>
    fun getAllProjectGroups() : ArrayList<ProjectGroup>
    fun updateStudent(student: Student) : Boolean
    fun deleteStudent(student: Student) : Boolean
    fun saveStudent(student: Student) : Long
    fun saveMembership(student: Student, projectGroup: ProjectGroup): Boolean
    fun deleteMembership(student: Student, projectGroup: ProjectGroup): Int
}