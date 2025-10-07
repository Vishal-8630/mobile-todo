package com.example.to_doapplication.ui.feature.profile

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.to_doapplication.R
import com.example.to_doapplication.model.AppMessage
import com.example.to_doapplication.ui.components.ButtonComponent
import com.example.to_doapplication.ui.components.LoadingComponent
import com.example.to_doapplication.ui.components.TopBarComponent
import com.example.to_doapplication.ui.feature.authentication.AuthState
import com.example.to_doapplication.ui.feature.authentication.screens.login.LoginViewModel
import com.example.to_doapplication.ui.feature.navbar.Navbar
import com.example.to_doapplication.ui.feature.todo.ToDoViewModel
import com.example.to_doapplication.ui.navigation.Routes
import com.example.to_doapplication.viewmodel.MessageViewModel

@Composable
fun ProfileScreen(
    navHostController: NavHostController,
    loginViewModel: LoginViewModel,
    messageViewModel: MessageViewModel,
    toDoViewModel: ToDoViewModel
) {
    val uiState by loginViewModel.uiState.collectAsStateWithLifecycle()
    val todos by toDoViewModel.todos.collectAsStateWithLifecycle()
    val isLoading by toDoViewModel.isLoading.collectAsStateWithLifecycle()

    val totalCompleted = todos.count { it.status.equals("Completed", ignoreCase = true) }
    val totalIncomplete = todos.count { it.status.equals("Incomplete", ignoreCase = true) }
    val totalInProgress = todos.count { it.status.equals("In Progress", ignoreCase = true) }

    LaunchedEffect(Unit) {
        toDoViewModel.getAllToDoByUser()
        loginViewModel.setCurrentUser()
        Log.d("ProfileScreen", "User: ${uiState.user}")
    }

    LaunchedEffect(uiState) {
        when(uiState.status) {
            is AuthState.LoggedOut -> {
                messageViewModel.showMessage(AppMessage(text = "Logged out successfully"))
                navHostController.navigate(Routes.LoginScreen) {
                    popUpTo(0) { inclusive = true }
                    launchSingleTop = true
                }
                loginViewModel.resetAuthState()
                loginViewModel.loginFields.clearLoginFields()
            }
            else -> {}
        }
    }

    Scaffold(
        topBar = { TopBarComponent(stringResource(R.string.profile)) },
        bottomBar = { Navbar(navHostController, onNavigate = { navHostController.navigate(it) }) },
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
    ) {
        Column(
            modifier = Modifier.padding(it).padding(horizontal = 32.dp, vertical = 12.dp)
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Welcome ${uiState.user?.username ?: "User"}",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(R.color.brand_color)
                )
                Spacer(modifier = Modifier.height(12.dp))
                HorizontalDivider()
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Your to-dos summary:",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black.copy(alpha = 0.8f)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Completed: $totalCompleted",
                    fontSize = 16.sp,
                    color = Color.Black.copy(alpha = 0.8f)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Incomplete: $totalIncomplete",
                    fontSize = 16.sp,
                    color = Color.Black.copy(alpha = 0.8f)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "In Progress: $totalInProgress",
                    fontSize = 16.sp,
                    color = Color.Black.copy(alpha = 0.8f)
                )
                Spacer(modifier = Modifier.height(12.dp))
                HorizontalDivider()
                Spacer(modifier = Modifier.height(12.dp))
                Row() {
                    uiState.user?.let {
                        if (it.isEmailVerified) {
                            Text(
                                text = "Email Verified",
                                fontSize = 16.sp,
                                color = colorResource(R.color.brand_color)
                            )
                        } else {
                            Text(
                                text = "Email Not Verified",
                                fontSize = 16.sp,
                                color = Color.Black.copy(alpha = 0.8f)
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Text(
                                text = "Click here to verify",
                                fontSize = 16.sp,
                                textDecoration = TextDecoration.Underline,
                                color = colorResource(R.color.brand_color),
                                modifier = Modifier
                                    .padding(start = 4.dp)
                                    .clickable {
                                        loginViewModel.sendOtp()
                                        messageViewModel.showMessage(AppMessage(text = "Verification OTP has been sent to your email"))
                                        navHostController.navigate(Routes.OtpScreen)
                                    }
                            )
                        }
                    }
                }
            }
            ButtonComponent(
                text = "Logout",
                onClick = { loginViewModel.logoutUser() }
            )
        }
        if (isLoading) {
            LoadingComponent()
        }
    }
}