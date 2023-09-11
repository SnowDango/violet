package com.snowdango.violet.presenter

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.snowdango.violet.presenter.album.AlbumScreen
import com.snowdango.violet.presenter.history.HistoryScreen
import org.koin.androidx.compose.getViewModel


enum class NavigationGraph(val route: String) {
    History(route = "history"),
    Album(route = "album")
}

@Composable
fun NavigationScreen(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = NavigationGraph.History.route,
    ) {
        composable(route = NavigationGraph.History.route) {
            HistoryScreen(viewModel = getViewModel())
        }
        composable(route = NavigationGraph.Album.route) {
            AlbumScreen() {

            }
        }
    }
}
