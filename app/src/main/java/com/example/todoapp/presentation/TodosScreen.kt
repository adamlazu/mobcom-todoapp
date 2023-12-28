package com.example.todoapp.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Sort
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.todoapp.R


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodosScreen(
    state: TodoState,
    navController: NavController,
    onEvent: (TodosEvent)-> Unit
){

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
                    .background(color = Color.Blue)
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = stringResource(id = R.string.app_name),
                    modifier = Modifier.weight(1f),
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onPrimary
                )

                IconButton(onClick = { onEvent(TodosEvent.SortTodos) }) {
                    Icon(
                        imageVector = Icons.Rounded.Sort,
                        contentDescription = "Sort Notes",
                        modifier = Modifier.size(35.dp),
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }

            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                state.task.value = ""
                state.desc.value = ""
                navController.navigate("AddTodoScreen")
            }) {
                Icon(imageVector = Icons.Rounded.Add, contentDescription = "Add new note")
            }
        }
    ) {
            paddingValues ->

        LazyColumn(
            contentPadding = paddingValues,
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            items(state.todos.size) { index ->

                TodoItem(
                    state = state,
                    index = index,
                    onEvent = onEvent
                )
            }

        }

    }
}

@Composable
fun TodoItem(
    state: TodoState,
    index: Int,
    onEvent: (TodosEvent) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(12.dp)
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {

            Text(
                text = state.todos[index].task,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = state.todos[index].desc,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )

        }

        IconButton(
            onClick = {
                onEvent(TodosEvent.DeleteTodo(state.todos[index]))
            }
        ) {

            Icon(
                imageVector = Icons.Rounded.Delete,
                contentDescription = "Delete Note",
                modifier = Modifier.size(35.dp),
                tint = MaterialTheme.colorScheme.onPrimaryContainer
            )

        }
        

    }
}