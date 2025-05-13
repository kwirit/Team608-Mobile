package com.example.scratchinterpretermobile.View

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.scratchinterpretermobile.Model.Variable

@Composable
fun InitializationCard(
    listOfBoxes: MutableList<ProgramBox>? = null,
    showBoxesState: MutableState<Boolean>? = null
) {
    val localList = listOfBoxes ?: mutableListOf<ProgramBox>()
    val localShowBoxesState = showBoxesState ?: remember { mutableStateOf(false) }

    BaseCard(
        name = "Инициализация",
        onClick = {
            localList.add(InitializationBox())
            if (showBoxesState != null) {
                localShowBoxesState.value = false
            }
        }
    ) {
        TextField(modifier = Modifier.size(80.dp), onValueChange = {}, value = "text")
    }
}

@Composable
fun AssigningCard(
    listOfBoxes: MutableList<ProgramBox>? = null,
    showBoxesState: MutableState<Boolean>? = null,
    variables: MutableList<Variable>
) {
    val localList = listOfBoxes ?: mutableListOf<ProgramBox>()
    val localShowBoxesState = showBoxesState ?: remember { mutableStateOf(false) }

    BaseCard(
        name = "Присваивание",
        onClick = {
            localList.add(AssigningBox())
            if (showBoxesState != null) {
                localShowBoxesState.value = false
            }
        }
    ) {
        ListOfVar(variables)
    }
}

@Composable
fun IfCard(
    listOfBoxes: MutableList<ProgramBox>? = null,
    showBoxesState: MutableState<Boolean>? = null
) {
    val localList = listOfBoxes ?: mutableListOf<ProgramBox>()
    val localShowBoxesState = showBoxesState ?: remember { mutableStateOf(false) }

    BaseCard(
        name = "Условие",
        onClick = {
            localList.add(IfBox())
            if (showBoxesState != null) {
                localShowBoxesState.value = false
            }
        }
    ) {}
}

@Composable
fun ConsoleCard(
    listOfBoxes: MutableList<ProgramBox>? = null,
    showBoxesState: MutableState<Boolean>? = null
) {
    val localList = listOfBoxes ?: mutableListOf<ProgramBox>()
    val localShowBoxesState = showBoxesState ?: remember { mutableStateOf(false) }

    BaseCard(
        name = "Вывод",
        onClick = {
            localList.add(ConsoleBox())
            if (showBoxesState != null) {
                localShowBoxesState.value = false
            }
        }
    ) {
    }
}