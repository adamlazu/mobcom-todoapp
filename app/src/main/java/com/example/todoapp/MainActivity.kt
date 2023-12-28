package com.example.todoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.todoapp.data.TodosDatabase
import com.example.todoapp.presentation.AddTodoScreen
import com.example.todoapp.presentation.TodosScreen
import com.example.todoapp.presentation.TodosViewModel
import com.example.todoapp.ui.theme.TodoappTheme

class MainActivity : ComponentActivity() {

    private val database by lazy {
        Room.databaseBuilder(
            applicationContext,
            TodosDatabase::class.java,
            "todos.db"
        ).build()
    }

    private val viewModel by viewModels<TodosViewModel> (
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun<T: ViewModel> create(modelClass: Class<T>): T {
                    return TodosViewModel(database.dao) as T
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TodoappTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val state by viewModel.state.collectAsState()
                    val navController = rememberNavController()

                    NavHost(navController= navController, startDestination = "TodosScreen") {
                        composable("TodosScreen") {
                            TodosScreen(
                                state = state,
                                navController = navController,
                                onEvent = viewModel::onEvent
                            )
                        }
                        composable("AddTodoScreen") {
                            AddTodoScreen(
                                state = state,
                                navController = navController,
                                onEvent = viewModel::onEvent
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TodoappTheme {
        Greeting("Android")
    }
}