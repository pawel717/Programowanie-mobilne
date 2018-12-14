package com.pm.lab4.model.entities

data class Student(var id: Long?,
                var firstName : String,
                var lastName : String,
                var projectGroupList : ArrayList<ProjectGroup>)
{
    constructor() : this(null, "", "", ArrayList<ProjectGroup>())
}
