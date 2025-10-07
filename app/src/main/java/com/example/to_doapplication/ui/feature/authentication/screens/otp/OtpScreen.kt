package com.example.to_doapplication.ui.feature.authentication.screens.otp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.to_doapplication.R
import com.example.to_doapplication.model.AppMessage
import com.example.to_doapplication.ui.components.ButtonComponent
import com.example.to_doapplication.ui.components.TextFieldComponent
import com.example.to_doapplication.ui.feature.authentication.AuthState
import com.example.to_doapplication.ui.feature.authentication.screens.login.LoginViewModel
import com.example.to_doapplication.ui.navigation.Routes
import com.example.to_doapplication.viewmodel.MessageViewModel

@Composable
fun OtpScreen(
    navHostController: NavHostController,
    loginViewModel: LoginViewModel,
    messageViewModel: MessageViewModel
) {
    var otp by remember { mutableStateOf("") }
    val uiState by loginViewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState) {
        when(uiState.status) {
            is AuthState.OtpVerified -> {
                messageViewModel.showMessage(AppMessage(text = "Email verified successfully"))
                navHostController.navigate(Routes.ProfileScreen) {
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
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Please enter the OTP sent to your email",
            fontSize = 16.sp,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(18.dp))
        TextFieldComponent(
            value = otp,
            labelText = "OTP",
            onValueChange = {
                if(it.all { char -> char.isDigit() }) {
                    otp = it
                }
            },
            leadingIconVector = Icons.Default.VerifiedUser,
            leadingIconDescription = "Verify User"
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Resend OTP",
            fontSize = 14.sp,
            color = colorResource(R.color.brand_color),
            textDecoration = TextDecoration.Underline,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.End)
                .clickable {
                    loginViewModel.sendOtp()
                    otp = ""
                    messageViewModel.showMessage(AppMessage(text = "OTP sent successfully"))
                }
        )
        Spacer(modifier = Modifier.height(8.dp))
        ButtonComponent(
            text = "Verify",
            onClick = {
                loginViewModel.verifyOtp(otp)
            }
        )
        if(uiState.status is AuthState.Loading) {
            Spacer(modifier = Modifier.height(18.dp))
            CircularProgressIndicator(color = colorResource(R.color.brand_color))
        }
    }
}