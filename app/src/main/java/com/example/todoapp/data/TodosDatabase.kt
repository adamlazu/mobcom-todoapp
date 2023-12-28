package com.example.todoapp.data

import androidx.room.Database
import androidx.room.RoomDatabase


@Database([Todo::class], version = 1)
abstract class TodosDatabase : RoomDatabase() {
    abstract val dao : TodoDao
}