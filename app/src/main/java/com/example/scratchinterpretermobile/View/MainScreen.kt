package com.example.scratchinterpretermobile.View
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.scratchinterpretermobile.Model.Variable
import com.example.scratchinterpretermobile.ui.theme.Blue
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import org.burnoutcrew.reorderable.*

@Composable
fun MainScreen(viewModel: MainViewModel) {
    val showBoxesState = remember { mutableStateOf(false) }
    val listOfBoxes = remember { mutableStateListOf<ProgramBox>() }

    if(showBoxesState.value == true){
        ShowListOfBoxes(showBoxesState, viewModel, listOfBoxes)
    }

    Column {
        TopBar(showBoxesState)
        Box ( Modifier.weight(1f)){
            VerticalReorderList(listOfBoxes)
        }
        BottomBar()
    }

    if (showBoxesState.value) {
        ShowListOfBoxes(showBoxesState, viewModel, listOfBoxes)
    }
}

@Composable
fun ShowListOfBoxes(showBoxesState: MutableState<Boolean>, viewModel: MainViewModel, listOfBoxes: MutableList<ProgramBox>){
    val list = mutableListOf<Variable>()
    list.add(Variable("test1",1))
    list.add(Variable("test2",2))
    list.add(Variable("test3",3))
    list.add(Variable("test4",4))
    Dialog(
        properties = DialogProperties(
            usePlatformDefaultWidth = false),
        onDismissRequest = {showBoxesState.value = false},
    ) {
        Column(Modifier.width(320.dp).height(600.dp).padding(20.dp).background(color = Color.White, shape = RoundedCornerShape(20.dp))) {
            InitializationCard(listOfBoxes, showBoxesState)
            AssigningCard(listOfBoxes,showBoxesState,list)
            IfCard(listOfBoxes,showBoxesState)
            ConsoleCard(listOfBoxes,showBoxesState)
        }
    }
}

@Composable
fun BaseCard(name: String,onClick: () -> Unit = {},content:@Composable () -> Unit){
    Card(Modifier.fillMaxWidth().height(100.dp).padding(10.dp).clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = Blue),
    ) {
        Box(Modifier.fillMaxWidth(),contentAlignment = Alignment.Center){
            Column {Text(text = name, textAlign = TextAlign.Center)
                Row(Modifier.padding(10.dp)){
                    content()
                }
            }
        }
    }
}

@Composable
fun ListOfVar(variables: MutableList<Variable>){
    val expanded = remember { mutableStateOf(false) }
    TextButton(onClick = {expanded.value = true}) { Text("Variable")}
    DropdownMenu(expanded = expanded.value,onDismissRequest = {expanded.value = false}) {
        variables.forEach {
            variable ->
            DropdownMenuItem(onClick = {expanded.value = false}, text = {Text(text = variable.name)})
        }
    }
}

@Composable
fun BaseBox(name: String,content:@Composable () -> Unit){
    Card(Modifier.fillMaxWidth().height(100.dp).padding(10.dp),
        colors = CardDefaults.cardColors(containerColor = Blue),
    ) {
        Box(Modifier.fillMaxWidth(),contentAlignment = Alignment.Center){
            Column {Text(text = name, textAlign = TextAlign.Center)
                Row(Modifier.padding(10.dp)){
                    content()
                }
            }
        }
    }
}

@Composable
fun VerticalReorderList(list: MutableList<ProgramBox>) {
    val data by rememberUpdatedState(newValue = list)
    val state = rememberReorderableLazyListState(onMove = { from, to ->
        data.apply {
            add(to.index, removeAt(from.index))
        }
    })

    LazyColumn(
        state = state.listState,
        modifier = Modifier
            .reorderable(state)
            .detectReorderAfterLongPress(state)
            .fillMaxSize()
    ) {
        items(data, key = { it.id }) { item ->
            ReorderableItem(state, key = item) { isDragging ->
                val elevation = animateDpAsState(if (isDragging) 30.dp else 0.dp)
                Column(
                    modifier = Modifier
                        .shadow(elevation.value)
                        .fillMaxWidth()
                ) {
                    item.render()
                }
            }
        }
    }
}