package com.example.to_doapplication.ui.feature.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.to_doapplication.R
import com.example.to_doapplication.model.AppMessage
import com.example.to_doapplication.ui.components.AuthNavigationTextComponent
import com.example.to_doapplication.ui.components.ButtonComponent
import com.example.to_doapplication.ui.components.TextFieldComponent
import com.example.to_doapplication.ui.components.TitleComponent
import com.example.to_doapplication.ui.feature.authentication.AuthState
import com.example.to_doapplication.ui.feature.authentication.screens.login.LoginViewModel
import com.example.to_doapplication.ui.navigation.Routes
import com.example.to_doapplication.viewmodel.MessageViewModel

@Composable
fun LoginScreen(
    navHostController: NavHostController,
    loginViewModel: LoginViewModel,
    messageViewModel: MessageViewModel
) {
    val uiState by loginViewModel.uiState.collectAsStateWithLifecycle()
    val fieldState by loginViewModel.loginFieldState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState) {
        when(uiState.status) {
            is AuthState.Success -> {
                val message = (uiState.status as AuthState.Success).message
                messageViewModel.showMessage(AppMessage(text = message))
                navHostController.navigate(Routes.HomeScreen) {
                    popUpTo(0) { inclusive = true }
                    launchSingleTop = true
                }
            }
            is AuthState.Error -> {
                val message = (uiState.status as AuthState.Error).message
                messageViewModel.showMessage(AppMessage(text = message))
                loginViewModel.resetAuthState()
            }
            else -> {}
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
            .padding(start = 20.dp, top = 20.dp, end = 20.dp, bottom = 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TitleComponent(
            stringResource(R.string.login),
            color = colorResource(R.color.brand_color)
        )
        Spacer(modifier = Modifier.height(20.dp))
        TextFieldComponent(
            value = fieldState.username,
            labelText = "Enter Username",
            onValueChange = { loginViewModel.loginFields.onUsernameChange(it) },
            leadingIconVector = Icons.Default.Person,
            leadingIconDescription = "Username Icon",
            imeAction = ImeAction.Next,
            error = fieldState.usernameError
        )
        Spacer(modifier = Modifier.height(20.dp))
        TextFieldComponent(
            value = fieldState.password,
            labelText = "Enter Password",
            onValueChange = { loginViewModel.loginFields.onPasswordChange(it) },
            leadingIconVector = Icons.Default.Lock,
            leadingIconDescription = "Password Icon",
            imeAction = ImeAction.Done,
            isPassword = true,
            error = fieldState.passwordError
        )
        Spacer(modifier = Modifier.height(30.dp))
        AuthNavigationTextComponent(
            staticText = "Don't have an account?",
            actionText = "Register",
            onClickAction = {
                loginViewModel.loginFields.clearLoginFields()
                navHostController.navigate(Routes.RegisterScreen)
            }
        )
        Spacer(modifier = Modifier.height(10.dp))
        ButtonComponent(
            text = stringResource(R.string.login),
            onClick = { loginViewModel.loginUser() }
        )
        if(uiState.status is AuthState.Loading) {
            Spacer(modifier = Modifier.height(18.dp))
            CircularProgressIndicator(color = colorResource(R.color.brand_color))
        }
    }
}