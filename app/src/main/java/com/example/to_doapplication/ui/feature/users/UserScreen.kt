package com.example.to_doapplication.ui.feature.users

import androidx.compose.foundation.layout.Column
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
import com.example.to_doapplication.ui.components.LoadingComponent
import com.example.to_doapplication.ui.components.TopBarComponent
import com.example.to_doapplication.ui.components.UserItemCard
import com.example.to_doapplication.ui.feature.navbar.Navbar

@Composable
fun UserScreen(
    navHostController: NavHostController,
    userViewModel: UserViewModel
) {
    val users by userViewModel.users.collectAsStateWithLifecycle()
    val isLoading by userViewModel.isLoading.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        userViewModel.getAllUsers()
    }

    Scaffold(
        topBar = { TopBarComponent(stringResource(R.string.users)) },
        bottomBar = { Navbar(navHostController, onNavigate = { navHostController.navigate(it) }) },
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.padding(it).padding(20.dp)
        ) {
            when {
                isLoading -> {
                    LoadingComponent()
                }
                users.isEmpty() -> {
                    Text(
                        text = "No user found",
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
                    LazyColumn {
                        items(users) { user ->
                            UserItemCard(user)
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
            }
        }
    }
}