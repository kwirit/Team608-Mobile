package com.example.scratchinterpretermobile.View
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.zIndex
import com.example.scratchinterpretermobile.View.Bars.BottomBar
import com.example.scratchinterpretermobile.View.Bars.TopBar
import com.example.scratchinterpretermobile.View.Dialogs.CreateBoxesDialog
import com.example.scratchinterpretermobile.View.ViewModels.MainViewModel
import com.example.scratchinterpretermobile.View.Widgets.VerticalReorderList

@Composable
fun MainScreen(viewModel: MainViewModel) {
    if (viewModel.showBoxesState.value) {
        CreateBoxesDialog(viewModel.showBoxesState, viewModel.boxes)
    }
    Box(modifier = Modifier.fillMaxSize()) {

        TopBar(viewModel, modifier = Modifier.align(Alignment.TopCenter))
        BottomBar(viewModel,modifier = Modifier.align(Alignment.BottomCenter))
        Box(
            modifier = Modifier
                .padding(top = 60.dp, bottom = 60.dp)
                .fillMaxSize()
        ) {
            when (viewModel.screenState.intValue) {
                0 -> CodeBlocksScreen(viewModel)
                1 -> ConsoleScreen()
            }
        }
    }
}


@Composable
fun CodeBlocksScreen(viewModel: MainViewModel){
    VerticalReorderList(viewModel.boxes)
}

@Composable
fun ConsoleScreen(){

}