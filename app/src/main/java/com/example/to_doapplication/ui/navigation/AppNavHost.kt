package com.example.to_doapplication.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.to_doapplication.ui.feature.authentication.screens.login.LoginViewModel
import com.example.to_doapplication.ui.feature.authentication.screens.otp.OtpScreen
import com.example.to_doapplication.ui.feature.authentication.screens.register.RegisterViewModel
import com.example.to_doapplication.ui.feature.home.HomeScreen
import com.example.to_doapplication.ui.feature.login.LoginScreen
import com.example.to_doapplication.ui.feature.profile.ProfileScreen
import com.example.to_doapplication.ui.feature.register.RegisterScreen
import com.example.to_doapplication.ui.feature.splash.SplashScreen
import com.example.to_doapplication.ui.feature.todo.ToDoViewModel
import com.example.to_doapplication.ui.feature.todo.add.AddToDoScreen
import com.example.to_doapplication.ui.feature.todo.detail.ToDoDetailScreen
import com.example.to_doapplication.ui.feature.todo.update.ToDoUpdateScreen
import com.example.to_doapplication.ui.feature.users.UserScreen
import com.example.to_doapplication.ui.feature.users.UserViewModel
import com.example.to_doapplication.viewmodel.MessageViewModel

@Composable
fun AppNavHost(
    messageViewModel: MessageViewModel
) {
    val navHostController = rememberNavController()
    val loginViewModel: LoginViewModel = hiltViewModel()
    val registerViewModel: RegisterViewModel = hiltViewModel()
    val toDoViewModel: ToDoViewModel = hiltViewModel()
    val userViewModel: UserViewModel = hiltViewModel()

    NavHost(navController = navHostController, startDestination = Routes.SplashScreen) {
        composable<Routes.SplashScreen> {
            SplashScreen(navHostController)
        }
        composable<Routes.LoginScreen> {
            LoginScreen(navHostController, loginViewModel, messageViewModel)
        }
        composable<Routes.RegisterScreen> {
            RegisterScreen(navHostController, registerViewModel, messageViewModel)
        }
        composable<Routes.HomeScreen> {
            HomeScreen(navHostController, toDoViewModel, messageViewModel)
        }
        composable<Routes.ProfileScreen> {
            ProfileScreen(navHostController, loginViewModel, messageViewModel, toDoViewModel)
        }
        composable<Routes.AddToDoScreen> {
            AddToDoScreen(navHostController, toDoViewModel, messageViewModel)
        }
        composable<Routes.UserScreen> {
            UserScreen(navHostController, userViewModel)
        }
        composable<Routes.ToDoDetailScreen> { backStackEntry ->
            val toDoId = backStackEntry.arguments?.getString("toDoId")
            ToDoDetailScreen(navHostController, toDoViewModel, messageViewModel, toDoId)
        }
        composable<Routes.ToDoUpdateScreen> { backStackEntry ->
            val toDoId = backStackEntry.arguments?.getString("toDoId")
            ToDoUpdateScreen(navHostController, toDoViewModel, messageViewModel, toDoId)
        }
        composable<Routes.OtpScreen> {
            OtpScreen(navHostController, loginViewModel, messageViewModel)
        }
    }
}