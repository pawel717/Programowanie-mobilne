package com.pm.lab4.model.database.schema

interface MembershipSchema {

    companion object {
        val MEMBERSHIP_TABLE = "memberships"
        val COLUMN_STUDENT_ID = "student_id"
        val COLUMN_PROJECT_GROUP_ID = "project_group_id"
        val MEMBERSHIP_TABLE_CREATE = ("CREATE TABLE IF NOT EXISTS "
                + MEMBERSHIP_TABLE
                + " (" + COLUMN_STUDENT_ID + " INTEGER, "
                + COLUMN_PROJECT_GROUP_ID + " INTEGER, "
                + "PRIMARY KEY(" + COLUMN_STUDENT_ID + ", " + COLUMN_PROJECT_GROUP_ID + "), "
                + "FOREIGN KEY(" + COLUMN_STUDENT_ID + ") REFERENCES "
                + StudentSchema.STUDENT_TABLE + "(" + StudentSchema.COLUMN_ID + "), "
                + "FOREIGN KEY(" + COLUMN_PROJECT_GROUP_ID + ") REFERENCES "
                + ProjectGroupSchema.PROJECT_GROUP_TABLE + "(" + ProjectGroupSchema.COLUMN_ID + ")"
                + ")")

        val MEMBERSHIP_COLUMNS = arrayOf(COLUMN_STUDENT_ID, COLUMN_PROJECT_GROUP_ID)

        val DELETE_MEMBERSHIP_TABLE = "DROP TABLE IF EXISTS" + MEMBERSHIP_TABLE
    }
}