package com.example.scratchinterpretermobile.View.Cards

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.example.scratchinterpretermobile.View.BaseStructure.BaseCard
import com.example.scratchinterpretermobile.View.Boxes.ConsoleBox
import com.example.scratchinterpretermobile.View.Boxes.ProgramBox

@Composable
fun ConsoleCard(listOfBoxes: MutableList<ProgramBox>, showBoxesState: MutableState<Boolean>){
    BaseCard(name = "Вывод", onClick = {
        listOfBoxes.add(ConsoleBox())
        showBoxesState.value = false
    }) {
    }
}