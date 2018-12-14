package com.pm.lab4.model.database.dao

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.pm.lab4.model.entities.Student
import android.database.sqlite.SQLiteStatement
import android.provider.BaseColumns
import com.pm.lab4.model.database.schema.StudentSchema
import kotlin.collections.ArrayList


class StudentDao : Dao<Student> {

    val database : SQLiteDatabase
    val insertStatement: SQLiteStatement

    constructor(sqLiteDatabase: SQLiteDatabase)
    {
        database = sqLiteDatabase
        insertStatement = database.compileStatement(
            "insert into " + StudentSchema.STUDENT_TABLE
            + "(" + StudentSchema.COLUMN_FIRST_NAME
            + ", " + StudentSchema.COLUMN_LAST_NAME
            + ") values (?, ?)"
        )
    }

    override fun get(id: Long): Student? {
        var student : Student? = null

        val cursor = database.query(StudentSchema.STUDENT_TABLE, StudentSchema.STUDENT_COLUMNS,
                BaseColumns._ID + " = ?", arrayOf(id.toString()), null, null,
                null, "1")

        if(cursor.moveToFirst())
            student = map(cursor)

        if(!cursor.isClosed)
            cursor.close()

        return student
    }


    override fun getAll(): ArrayList<Student> {

        var students = ArrayList<Student>()

        val cursor = database.query(StudentSchema.STUDENT_TABLE, StudentSchema.STUDENT_COLUMNS,
                null, null, null, null,
                null, null)

        if(cursor.moveToFirst())
            do {
                students.add(map(cursor))
            } while(cursor.move(1))

        if(!cursor.isClosed)
            cursor.close()

        return students
    }

    override fun save(obj: Student) : Long {
        insertStatement.clearBindings()
        insertStatement.bindString(1, obj.firstName)
        insertStatement.bindString(2, obj.lastName)

        return insertStatement.executeInsert()
    }

    override fun update(obj: Student) : Int {
        val contentValues = ContentValues()
        contentValues.put(StudentSchema.COLUMN_FIRST_NAME, obj.firstName)
        contentValues.put(StudentSchema.COLUMN_LAST_NAME, obj.lastName)

        return database.update(StudentSchema.STUDENT_TABLE, contentValues,BaseColumns._ID + " = ?",
             arrayOf(obj.id.toString()))
    }

    override fun delete(obj: Student) : Int {
        return database.delete(StudentSchema.STUDENT_TABLE, BaseColumns._ID + " = ?",
                arrayOf(obj.id.toString()))
    }

    private fun map(cursor : Cursor) : Student {
        var student : Student = Student()

        student.id = cursor.getLong(0)
        student.firstName = cursor.getString(1)
        student.lastName = cursor.getString(2)

        return student
    }
}