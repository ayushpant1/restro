package com.retro.retroapp.ui.screens.activities.drawermenu

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.retro.retroapp.ui.screens.activities.Screen
import com.retro.retroapp.ui.screens.activities.currentRoute

// Corrected BottomNavigationUI with PagerState
@Composable
fun BottomDrawerUI(navController: NavController, pagerState: PagerState) {
    NavigationBar {
        // Condition based on PagerState to decide which items to show
        val items = listOf(Screen.Home, Screen.Profile, Screen.Setting, Screen.About)
        items.forEachIndexed { _, item ->
            NavigationBarItem(
                icon = { item.navIcon() },
                label = { Text(text = item.title) },
                selected = currentRoute(navController) == item.route,
                onClick = {
                    navController.singleTopNavigator(item.route) // Navigating using singleTopNavigator
                }
            )
        }
    }
}


@Composable
fun SideDrawerUI(
    menus: List<Screen>, navController: NavController, onNavigate: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier.size(150.dp),
                imageVector = Icons.Filled.AccountCircle,
                contentScale = ContentScale.Crop,
                contentDescription = null
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        menus.forEachIndexed { _, item ->
            NavigationDrawerItem(
                label = { Text(text = item.title) },
                icon = {  {item.navIcon} },
                selected = false,
                onClick = {
                    onNavigate(item.route)
                }
            )
        }
    }
}




fun NavController.singleTopNavigator(route: String) {
    this.navigate(route) {
        // Check if the graph has a start destination route
        graph.startDestinationRoute?.let { startRoute ->
            popUpTo(startRoute) {
                inclusive = true
            }
        }
        launchSingleTop = true
        restoreState = true
    }
}