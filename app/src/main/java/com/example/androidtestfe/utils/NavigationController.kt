package com.example.androidtestfe.utils

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.androidtestfe.ui.auth.AuthScreen
import com.example.androidtestfe.ui.listscreen.ListScreen

sealed class Screen(val route: String) {
    data object Auth : Screen("Auth")
    data object List : Screen("List")
}

@Composable
fun NavigationController() {
    val navController = rememberNavController()
    
    NavHost(navController = navController, startDestination = Screen.Auth.route) {
        composable(Screen.Auth.route) {
            AuthScreen(navController)
        }
        composable(Screen.List.route) {
            ListScreen(navController)
        }
    }
}
