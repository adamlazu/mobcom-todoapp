package com.example.todoapp.presentation

import com.example.todoapp.data.Todo

sealed interface TodosEvent{
    object SortTodos: TodosEvent

    data class DeleteTodo(val todo: Todo):TodosEvent

    data class SaveTodo(
        val task: String,
        val desc: String
    ): TodosEvent


}
