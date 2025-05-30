package com.example.scratchinterpretermobile.View.Cards

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.scratchinterpretermobile.View.BaseStructure.BaseCard
import com.example.scratchinterpretermobile.View.Boxes.ConsoleBox
import com.example.scratchinterpretermobile.View.Boxes.ProgramBox

@Composable
fun ConsoleCard(onAdd: () -> Unit, showBoxesState: MutableState<Boolean> = mutableStateOf(false)){
    BaseCard(name = "Вывод", onClick = {
        onAdd()
        showBoxesState.value = false
    }) {
    }
}