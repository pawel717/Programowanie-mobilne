package com.pm.lab4.model.entities

data class ProjectGroup (var id: Long?,
                         var name : String,
                         var membersList: ArrayList<Student>)
{
    constructor() : this(null, "", ArrayList<Student>())
}
