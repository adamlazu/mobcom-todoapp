package com.example.todoapp.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.Todo
import com.example.todoapp.data.TodoDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TodosViewModel(
    private val dao: TodoDao
) :ViewModel() {
    private val isSortedByDateAdded = MutableStateFlow(true)
    private var todos =
        isSortedByDateAdded.flatMapLatest { sort ->
            if (sort) {
                dao.getTodosOrderdByDateAdded()
            } else {
                dao.getTodosOrderdByTitle()
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    val _state = MutableStateFlow(TodoState())
    val state =
        combine(_state, isSortedByDateAdded, todos) { state, isSortedByDateAdded, todos ->
            state.copy(
                todos = todos
            )
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), TodoState())

    fun onEvent(event: TodosEvent) {
        when (event) {
            is TodosEvent.DeleteTodo -> {
                viewModelScope.launch {
                    dao.deleteTodo(event.todo)
                }
            }

            is TodosEvent.SaveTodo -> {
                val todo = Todo(
                    task = state.value.task.value,
                    desc = state.value.desc.value,
                    dateAdded = System.currentTimeMillis()
                )

                viewModelScope.launch {
                    dao.upsertTodo(todo)
                }

                _state.update {
                    it.copy(
                        task = mutableStateOf(""),
                        desc = mutableStateOf("")
                    )
                }



            }

            TodosEvent.SortTodos -> {
                isSortedByDateAdded.value = !isSortedByDateAdded.value
            }


        }
    }
}