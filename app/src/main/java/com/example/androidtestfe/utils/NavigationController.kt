package com.example.androidtestfe.utils

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.androidtestfe.ui.admin.AdminScreen
import com.example.androidtestfe.ui.auth.AuthScreen
import com.example.androidtestfe.ui.lists.ListScreen

sealed class Screen(val route: String) {
    data object Auth : Screen("Auth")
    data object List : Screen("List")
    data object Admin : Screen("Admin")
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
        composable(
            route = "${Screen.Admin.route}/{id}/{username}/{email}/{role}",
            arguments = listOf(
                navArgument("id") { type = NavType.IntType },
                navArgument("username") { type = NavType.StringType },
                navArgument("email") { type = NavType.StringType },
                navArgument("role") { type = NavType.StringType }
            )
        ) { navBackStackEntry ->
            val id = navBackStackEntry.arguments?.getInt("id")
            val username = navBackStackEntry.arguments?.getString("username")
            val email = navBackStackEntry.arguments?.getString("email")
            val role = navBackStackEntry.arguments?.getString("role")

            AdminScreen(
                id = id ?: 0,
                username = username ?: "",
                email = email ?: "",
                role = role ?: "",
                navController = navController
            )
        }
    }
}
