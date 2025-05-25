package com.example.scratchinterpretermobile.View.Cards

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.scratchinterpretermobile.Model.Variable
import com.example.scratchinterpretermobile.View.BaseStructure.BaseCard
import com.example.scratchinterpretermobile.View.Boxes.AssigningBox
import com.example.scratchinterpretermobile.View.Boxes.ProgramBox
import com.example.scratchinterpretermobile.View.Widgets.ListOfVar

@Composable
fun AssigningCard(onAdd: () -> Unit , showBoxesState: MutableState<Boolean> = mutableStateOf(false)){
    BaseCard(name = "Присваивание", onClick = {
        onAdd()
        showBoxesState.value = false
    }) {
    }
}