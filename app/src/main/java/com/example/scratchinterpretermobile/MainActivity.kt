package com.example.scratchinterpretermobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.scratchinterpretermobile.ui.theme.AppTheme
import com.example.scratchinterpretermobile.View.MainScreen
import com.example.scratchinterpretermobile.View.ViewModels.MainViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        val mainViewModel: MainViewModel by viewModels()
        setContent {
            var darkTheme = remember { mutableStateOf(true) }
            AppTheme(darkTheme = darkTheme.value) {
                MainScreen(mainViewModel)
            }
        }
    }
}

