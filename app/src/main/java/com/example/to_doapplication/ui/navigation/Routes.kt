package com.example.to_doapplication.ui.navigation

import kotlinx.serialization.Serializable

sealed class Routes {
    @Serializable
    data object SplashScreen: Routes()

    @Serializable
    data object LoginScreen: Routes()

    @Serializable
    data object RegisterScreen: Routes()

    @Serializable
    data object HomeScreen: Routes()

    @Serializable
    data object ProfileScreen: Routes()

    @Serializable
    data object AddToDoScreen: Routes()

    @Serializable
    data object UserScreen: Routes()

    @Serializable
    data class ToDoDetailScreen(val toDoId: String): Routes()

    @Serializable
    data class ToDoUpdateScreen(val toDoId: String): Routes()

    @Serializable
    data object OtpScreen: Routes()
}

val routeMap = mapOf(
    "HomeScreen" to Routes.HomeScreen,
    "AddToDoScreen" to Routes.AddToDoScreen,
    "UserScreen" to Routes.UserScreen,
    "ProfileScreen" to Routes.ProfileScreen
)