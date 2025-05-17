package com.example.scratchinterpretermobile.View.Cards

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.example.scratchinterpretermobile.View.BaseStructure.BaseCard
import com.example.scratchinterpretermobile.View.Boxes.IfBox
import com.example.scratchinterpretermobile.View.Boxes.ProgramBox

@Composable
fun IfCard(listOfBoxes: MutableList<ProgramBox>, showBoxesState: MutableState<Boolean>){
    BaseCard(name = "Условие", onClick = {
        listOfBoxes.add(IfBox())
        showBoxesState.value = false
    }) {
    }
}