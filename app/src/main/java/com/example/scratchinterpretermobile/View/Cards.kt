package com.example.scratchinterpretermobile.View

import androidx.compose.foundation.layout.size
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.scratchinterpretermobile.Model.Variable

@Composable
fun InitializationCard(listOfBoxes: MutableList<ProgramBox>,showBoxesState: MutableState<Boolean> = mutableStateOf(false)){
    BaseCard(name = "Инициализация", onClick = {
        listOfBoxes.add(InitializationBox())
        showBoxesState.value = false
    }) {
        TextField(modifier =  Modifier.size(80.dp),onValueChange = {}, value = "text")
    }
}

@Composable
fun AssigningCard(listOfBoxes: MutableList<ProgramBox>,showBoxesState: MutableState<Boolean>,variables: MutableList<Variable>){
    BaseCard(name = "Присваивание", onClick = {
        listOfBoxes.add(AssigningBox())
        showBoxesState.value = false
    }) {
        ListOfVar(variables)
    }
}

@Composable
fun IfCard(listOfBoxes: MutableList<ProgramBox>,showBoxesState: MutableState<Boolean>){
    BaseCard(name = "Условие", onClick = {
        listOfBoxes.add(IfBox())
        showBoxesState.value = false
    }) {
    }
}

@Composable
fun ConsoleCard(listOfBoxes: MutableList<ProgramBox>,showBoxesState: MutableState<Boolean>){
    BaseCard(name = "Вывод", onClick = {
        listOfBoxes.add(ConsoleBox())
        showBoxesState.value = false
    }) {
    }
}