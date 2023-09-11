package com.snowdango.violet.presenter

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Album
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen() {

    val navController = rememberNavController()
    val bottomNavList: List<BottomNavigationGraph> = listOf(
        BottomNavigationGraph.History,
        BottomNavigationGraph.Album
    )

    Scaffold(
        bottomBar = {
            BottomNavigation(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                bottomNavList.forEach {
                    BottomNavigationItem(
                        icon = { Icon(it.icon, contentDescription = it.name) },
                        label = { Text(text = it.title) },
                        alwaysShowLabel = true,
                        selected = currentRoute == it.navigationGraph.route,
                        onClick = {
                            navController.navigate(it.navigationGraph.route) {
                                popUpTo(navController.graph.startDestinationId)
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) {
        NavigationScreen(navController)
    }
}

enum class BottomNavigationGraph(
    val navigationGraph: NavigationGraph,
    val icon: ImageVector,
    val title: String
) {
    History(NavigationGraph.History, Icons.Filled.History, "History"),
    Album(NavigationGraph.Album, Icons.Filled.Album, "Album")
}



