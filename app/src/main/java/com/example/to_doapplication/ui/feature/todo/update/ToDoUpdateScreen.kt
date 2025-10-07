package com.example.to_doapplication.ui.feature.todo.update

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.to_doapplication.R
import com.example.to_doapplication.model.AppMessage
import com.example.to_doapplication.ui.components.ButtonComponent
import com.example.to_doapplication.ui.components.TopBarComponent
import com.example.to_doapplication.ui.feature.navbar.Navbar
import com.example.to_doapplication.ui.feature.todo.ToDoViewModel
import com.example.to_doapplication.ui.navigation.Routes
import com.example.to_doapplication.viewmodel.MessageViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToDoUpdateScreen(
    navHostController: NavHostController,
    toDoViewModel: ToDoViewModel,
    messageViewModel: MessageViewModel,
    toDoId: String?
) {
    val fieldState by toDoViewModel.toDoFieldState.collectAsStateWithLifecycle()
    val isLoading by toDoViewModel.isLoading.collectAsStateWithLifecycle()
    val error by toDoViewModel.error.collectAsStateWithLifecycle()
    val success by toDoViewModel.success.collectAsStateWithLifecycle()
    val todo by toDoViewModel.todo.collectAsStateWithLifecycle()

    var showCancelDialog by remember { mutableStateOf(false) }
    var pendingRoute by remember { mutableStateOf<Routes?>(null) }

    toDoId?.let {
        LaunchedEffect(it) {
            toDoViewModel.getToDoById(toDoId)
        }
    }

    LaunchedEffect(todo) {
        todo?.let {
            toDoViewModel.toDoFields.onTitleChange(it.title)
            toDoViewModel.toDoFields.onDescriptionChange(it.description)
        }
    }

    LaunchedEffect(error) {
        error?.let {
            messageViewModel.showMessage(AppMessage(text = it))
            toDoViewModel.clearError()
        }
    }

    LaunchedEffect(success) {
        success?.let {
            messageViewModel.showMessage(AppMessage(text = it))
            toDoViewModel.clearSuccess()
            navHostController.navigate(Routes.HomeScreen) {
                popUpTo(0) { inclusive = true }
                launchSingleTop = true
            }
        }
    }

    Scaffold(
        topBar = { TopBarComponent(stringResource(R.string.add_to_do)) },
        bottomBar = {
            Navbar(navHostController, onNavigate = {
                if (toDoViewModel.hasUnsavedChanges()) {
                    pendingRoute = it
                    showCancelDialog = true
                } else {
                    navHostController.navigate(it)
                }
            })
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(horizontal = 20.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 50.dp)
            ) {
                OutlinedTextField(
                    value = fieldState.title,
                    onValueChange = { toDoViewModel.toDoFields.onTitleChange(it) },
                    modifier = Modifier
                        .fillMaxWidth(),
                    label = { Text("Title") },
                    singleLine = true,
                    isError = fieldState.titleError != null
                )
                if (fieldState.titleError != null) {
                    Text(
                        text = fieldState.titleError ?: "",
                        color = Color.Red,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = fieldState.description,
                    onValueChange = { toDoViewModel.toDoFields.onDescriptionChange(it) },
                    label = { Text("Description") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp),
                    singleLine = false,
                    isError = fieldState.descriptionError != null
                )
                if (fieldState.descriptionError != null) {
                    Text(
                        text = fieldState.descriptionError ?: "",
                        color = Color.Red,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            ButtonComponent(
                text = stringResource(R.string.cancel),
                textColor = Color.Black.copy(alpha = 0.5f),
                containerColor = colorResource(R.color.background_color),
                onClick = {
                    if(toDoViewModel.hasUnsavedChanges()) {
                        showCancelDialog = true
                    } else {
                        navHostController.popBackStack()
                    }
                }
            )
            Spacer(modifier = Modifier.height(2.dp))
            ButtonComponent(
                text = stringResource(R.string.update),
                onClick = {
                    if(!toDoViewModel.hasUnsavedChanges()) {
                        messageViewModel.showMessage(AppMessage(text = "No changes to save"))
                    } else {
                        toDoId?.let {
                            toDoViewModel.updateToDo(it)
                        }
                    }
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White.copy(alpha = 0.7f)),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Updating...",
                        fontSize = 32.sp,
                        color = colorResource(R.color.brand_color)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    CircularProgressIndicator(
                        color = colorResource(R.color.brand_color)
                    )
                }
            }
        }
        if(showCancelDialog) {
            AlertDialog(
                onDismissRequest = { showCancelDialog = false },
                title = { Text(text = "Discard changes?") },
                text = { Text(text = "You have unsaved changes. Do you really want to discard them?") },
                confirmButton = {
                    TextButton(
                        onClick = {
                            showCancelDialog = false
                            pendingRoute?.let { route ->
                                navHostController.navigate(route) {
                                    launchSingleTop = true
                                    popUpTo(0)
                                }
                                pendingRoute = null
                            } ?: run {
                                navHostController.popBackStack()
                            }
                        }
                    ) {
                        Text(text = "Yes")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = { showCancelDialog = false }
                    ) {
                        Text(text = "No")
                    }
                }
            )
        }
    }
}