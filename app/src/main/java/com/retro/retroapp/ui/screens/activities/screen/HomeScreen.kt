package com.retro.retroapp.ui.screens.activities.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.retro.retroapp.ui.screens.activities.Screen

@Composable
fun HomeScreen(navController: NavHostController) {

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "Home Screen",
            modifier = Modifier.align(Alignment.Center)
        )
        Button(onClick = {
            navController.navigate(Screen.Profile.route)
        }) {
            Text("Go to Home Screen")
        }
    }
}
