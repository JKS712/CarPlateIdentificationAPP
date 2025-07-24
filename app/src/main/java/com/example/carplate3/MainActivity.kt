package com.example.carplate3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.carplate3.ui.camera.CameraScreen
import com.example.carplate3.ui.history.HistoryScreen
import com.example.carplate3.ui.theme.CarPlateTheme
import com.example.carplate3.viewmodel.PlateViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }
}

@Composable
fun MainScreen() {
    CarPlateTheme {
        val navController = rememberNavController()
        val viewModel: PlateViewModel = viewModel()

        Scaffold(
            bottomBar = {
                NavigationBar {
                    NavigationBarItem(
                        selected = navController.currentBackStackEntry?.destination?.route == "camera",
                        onClick = { navController.navigate("camera") {
                            popUpTo("camera") { inclusive = true }
                        }},
                        icon = { Box {} },  // 空的 Box 作為 icon
                        label = { Text("相機") }
                    )
                    NavigationBarItem(
                        selected = navController.currentBackStackEntry?.destination?.route == "history",
                        onClick = { navController.navigate("history") {
                            popUpTo("history") { inclusive = true }
                        }},
                        icon = { Box {} },
                        label = { Text("歷史記錄") }
                    )
                }
            }
        ) { paddingValues ->
            NavHost(
                navController = navController,
                startDestination = "camera",
                modifier = Modifier.padding(paddingValues)
            ) {
                composable("camera") {
                    CameraScreen(viewModel = viewModel)
                }
                composable("history") {
                    HistoryScreen(viewModel = viewModel)
                }
            }
        }
    }
}