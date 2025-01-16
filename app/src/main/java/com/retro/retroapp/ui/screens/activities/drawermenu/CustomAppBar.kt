package com.retro.retroapp.ui.screens.activities.drawermenu

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.retro.retroapp.ui.theme.DefaultBackgroundColor
import com.retro.retroapp.ui.theme.PurpleGrey80
import com.retro.retroapp.ui.theme.RetroAppTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomAppBar(
    drawerState: DrawerState?,
    title: String,
) {
    val coroutineScope = rememberCoroutineScope()

    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = PurpleGrey80,
            titleContentColor = MaterialTheme.colorScheme.primary),
        title = {
            Text(
                text = title,
                color = Color.Gray
            )
        },
        navigationIcon = {
            if (drawerState != null) {
                // Hamburger menu to open the drawer
                IconButton(onClick = {
                    coroutineScope.launch {
                        drawerState.open()
                    }
                }) {
                    Icon(
                        imageVector = Icons.Filled.Menu,
                        contentDescription = "Menu",
                        tint = DefaultBackgroundColor
                    )
                }
            }
        },
        actions = {
            // Add any actions like search button or other icons here
        }
    )
}


@Preview(widthDp = 300)
@Composable
fun PreviewCustomAppBar() {
    RetroAppTheme {
        CustomAppBar(drawerState = null, title = "Title")
    }

}