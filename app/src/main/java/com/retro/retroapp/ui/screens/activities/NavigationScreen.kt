package com.retro.retroapp.ui.screens.activities

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable

// Screen class with route, title, and navIcon
sealed class Screen(
    val route: String,
    val title: String,  // Title as a String
    val navIcon: @Composable () -> Unit  // Icon composable
) {

    // Define each screen as a simple object with route, title, and navIcon
    data object Home : Screen("home", "Home", {
        Icon(Icons.Filled.Home, contentDescription = "Home")
    })

    data object Profile : Screen("profile", "Profile", {
        Icon(Icons.Filled.Favorite, contentDescription = "Profile")
    })

    data object About : Screen("about", "About", {
        Icon(Icons.Filled.Star, contentDescription = "About")
    })

    data object Setting : Screen("setting", "Setting", {
        Icon(Icons.Filled.Home, contentDescription = "Setting")
    })
}
