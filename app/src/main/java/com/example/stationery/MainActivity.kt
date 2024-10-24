package com.example.stationery

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.stationery.logic.model.SearchViewModel
import com.example.stationery.logic.model.StickyViewModel
import com.example.stationery.ui.UserSettingsScreen
import com.example.stationery.ui.homescreen.HomeScreen
import com.example.stationery.ui.theme.StationeryTheme

class MainActivity : ComponentActivity() {
    private val stickyViewModel: StickyViewModel by viewModels<StickyViewModel> { StickyViewModel.Factory }
    private val searchViewModel: SearchViewModel by viewModels<SearchViewModel> { SearchViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = androidx.navigation.compose.rememberNavController()
            StationeryTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "home", // Home screen is the start destination
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("home") {
                            HomeScreen(
                                stickyViewModel = stickyViewModel,
                                searchViewModel = searchViewModel,
                                navController = navController
                            )
                        }
                        composable("settings") {
                            UserSettingsScreen(
                                navController = navController
                            )
                        }
                    }
                }
            }
        }
    }
}