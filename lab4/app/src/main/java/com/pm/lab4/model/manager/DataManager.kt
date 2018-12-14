package com.pm.lab4.model.manager

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.pm.lab4.model.database.DbHelper
import com.pm.lab4.model.database.dao.MembershipDao
import com.pm.lab4.model.database.dao.ProjectGroupDao
import com.pm.lab4.model.database.dao.StudentDao
import com.pm.lab4.model.entities.Membership
import com.pm.lab4.model.entities.ProjectGroup
import com.pm.lab4.model.entities.Student
import java.lang.reflect.Member
import java.sql.SQLException
import java.util.concurrent.atomic.AtomicBoolean


class DataManager : IDataManager {

    private val context: Context
    private val db: SQLiteDatabase
    private val studentDao: StudentDao
    private val projectGroupDao: ProjectGroupDao
    private val membershipDao: MembershipDao

    private constructor (context: Context) {
        this.context = context
        val dbHelper = DbHelper(this.context);
        this.db = dbHelper.getWritableDatabase()
        this.studentDao = StudentDao(this.db)
        this.projectGroupDao = ProjectGroupDao(this.db)
        this.membershipDao = MembershipDao(this.db)
    }

    companion object {
        var INSTANCE: DataManager? = null

        fun getInstance(context: Context): DataManager =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: DataManager(context)
                }

    }

    override fun getAllStudents(): ArrayList<Student> {
        val students = studentDao.getAll()

        students.forEach { student ->
            student.projectGroupList = membershipDao.getProjectGroupsByStudentId(student.id!!)
        }

        return students
    }

    override fun getAllProjectGroups(): ArrayList<ProjectGroup> {
        val projectGroups = projectGroupDao.getAll()

        projectGroups.forEach { projectGroup ->
            projectGroup.membersList = membershipDao.getStudentsByProjectGroupId(projectGroup.id!!)
        }

        return projectGroups
    }

    override fun updateStudent(student: Student): Boolean {

        if (studentDao.update(student) >= 1)
            return true

        return false
    }


    override fun deleteStudent(student: Student): Boolean {
        var result: Boolean = false

        try {
            db.beginTransaction()
            val dbStudent = studentDao.get(student.id!!)
            if (dbStudent != null) {
                student.projectGroupList.forEach { projectGroup ->
                    membershipDao.delete(Membership(null, student.id, projectGroup.id))
                }
                studentDao.delete(student)
            }
            db.setTransactionSuccessful()
            result = true
        } catch (e: SQLException) {
            Log.e("Exception", "Error when deleting student", e)
        } finally {
            db.endTransaction()
        }

        return result
    }

    override fun saveStudent(student: Student): Long {
        var studentId: Long
        try {
            db.beginTransaction()

            studentId = studentDao.save(student)

            if (student.projectGroupList.size > 0) {
                student.projectGroupList.forEach { projectGroup ->
                    val projectGroupId: Long
                    val dbProjectGroup = projectGroupDao.getByName(projectGroup.name)
                    if (dbProjectGroup == null)
                        projectGroupId = projectGroupDao.save(projectGroup)
                    else
                        projectGroupId = dbProjectGroup.id!!

                    val membership = Membership(null, studentId, projectGroupId)
                    if (!membershipDao.exists(membership)) {
                        membershipDao.save(membership)
                    }
                }
            }
            db.setTransactionSuccessful()
        } catch (e: SQLException) {
            Log.e("Exception", "Error when saving student", e)
            studentId = -1
        } finally {
            db.endTransaction()
        }

        return studentId
    }

    override fun saveMembership(student: Student, projectGroup: ProjectGroup): Boolean {
        var result = false
        val membership = Membership(null, student.id, projectGroup.id)
        try {
            db.beginTransaction()
            if(!membershipDao.exists(membership)) {
                membershipDao.save(membership)
            }
            db.setTransactionSuccessful()
            result = true
        } catch (e : SQLException) {
            Log.e("Exception", "Error when saving membership", e)
        } finally {
            db.endTransaction()
        }

        return result
    }

    override fun deleteMembership(student: Student, projectGroup: ProjectGroup): Int {
        val membership = Membership(null, student.id, projectGroup.id)
        return membershipDao.delete(membership)

    }
}