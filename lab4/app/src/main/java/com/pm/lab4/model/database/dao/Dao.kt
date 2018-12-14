package com.pm.lab4.model.database.dao

import com.pm.lab4.model.entities.Student
import java.util.*

interface Dao<T> {

    fun get(id : Long) : T?

    fun getAll() : List<T>

    fun save(obj : T): Long

    fun update(obj : T): Int

    fun delete(obj: T): Int

}