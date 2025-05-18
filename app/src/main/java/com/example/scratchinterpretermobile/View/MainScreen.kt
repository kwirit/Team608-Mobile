package com.example.scratchinterpretermobile.View
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.scratchinterpretermobile.Model.Variable
import com.example.scratchinterpretermobile.ui.theme.Blue
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.zIndex
import com.example.scratchinterpretermobile.Model.mainContext
import com.example.scratchinterpretermobile.View.Bars.BottomBar
import com.example.scratchinterpretermobile.View.Bars.TopBar
import com.example.scratchinterpretermobile.View.Boxes.ProgramBox
import com.example.scratchinterpretermobile.View.Cards.AssigningCard
import com.example.scratchinterpretermobile.View.Cards.ConsoleCard
import com.example.scratchinterpretermobile.View.Cards.IfCard
import com.example.scratchinterpretermobile.View.Dialogs.CreateBoxesDialog
import com.example.scratchinterpretermobile.View.Dialogs.CustomDialog
import org.burnoutcrew.reorderable.*

@Composable
fun MainScreen(viewModel: MainViewModel) {
    val showBoxesState = remember { mutableStateOf(false) }
    val listOfBoxes = remember { mutableStateListOf<ProgramBox>() }
    val screenState = remember { mutableIntStateOf(0) }

    if(showBoxesState.value == true){
        CreateBoxesDialog(showBoxesState, viewModel, listOfBoxes)
    }

    Column {
        TopBar(showBoxesState)
        Box ( Modifier.weight(1f)){
            if(screenState.intValue == 0){
                CodeBlocksScreen(listOfBoxes)
            }
            else if (screenState.intValue == 1){
                ConsoleScreen()
            }
        }
        BottomBar(screenState)
    }

    if (showBoxesState.value) {
        CreateBoxesDialog(showBoxesState, viewModel, listOfBoxes)
    }
}


@Composable
fun ListOfVar(){
    val variables = mainContext.getListOfIntVariable();
    val expanded = remember { mutableStateOf(false) }
    TextButton(onClick = {expanded.value = true}) { Text("Variable")}
    DropdownMenu(expanded = expanded.value,onDismissRequest = {expanded.value = false}) {
        variables.forEach {
            variable ->
            DropdownMenuItem(onClick = {expanded.value = false}, text = {Text(text = variable)})
        }
    }
}

@Composable
fun CodeBlocksScreen(listOfBoxes: MutableList<ProgramBox>){
    VerticalReorderList(listOfBoxes)
}

@Composable
fun ConsoleScreen(){

}