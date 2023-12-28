package com.example.todoapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {
    @Upsert
    suspend fun upsertTodo(todo: Todo)

    @Delete
    suspend fun deleteTodo(todo: Todo)

    @Update
    suspend fun updateTodo(todo: Todo)

    @Query("SELECT * FROM todo ORDER BY dateAdded")
    fun getTodosOrderdByDateAdded(): Flow<List<Todo>>


    @Query("SELECT * FROM todo ORDER BY task ASC")
    fun getTodosOrderdByTitle(): Flow<List<Todo>>
}