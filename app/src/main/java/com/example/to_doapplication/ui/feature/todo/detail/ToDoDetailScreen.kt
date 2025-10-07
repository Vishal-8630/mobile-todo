package com.example.to_doapplication.ui.feature.todo.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.to_doapplication.R
import com.example.to_doapplication.model.AppMessage
import com.example.to_doapplication.model.toReadableTime
import com.example.to_doapplication.ui.components.ButtonComponent
import com.example.to_doapplication.ui.components.LoadingComponent
import com.example.to_doapplication.ui.components.TopBarComponent
import com.example.to_doapplication.ui.feature.navbar.Navbar
import com.example.to_doapplication.ui.feature.todo.ToDoViewModel
import com.example.to_doapplication.ui.navigation.Routes
import com.example.to_doapplication.viewmodel.MessageViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToDoDetailScreen(
    navHostController: NavHostController,
    toDoViewModel: ToDoViewModel,
    messageViewModel: MessageViewModel,
    toDoId: String?
) {
    val todo by toDoViewModel.todo.collectAsStateWithLifecycle()
    val isLoading by toDoViewModel.isLoading.collectAsStateWithLifecycle()
    val error by toDoViewModel.error.collectAsStateWithLifecycle()

    var openDialog by remember { mutableStateOf(false) }

    toDoId?.let {
        LaunchedEffect(it) {
            toDoViewModel.getToDoById(it)
        }
    }

    Scaffold(
        topBar = { TopBarComponent(stringResource(R.string.to_do_summary)) },
        bottomBar = { Navbar(navHostController, onNavigate = { navHostController.navigate(it)}) },
        modifier = Modifier.fillMaxSize()
    ) {
        Column(modifier = Modifier.padding(it)) {
            when {
                isLoading -> {
                    LoadingComponent()
                }
                error != null -> {
                    Text(
                        text = error ?: "Unknown error",
                        color = Color.Red,
                        modifier = Modifier.padding(16.dp)
                    )
                }
                todo != null -> {
                    todo?.let { current ->
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 14.dp, vertical = 22.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                Text(
                                    text = current.title,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.weight(1f)
                                )
                                Text(
                                    text = current.updatedAt.toReadableTime(),
                                    fontSize = 14.sp,
                                    color = Color.Gray
                                )
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                            HorizontalDivider()
                            Spacer(modifier = Modifier.height(16.dp))
                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxWidth()
                                    .verticalScroll(rememberScrollState())
                            ) {
                                Text(
                                    text = current.description,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black.copy(alpha = 0.5f)
                                )
                            }
                            Column() {
                                ButtonComponent(
                                    text = stringResource(R.string.edit),
                                    onClick = {
                                        navHostController.navigate(Routes.ToDoUpdateScreen(current.id))
                                    },
                                    textColor = Color.Black.copy(alpha = 0.5f),
                                    containerColor = colorResource(R.color.background_color)
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                ButtonComponent(
                                    text = stringResource(R.string.delete),
                                    onClick = { openDialog = true }
                                )
                                if (openDialog) {
                                    AlertDialog(
                                        onDismissRequest = { openDialog = false },
                                        title = { Text(text = "Delete To-Do") },
                                        text = { Text(text = "Are you sure you want to delete this To-Do?") },
                                        confirmButton = {
                                            TextButton(
                                                onClick = {
                                                    openDialog = false
                                                    toDoViewModel.deleteToDo(todo!!)
                                                    messageViewModel.showMessage(AppMessage(text = "Deleted Successfully"))
                                                    navHostController.navigate(Routes.HomeScreen) {
                                                        popUpTo(0) { inclusive = true }
                                                    }
                                                }
                                            ) {
                                                Text(
                                                    text = "Yes"
                                                )
                                            }
                                        },
                                        dismissButton = {
                                            TextButton(
                                                onClick = { openDialog = false }
                                            ) {
                                                Text(
                                                    text = "No"
                                                )
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}