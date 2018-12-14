package com.pm.lab4.model.entities

data class Membership(var id: Long?,
                   var studentId : Long?,
                   var projectGroupId : Long?)
{
    constructor() : this(null, null, null)
}
