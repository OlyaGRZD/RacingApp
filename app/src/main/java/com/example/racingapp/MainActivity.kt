package com.example.racingapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.DirectionsRun
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.racingapp.ui.HistoryScreen
import com.example.racingapp.ui.RaceScreen
import com.example.racingapp.ui.theme.RacingAppTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RacingAppTheme {
                val navController = rememberNavController()
                MainScreen(navController)
            }
        }
    }
}

@Composable
fun MainScreen(navController: NavHostController) {
    val items = listOf(Screen.Race, Screen.History)
    Scaffold(
        bottomBar = {
            NavigationBar {
                val currentRoute = currentBackStackEntryAsState(navController)
                    .value?.destination?.route
                items.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(
                            screen.icon,
                            contentDescription = screen.title) },
                        label = { Text(screen.title) },
                        selected = currentRoute == screen.route,
                        onClick = {
                            if (currentRoute != screen.route) {
                                navController.navigate(screen.route) {
                                    popUpTo(Screen.Race.route) { inclusive = false }
                                    launchSingleTop = true
                                }
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Race.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Race.route) { RaceScreen() }
            composable(Screen.History.route) { HistoryScreen() }
        }
    }
}

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Race : Screen(
        "race",
        "Скачки",
        Icons.AutoMirrored.Filled.DirectionsRun)
    object History : Screen(
        "history",
        "История",
        Icons.Default.History)
}

@Composable
private fun currentBackStackEntryAsState(navController: NavHostController): State<NavBackStackEntry?> {
    val backStackEntry = remember { mutableStateOf(navController.currentBackStackEntry) }
    LaunchedEffect(navController) {
        navController.currentBackStackEntryFlow.collect {
            backStackEntry.value = it
        }
    }
    return backStackEntry
}