package com.example.androidtestfe.utils

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.androidtestfe.ui.auth.AuthScreen


sealed class Screen(val route: String) {
    object Auth : Screen("Auth")
    object List : Screen("List")
}

@Composable
fun NavigationController() {
    val navController = rememberNavController()
    
    NavHost(navController = navController, startDestination = Screen.Auth.route) {
        composable(Screen.Auth.route) {
            AuthScreen(navController)
        }
        composable(Screen.List.route) {

        }
    }
}

