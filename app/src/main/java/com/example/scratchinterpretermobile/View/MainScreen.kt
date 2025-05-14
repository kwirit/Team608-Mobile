package com.example.scratchinterpretermobile.View
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.Surface
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import com.example.scratchinterpretermobile.R
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.scratchinterpretermobile.Model.Variable
import com.example.scratchinterpretermobile.View.DraggedBlock.Initialization
import com.example.scratchinterpretermobile.ui.theme.Blue
import com.example.scratchinterpretermobile.ui.theme.LightOrange
import com.example.scratchinterpretermobile.ui.theme.Orange

@Composable
fun MainScreen(viewModel: MainViewModel) {
    val showBoxesState = remember { mutableStateOf(false) }
    val listOfBoxes = remember { mutableStateListOf<ProgramBox>() }

    Column(modifier = Modifier.fillMaxSize()) {
        TopBar(showBoxesState)

        Box(modifier = Modifier.weight(1f)) {
            val boxHeightDp = 100.dp
            val density = LocalDensity.current

            var draggingIndex by remember { mutableStateOf<Int?>(null) }
            var offset by remember { mutableStateOf(0f) }

            Column(
                modifier = Modifier.verticalScroll(rememberScrollState())
            ) {
                listOfBoxes.forEachIndexed { index, item ->
                    val currentOffset = if (index == draggingIndex) offset else 0f

                    Box(
                        modifier = Modifier
                            .height(boxHeightDp)
                            .fillMaxWidth()
                            .offset(y = with(density) { currentOffset.toDp() })
                            .pointerInput(Unit) {
                                detectDragGestures(
                                    onDragStart = {
                                        draggingIndex = index
                                        offset = 0f
                                    },
                                    onDrag = { change, dragAmount ->
                                        change.consume()
                                        offset += dragAmount.y

                                        val boxHeightPx = density.run { boxHeightDp.roundToPx() }.toFloat()
                                        val newIndex = ((offset + boxHeightPx / 2) / boxHeightPx).toInt() + index

                                        if (newIndex != index && newIndex in 0..listOfBoxes.lastIndex) {
                                            listOfBoxes.add(newIndex, listOfBoxes.removeAt(index))
                                            draggingIndex = newIndex
                                            offset = 0f
                                        }
                                    },
                                    onDragEnd = {
                                        draggingIndex = null
                                    }
                                )
                            }
                    ) {
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = if (index == draggingIndex) Orange else Blue
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp)
                        ) {
                            item.render()
                        }
                    }
                }
            }
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
    Card(Modifier.fillMaxWidth().height(200.dp).padding(10.dp),
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
