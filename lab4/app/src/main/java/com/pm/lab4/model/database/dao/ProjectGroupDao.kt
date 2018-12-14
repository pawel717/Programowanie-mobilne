package com.pm.lab4.model.database.dao

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteStatement
import android.provider.BaseColumns
import com.pm.lab4.model.database.schema.ProjectGroupSchema
import com.pm.lab4.model.entities.ProjectGroup
import java.util.*

class ProjectGroupDao : Dao<ProjectGroup> {
    val database : SQLiteDatabase
    val insertStatement: SQLiteStatement

    constructor(sqLiteDatabase: SQLiteDatabase)
    {
        database = sqLiteDatabase
        insertStatement = database.compileStatement(
                "insert into " + ProjectGroupSchema.PROJECT_GROUP_TABLE
                        + "(" + ProjectGroupSchema.COLUMN_NAME
                        + ") values (?)"
        )
    }

    fun getByName(name : String) : ProjectGroup? {
        var projectGroup : ProjectGroup? = null

        val cursor = database.query(ProjectGroupSchema.PROJECT_GROUP_TABLE, ProjectGroupSchema.PROJECT_GROUP_COLUMNS,
                ProjectGroupSchema.COLUMN_NAME + " = ?", arrayOf(name), null, null,
                null, "1")

        if(cursor.moveToFirst())
            projectGroup = map(cursor)

        if(!cursor.isClosed)
            cursor.close()

        return projectGroup
    }

    override fun get(id: Long): ProjectGroup? {
        var projectGroup : ProjectGroup? = null

        val cursor = database.query(ProjectGroupSchema.PROJECT_GROUP_TABLE, ProjectGroupSchema.PROJECT_GROUP_COLUMNS,
                BaseColumns._ID + " = ?", arrayOf(id.toString()), null, null,
                null, "1")

        if(cursor.moveToFirst())
            projectGroup = map(cursor)

        if(!cursor.isClosed)
            cursor.close()

        return projectGroup
    }


    override fun getAll(): ArrayList<ProjectGroup> {

        var projectGroups = ArrayList<ProjectGroup>()

        val cursor = database.query(ProjectGroupSchema.PROJECT_GROUP_TABLE, ProjectGroupSchema.PROJECT_GROUP_COLUMNS,
                null, null, null, null,
                null, null)

        if(cursor.moveToFirst())
            do {
                projectGroups.add(map(cursor))
            } while(cursor.move(1))

        if(!cursor.isClosed)
            cursor.close()

        return projectGroups
    }

    override fun save(obj: ProjectGroup) : Long {
        insertStatement.clearBindings()
        insertStatement.bindString(1, obj.name!!)

        return insertStatement.executeInsert()
    }

    override fun update(obj: ProjectGroup) : Int {
        val contentValues = ContentValues()
        contentValues.put(ProjectGroupSchema.COLUMN_NAME, obj.name)

        return database.update(ProjectGroupSchema.PROJECT_GROUP_TABLE, contentValues, BaseColumns._ID + " = ?",
                arrayOf(obj.id.toString()))
    }

    override fun delete(obj: ProjectGroup) : Int {
        return database.delete(ProjectGroupSchema.PROJECT_GROUP_TABLE, BaseColumns._ID + " = ?",
                arrayOf(obj.id.toString()))
    }

    private fun map(cursor : Cursor) : ProjectGroup {
        var projectGroup : ProjectGroup = ProjectGroup()

        projectGroup.id = cursor.getLong(0)
        projectGroup.name = cursor.getString(1)

        return projectGroup
    }
}