package com.retro.retroapp.ui.screens.activities

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.retro.retroapp.R
import com.retro.retroapp.ui.screens.activities.screen.AboutScreen
import com.retro.retroapp.ui.screens.activities.screen.HomeScreen
import com.retro.retroapp.ui.screens.activities.screen.ProfileScreen
import com.retro.retroapp.ui.screens.activities.screen.SettingsScreen

@Composable
fun Navigation(
    navController: NavHostController,
) {
    NavHost(navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) { HomeScreen(navController = navController) }
        composable(Screen.Profile.route) { ProfileScreen(navController = navController) }
        composable(Screen.Setting.route) { SettingsScreen(navController = navController) }
        composable(Screen.About.route) { AboutScreen(navController = navController) }
    }
}

@Composable
fun navigationTitle(navController: NavController): String {
    return when (currentRoute(navController)) {
        Screen.Home.route -> stringResource(R.string.home)
        Screen.Profile.route -> stringResource(R.string.profile)
        Screen.Setting.route -> stringResource(R.string.setting)
        Screen.About.route -> stringResource(R.string.about)
        else -> {
           "Restro"
        }
    }
}

@Composable
fun currentRoute(navController: NavController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route?.substringBeforeLast("/")
}
