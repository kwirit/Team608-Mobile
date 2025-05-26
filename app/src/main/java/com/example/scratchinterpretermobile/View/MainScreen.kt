package com.example.scratchinterpretermobile.View
import android.text.Layout
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.zIndex
import com.example.scratchinterpretermobile.R
import com.example.scratchinterpretermobile.View.Bars.BottomBar
import com.example.scratchinterpretermobile.View.Bars.TopBar
import com.example.scratchinterpretermobile.View.Boxes.ArrayInitializationBox
import com.example.scratchinterpretermobile.View.Dialogs.CreateBoxesDialog
import com.example.scratchinterpretermobile.View.ViewModels.MainViewModel
import com.example.scratchinterpretermobile.View.Widgets.VerticalReorderList

@Composable
fun MainScreen(viewModel: MainViewModel) {
    if (viewModel.showBoxesState.value) {
        CreateBoxesDialog(viewModel.showBoxesState, viewModel.boxes)
    }
    Box(modifier = Modifier.fillMaxSize()) {
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
        TopBar(viewModel, modifier = Modifier.align(Alignment.TopCenter))
        BottomBar(viewModel,modifier = Modifier.align(Alignment.BottomCenter))
    }
}


@Composable
fun CodeBlocksScreen(viewModel: MainViewModel){
    VerticalReorderList(viewModel.boxes)
    Box(modifier = Modifier.fillMaxSize().graphicsLayer(alpha = 0.7f)){
        Button(modifier = Modifier.align(Alignment.BottomCenter).padding(12.dp).size(54.dp),onClick = {
            viewModel.showBoxesState.value = true },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = Color.White),
            shape = RoundedCornerShape(16.dp),
            contentPadding = PaddingValues(0.dp)
        ) {
            Icon(painter = painterResource(R.drawable.baseline_add), contentDescription = null,modifier = Modifier.size(40.dp))
        }
    }
}

@Composable
fun ConsoleScreen(){

}