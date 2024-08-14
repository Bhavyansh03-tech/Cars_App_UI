package com.example.cars_app_ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.example.cars_app_ui.mainScreen.HomeScreen
import com.example.cars_app_ui.topBar.Pager
import com.example.cars_app_ui.topBar.TopBar
import com.example.cars_app_ui.ui.theme.Blur
import com.example.cars_app_ui.ui.theme.CarsAppUITheme
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.hazeChild

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CarsAppUITheme {

                // Creating a top bar blur :->
                val hazeState = remember {
                    HazeState()
                }

                val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(
                    state = rememberTopAppBarState()
                )

                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .nestedScroll(scrollBehavior.nestedScrollConnection)
                        .background(MaterialTheme.colorScheme.background),
                    containerColor = Color.Transparent,
                    topBar = {
                        Column {
                            TopBar(
                                modifier = Modifier.hazeChild(
                                    state = remember { HazeState() },
                                    style = HazeStyle(
                                        blurRadius = 13.dp,
                                        tint = Blur,
                                        backgroundColor = Blur
                                    )
                                ),
                                scrollBehavior = scrollBehavior
                            )

                            Pager(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .hazeChild(state = hazeState)
                            )
                        }
                    }
                ) { innerPadding ->
                    HomeScreen(
                        modifier = Modifier.fillMaxSize(),
                        hazeState = hazeState,
                        paddingValues = innerPadding
                    )
                }
            }
        }
    }
    private fun enableEdgeToEdge() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, window.decorView).let { controller ->
            controller.hide(WindowInsetsCompat.Type.navigationBars())
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }

        // Set status bar color to transparent
        window.statusBarColor = Color.Transparent.toArgb()
    }

}