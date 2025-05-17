package com.example.scratchinterpretermobile.View.Cards

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.example.scratchinterpretermobile.Model.Variable
import com.example.scratchinterpretermobile.View.BaseStructure.BaseCard
import com.example.scratchinterpretermobile.View.Boxes.AssigningBox
import com.example.scratchinterpretermobile.View.Boxes.ProgramBox
import com.example.scratchinterpretermobile.View.ListOfVar

@Composable
fun AssigningCard(listOfBoxes: MutableList<ProgramBox>, showBoxesState: MutableState<Boolean>, variables: MutableList<Variable>){
    BaseCard(name = "Присваивание", onClick = {
        listOfBoxes.add(AssigningBox())
        showBoxesState.value = false
    }) {
        ListOfVar(variables)
    }
}