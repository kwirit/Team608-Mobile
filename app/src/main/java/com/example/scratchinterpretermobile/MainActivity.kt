package com.example.scratchinterpretermobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.scratchinterpretermobile.ui.theme.AppTheme
import com.example.scratchinterpretermobile.View.MainScreen
import com.example.scratchinterpretermobile.View.Storage.StorageManager
import com.example.scratchinterpretermobile.View.ViewModels.MainViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StorageManager.init(this)
        val mainViewModel: MainViewModel by viewModels()
        setContent {
            AppTheme(){
               MainScreen(mainViewModel)
           }
        }
    }
}

