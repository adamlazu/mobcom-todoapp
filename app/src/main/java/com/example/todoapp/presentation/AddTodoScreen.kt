package com.example.todoapp.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTodoScreen(
    state: TodoState,
    navController: NavController,
    onEvent: (TodosEvent) -> Unit
) {

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {

                onEvent(TodosEvent.SaveTodo(
                    task = state.task.value,
                    desc = state.desc.value
                ))
                navController.popBackStack()
            }) {

                Icon(
                    imageVector = Icons.Rounded.Check,
                    contentDescription = "Save Note"
                )

            }
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {

            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                value = state.task.value,
                onValueChange = {
                    state.task.value = it
                },
                textStyle = TextStyle(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 17.sp
                ),
                placeholder = {
                    Text(text = "Task")
                }

            )

            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                value = state.desc.value,
                onValueChange = {
                    state.desc.value = it
                },
                placeholder = {
                    Text(text = "Description")
                }

            )

        }

    }

}