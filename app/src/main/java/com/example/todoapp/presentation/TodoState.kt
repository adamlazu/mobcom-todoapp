package com.example.todoapp.presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.todoapp.data.Todo

data class TodoState(
    val todos: List<Todo> = emptyList(),
    val task: MutableState<String> = mutableStateOf(""),
    val desc: MutableState<String> = mutableStateOf("")
)
