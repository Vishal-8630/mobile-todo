package com.example.to_doapplication.ui.feature.navbar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.to_doapplication.R
import com.example.to_doapplication.ui.navigation.Routes
import com.example.to_doapplication.ui.navigation.routeMap

@Composable
fun Navbar(
    navHostController: NavHostController,
    onNavigate: (Routes) -> Unit
) {
    val currentRoute = navHostController.currentBackStackEntryAsState().value?.destination?.route
    val currentRouteName = currentRoute?.substringAfterLast(".")
    val selectedRoute = routeMap[currentRouteName]

    Column(
        modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
    ) {
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
        Row() {
            NavbarItem(
                imageVector = Icons.Default.Home,
                contentDescription = "Home Icon",
                text = stringResource(R.string.home),
                isActive = selectedRoute == Routes.HomeScreen,
                onClick = { onNavigate(Routes.HomeScreen) }
            )
            NavbarItem(
                imageVector = Icons.Default.Add,
                contentDescription = "Add Icon",
                text = stringResource(R.string.add),
                isActive = selectedRoute == Routes.AddToDoScreen,
                onClick = { onNavigate(Routes.AddToDoScreen) }
            )
            NavbarItem(
                imageVector = Icons.Default.Person,
                contentDescription = "User Icon",
                text = stringResource(R.string.users),
                isActive = selectedRoute == Routes.UserScreen,
                onClick = { onNavigate(Routes.UserScreen) }
            )
            NavbarItem(
                imageVector = Icons.Default.AccountBox,
                contentDescription = "Profile Icon",
                text = stringResource(R.string.profile),
                isActive = selectedRoute == Routes.ProfileScreen,
                onClick = { onNavigate(Routes.ProfileScreen) }
            )
        }
    }
}