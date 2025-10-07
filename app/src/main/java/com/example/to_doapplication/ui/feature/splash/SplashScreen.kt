package com.example.to_doapplication.ui.feature.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.to_doapplication.R
import com.example.to_doapplication.ui.components.ButtonComponent
import com.example.to_doapplication.ui.navigation.Routes

@Composable
fun SplashScreen(
    navHostController: NavHostController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 20.dp, top = 100.dp, end = 20.dp, bottom = 50.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column() {
            Image(
                painter = painterResource(R.drawable.splash_img),
                contentDescription = null
            )
            Text(
                text = "Create you own To-Do List",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
        ButtonComponent(
            text = stringResource(R.string.get_started),
            onClick = { navHostController.navigate(Routes.LoginScreen) },
        )
    }
}