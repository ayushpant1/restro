package com.retro.retroapp.ui.screens.activities

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.retro.retroapp.ui.screens.activities.drawermenu.BottomDrawerUI
import com.retro.retroapp.ui.screens.activities.drawermenu.CustomAppBar
import com.retro.retroapp.ui.screens.activities.drawermenu.SideDrawerUI
import com.retro.retroapp.ui.screens.activities.drawermenu.singleTopNavigator
import com.retro.retroapp.ui.utils.networkconnection.networkconnection.ConnectionState
import com.retro.retroapp.ui.utils.networkconnection.networkconnection.connectivityState
import com.retro.retroapp.ui.theme.FloatingActionBackground
import com.retro.retroapp.ui.theme.cornerRadius
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val mainViewModel = hiltViewModel<MainActivityViewModel>()
    val navController = rememberNavController()
    val isAppBarVisible = remember { mutableStateOf(true) }
    val searchProgressBar = remember { mutableStateOf(false) }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val connection by connectivityState()
    val isConnected = connection === ConnectionState.Available
    val isFavoriteActive = remember { mutableStateOf(false) }
    val pagerState = rememberPagerState { 2 }
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    val openDrawer: () -> Unit = {
        coroutineScope.launch { drawerState.open() }
    }
    // ModalNavigationDrawer is outside the content Box to overlay on top of everything
    ModalNavigationDrawer(drawerState = drawerState, // Pass drawerState to ModalDrawer
        drawerContent = {
            val items = listOf(Screen.Profile, Screen.Setting, Screen.About)
            ModalDrawerSheet {
                SideDrawerUI(menus = items,
                    navController = navController,
                    onNavigate = { route ->
                        coroutineScope.launch { drawerState.close() }
                        navController.singleTopNavigator(route)
                    })
            }
        }) {
        Scaffold(topBar = {
            CustomAppBar(
                drawerState = drawerState,
                title = navigationTitle(navController), // Custom title
            )
        }, floatingActionButton = {
            FloatingActionButton(modifier = Modifier.cornerRadius(30),
                containerColor = FloatingActionBackground,
                onClick = {
                    isAppBarVisible.value = false
                }) {
                Icon(Icons.Filled.Search, "", tint = Color.White)
            }
        }, bottomBar = {
            when (currentRoute(navController)) {
                Screen.Home.route,
                Screen.Profile.route,
                Screen.Setting.route,
                Screen.About.route,
                    -> {
                    BottomDrawerUI(navController, pagerState)
                }
            }
        }, snackbarHost = {
            if (isConnected.not()) {
                Snackbar(
                    action = {}, modifier = Modifier.padding(8.dp)
                ) {
                    Text(text = "There is no internet!")
                }
            }
        }) { paddingValues ->
            // Main content of the screen goes inside this Box and should have padding to adjust for drawer
            Box(modifier = Modifier.padding(paddingValues)) {
                MainView(navController, pagerState)
            }
        }
    }

}

@Composable
fun MainView(
    navigator: NavHostController,
    pagerState: PagerState,
) {
    Column {
        HorizontalPager(
            state = pagerState, modifier = Modifier.fillMaxSize(), userScrollEnabled = false
        ) {
            Navigation(navigator)
        }
    }
}


