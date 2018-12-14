package com.pm.lab4.model.database.schema

interface ProjectGroupSchema {

    companion object {
        val PROJECT_GROUP_TABLE = "project_groups"
        val COLUMN_ID = "_id"
        val COLUMN_NAME = "name"
        val PROJECT_GROUP_TABLE_CREATE = ("CREATE TABLE IF NOT EXISTS "
                + PROJECT_GROUP_TABLE
                + " ("
                + COLUMN_ID
                + " INTEGER PRIMARY KEY, "
                + COLUMN_NAME
                + " TEXT NOT NULL"
                + ")")

        val PROJECT_GROUP_COLUMNS = arrayOf(COLUMN_ID, COLUMN_NAME)
        val DELETE_PROJECT_GROUP_TABLE = "DROP TABLE IF EXISTS" + PROJECT_GROUP_TABLE
    }
}