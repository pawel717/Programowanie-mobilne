package com.pm.lab4.model.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.pm.lab4.model.database.schema.MembershipSchema
import com.pm.lab4.model.database.schema.ProjectGroupSchema
import com.pm.lab4.model.database.schema.StudentSchema

class DbHelper(context : Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    lateinit var membershipSchema : MembershipSchema

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(MembershipSchema.MEMBERSHIP_TABLE_CREATE)
        db.execSQL(ProjectGroupSchema.PROJECT_GROUP_TABLE_CREATE)
        db.execSQL(StudentSchema.STUDENT_TABLE_CREATE)

//        val studentValues = ContentValues()
//        studentValues.put(StudentSchema.COLUMN_FIRST_NAME, "Adam")
//        studentValues.put(StudentSchema.COLUMN_LAST_NAME, "Miałczyński")
//        db.insert(StudentSchema.STUDENT_TABLE, null, studentValues)
//        studentValues.clear()
//        studentValues.put(StudentSchema.COLUMN_FIRST_NAME, "Jan")
//        studentValues.put(StudentSchema.COLUMN_LAST_NAME, "Śpiewak")
//        db.insert(StudentSchema.STUDENT_TABLE, null, studentValues)
//
//        val groupValues = ContentValues()
//        groupValues.put(ProjectGroupSchema.COLUMN_NAME, "grupa 1")
//        db.insert(ProjectGroupSchema.PROJECT_GROUP_TABLE, null, groupValues)
//        groupValues.clear()
//        groupValues.put(ProjectGroupSchema.COLUMN_NAME, "grupa 2")
//        db.insert(ProjectGroupSchema.PROJECT_GROUP_TABLE, null, groupValues)
//        groupValues.clear()
//        groupValues.put(ProjectGroupSchema.COLUMN_NAME, "grupa 3")
//        db.insert(ProjectGroupSchema.PROJECT_GROUP_TABLE, null, groupValues)
//        groupValues.clear()
//        groupValues.put(ProjectGroupSchema.COLUMN_NAME, "grupa 4")
//        db.insert(ProjectGroupSchema.PROJECT_GROUP_TABLE, null, groupValues)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(MembershipSchema.DELETE_MEMBERSHIP_TABLE)
        db.execSQL(ProjectGroupSchema.DELETE_PROJECT_GROUP_TABLE)
        db.execSQL(StudentSchema.DELETE_STUDENT_TABLE)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    companion object {
        // If you change the database schema, you must increment the database version.
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "lab4.db"
    }

}