package com.example.to_doapplication.ui.feature.home

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.to_doapplication.R
import com.example.to_doapplication.model.ToDo
import com.example.to_doapplication.ui.components.LoadingComponent
import com.example.to_doapplication.ui.components.ToDoItemCard
import com.example.to_doapplication.ui.components.TopBarComponent
import com.example.to_doapplication.ui.feature.navbar.Navbar
import com.example.to_doapplication.ui.feature.todo.ToDoViewModel
import com.example.to_doapplication.viewmodel.MessageViewModel
import kotlin.time.Duration.Companion.seconds

@Composable
fun HomeScreen(
    navHostController: NavHostController,
    toDoViewModel: ToDoViewModel,
    messageViewModel: MessageViewModel
) {
    val toDos by toDoViewModel.todos.collectAsStateWithLifecycle()
    val isLoading by toDoViewModel.isLoading.collectAsStateWithLifecycle()
    val sortedToDos: List<ToDo> = toDos.sortedWith(
        compareBy<ToDo> { it.status == "Completed" } // completed last
            .thenByDescending { it.updatedAt.seconds  } // recent first
    )

    LaunchedEffect(Unit) {
        toDoViewModel.getAllToDoByUser()
    }

    Scaffold(
        topBar = { TopBarComponent(stringResource(R.string.to_do_list)) },
        bottomBar = {
            Navbar(navHostController, onNavigate = { navHostController.navigate(it) })
        },
        modifier = Modifier.fillMaxSize()
    ) {
        when {
            isLoading  -> { LoadingComponent() }
            toDos.isEmpty() -> {
                Text(
                    text = "No To-Dos found",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(it)
                        .padding(20.dp),
                    textAlign = TextAlign.Center
                )
            }
            else -> {
                LazyColumn(
                    modifier = Modifier
                        .padding(it)
                        .padding(16.dp)
                ) {
                    items(sortedToDos) { toDo ->
                        ToDoItemCard(
                            toDo = toDo,
                            navHostController = navHostController,
                            onStatusSelected = { status ->
                                toDoViewModel.updateToDoStatus(toDo, status)
                            }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}