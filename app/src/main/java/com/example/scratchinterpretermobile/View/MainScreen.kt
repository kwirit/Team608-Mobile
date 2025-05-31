package com.example.scratchinterpretermobile.View

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.scratchinterpretermobile.View.Bars.BottomBar
import com.example.scratchinterpretermobile.View.Bars.TopBar
import com.example.scratchinterpretermobile.View.Dialogs.CreateBoxesDialog
import com.example.scratchinterpretermobile.View.Screens.CodeBlocksScreen
import com.example.scratchinterpretermobile.View.Screens.LogScreen
import com.example.scratchinterpretermobile.View.Screens.SettingsScreen
import com.example.scratchinterpretermobile.View.ViewModels.MainViewModel

@Composable
fun MainScreen(viewModel: MainViewModel, darkTheme: Boolean, onThemeChange: () -> Unit) {
    if (viewModel.showBoxesState.value) {
        CreateBoxesDialog(viewModel.showBoxesState, viewModel.boxes)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primaryContainer)
            .windowInsetsPadding(WindowInsets.safeDrawing)
    ) {
        Box(
            modifier = Modifier
                .padding(top = 60.dp, bottom = 60.dp)
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background)
        ) {
            when (viewModel.screenState.intValue) {
                0 -> CodeBlocksScreen(viewModel)
                1 -> LogScreen(viewModel)
                2 -> SettingsScreen(darkTheme, onThemeChange)
            }
        }
        TopBar(viewModel, modifier = Modifier.align(Alignment.TopCenter))
        BottomBar(viewModel, modifier = Modifier.align(Alignment.BottomCenter))
    }
}


