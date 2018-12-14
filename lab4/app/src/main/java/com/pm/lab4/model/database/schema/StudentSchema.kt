package com.pm.lab4.model.database.schema
// sqllite openhelper
interface StudentSchema {

    companion object {
        val STUDENT_TABLE = "students"
        val COLUMN_ID = "_id"
        val COLUMN_FIRST_NAME = "first_name"
        val COLUMN_LAST_NAME = "last_name"
        val STUDENT_TABLE_CREATE = ("CREATE TABLE IF NOT EXISTS "
                + STUDENT_TABLE
                + " ("
                + COLUMN_ID
                + " INTEGER PRIMARY KEY, "
                + COLUMN_FIRST_NAME
                + " TEXT NOT NULL, "
                + COLUMN_LAST_NAME
                + " TEXT NOT NULL "
                + ")")

        val STUDENT_COLUMNS = arrayOf(COLUMN_ID, COLUMN_FIRST_NAME, COLUMN_LAST_NAME)
        val DELETE_STUDENT_TABLE = "DROP TABLE IF EXISTS" + STUDENT_TABLE
    }
}
