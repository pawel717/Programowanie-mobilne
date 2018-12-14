package com.pm.lab4.model.database.dao

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteStatement
import android.provider.BaseColumns
import com.pm.lab4.model.database.schema.MembershipSchema
import com.pm.lab4.model.database.schema.ProjectGroupSchema
import com.pm.lab4.model.database.schema.StudentSchema
import com.pm.lab4.model.entities.Membership
import com.pm.lab4.model.entities.ProjectGroup
import com.pm.lab4.model.entities.Student
import java.util.*
import kotlin.collections.ArrayList

class MembershipDao : Dao<Membership> {
    val database : SQLiteDatabase
    val insertStatement: SQLiteStatement

    constructor(sqLiteDatabase: SQLiteDatabase)
    {
        database = sqLiteDatabase
        insertStatement = database.compileStatement(
                "insert into " + MembershipSchema.MEMBERSHIP_TABLE
                        + "(" + MembershipSchema.COLUMN_STUDENT_ID
                        + ", " + MembershipSchema.COLUMN_PROJECT_GROUP_ID
                        + ") values (?, ?)"
        )
    }

    fun getStudentsByProjectGroupId(projectGroupId: Long): ArrayList<Student> {
        var students = ArrayList<Student>()

        var query = "select * from " + StudentSchema.STUDENT_TABLE +
                " inner join " + MembershipSchema.MEMBERSHIP_TABLE + " on " +
                StudentSchema.STUDENT_TABLE+"."+StudentSchema.COLUMN_ID + "=" +
                MembershipSchema.MEMBERSHIP_TABLE+"."+MembershipSchema.COLUMN_STUDENT_ID +
                " where " + MembershipSchema.MEMBERSHIP_TABLE+"."+MembershipSchema.COLUMN_PROJECT_GROUP_ID +
                "=" + projectGroupId.toString() + ";"

        val cursor = database.rawQuery(query,null)

        if(cursor.moveToFirst())
            do {
                var student = Student()
                student.id = cursor.getLong(0)
                student.firstName = cursor.getString(1)
                student.lastName = cursor.getString(2)
                students.add(student)
            } while(cursor.move(1))

        if(!cursor.isClosed)
            cursor.close()

        return students
    }

    fun getProjectGroupsByStudentId(studentId : Long) : ArrayList<ProjectGroup> {
        var projectGroups = ArrayList<ProjectGroup>()

        var query = "select * from " + ProjectGroupSchema.PROJECT_GROUP_TABLE +
                " inner join " + MembershipSchema.MEMBERSHIP_TABLE + " on " +
                MembershipSchema.MEMBERSHIP_TABLE+"."+MembershipSchema.COLUMN_PROJECT_GROUP_ID +
                "=" + ProjectGroupSchema.PROJECT_GROUP_TABLE+"."+ProjectGroupSchema.COLUMN_ID +
                " where " + MembershipSchema.MEMBERSHIP_TABLE+"."+MembershipSchema.COLUMN_STUDENT_ID +
                "=" + studentId.toString() + ";"

        val cursor = database.rawQuery(query, null)

        if(cursor.moveToFirst())
            do {
                var projectGroup = ProjectGroup()
                projectGroup.id = cursor.getLong(0)
                projectGroup.name = cursor.getString(1)
                projectGroups.add(projectGroup)
            } while(cursor.move(1))

        if(!cursor.isClosed)
            cursor.close()

        return projectGroups
    }

    override fun get(id: Long): Membership? {
        var membership : Membership? = null

        val cursor = database.query(MembershipSchema.MEMBERSHIP_TABLE, MembershipSchema.MEMBERSHIP_COLUMNS,
                BaseColumns._ID + " = ?", arrayOf(id.toString()), null, null,
                null, "1")

        if(cursor.moveToFirst())
            membership = map(cursor)

        if(!cursor.isClosed)
            cursor.close()

        return membership
    }



    override fun getAll(): ArrayList<Membership> {

        var memberships = ArrayList<Membership>()

        val cursor = database.query(MembershipSchema.MEMBERSHIP_TABLE, MembershipSchema.MEMBERSHIP_COLUMNS,
                null, null, null, null,
                null, null)

        if(cursor.moveToFirst())
            do {
                memberships.add(map(cursor))
            } while(cursor.move(1))

        if(!cursor.isClosed)
            cursor.close()

        return memberships
    }

    override fun save(obj: Membership) : Long {
        insertStatement.clearBindings()
        insertStatement.bindLong(1, obj.studentId!!)
        insertStatement.bindLong(2, obj.projectGroupId!!)

        return insertStatement.executeInsert()
    }

    override fun update(obj: Membership) : Int {
        val contentValues = ContentValues()
        contentValues.put(MembershipSchema.COLUMN_STUDENT_ID, obj.studentId)
        contentValues.put(MembershipSchema.COLUMN_PROJECT_GROUP_ID, obj.projectGroupId)

        return database.update(MembershipSchema.MEMBERSHIP_TABLE, contentValues, BaseColumns._ID + " = ?",
                arrayOf(obj.id.toString()))
    }

    override fun delete(obj: Membership) : Int {
        return database.delete(MembershipSchema.MEMBERSHIP_TABLE, MembershipSchema.COLUMN_STUDENT_ID
                + " = ? and " + MembershipSchema.COLUMN_PROJECT_GROUP_ID + " = ?",
                arrayOf(obj.studentId.toString(), obj.projectGroupId.toString()))
    }

    private fun map(cursor : Cursor) : Membership {
        var membership : Membership = Membership()

        membership.studentId = cursor.getLong(0)
        membership.projectGroupId = cursor.getLong(1)
        membership.id = membership.studentId

        return membership
    }

    fun exists(membership: Membership): Boolean {
        var result = false

        val cursor = database.query(MembershipSchema.MEMBERSHIP_TABLE, MembershipSchema.MEMBERSHIP_COLUMNS,
                MembershipSchema.COLUMN_STUDENT_ID + " = ? and " + MembershipSchema.COLUMN_PROJECT_GROUP_ID
                + " = ?", arrayOf(membership.studentId.toString(), membership.projectGroupId.toString()),
                null, null, null, "1")

        if(cursor.moveToFirst())
            result = true

        if(!cursor.isClosed)
            cursor.close()

        return result
    }


}