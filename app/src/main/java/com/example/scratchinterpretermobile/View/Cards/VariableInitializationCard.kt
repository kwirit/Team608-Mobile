package com.example.scratchinterpretermobile.View.Cards

import androidx.compose.foundation.layout.size
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.scratchinterpretermobile.View.BaseStructure.BaseCard
import com.example.scratchinterpretermobile.View.Boxes.VariableInitializationBox
import com.example.scratchinterpretermobile.View.Boxes.ProgramBox

@Composable
fun VariableInitializationCard(listOfBoxes: MutableList<ProgramBox>, showBoxesState: MutableState<Boolean> = mutableStateOf(false)){
    BaseCard(name = "Инициализация переменной", onClick = {
        listOfBoxes.add(VariableInitializationBox())
        showBoxesState.value = false
    }) {
        TextField(modifier =  Modifier.size(80.dp),onValueChange = {}, value = "text")
    }
}