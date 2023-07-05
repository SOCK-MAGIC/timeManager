package com.setruth.timemanager

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.setruth.timemanager.ui.navigation.NavigationPage
import com.setruth.timemanager.ui.screen.start.StartPageView
import com.setruth.timemanager.ui.theme.TimeManagerTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlin.system.exitProcess

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    sealed class Route(val route: String) {
        object Start : Route("start")
        object Main : Route("main")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TimeManagerTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = Route.Start.route) {
                    composable(Route.Start.route) {
                        StartPageView(navController)
                    }
                    composable(Route.Main.route) {
                        NavigationPage()
                    }
                }
            }
            var firstTime by remember {
                mutableStateOf(0L)
            }
            BackHandler {
                val secondTime = System.currentTimeMillis()
                if (secondTime - firstTime < 2000) {
                    exitProcess(0)
                } else {
                    Toast.makeText(applicationContext, "再按一次返回键退出", Toast.LENGTH_SHORT).show()
                    firstTime = System.currentTimeMillis()
                }
            }
        }
    }
}
